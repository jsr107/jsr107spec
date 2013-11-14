/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import javax.cache.CacheManager;
import javax.enterprise.util.Nonbinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;


/**
 * Allows the configuration of defaults for {@link CacheResult}, {@link CachePut},
 * {@link CacheRemove}, and {@link CacheRemoveAll} at the class level. Without
 * the method level annotations this annotation has no effect.
 * <p>
 * Example of specifying a default cache name that is used by the annotations on the
 * getDomain and deleteDomain methods. The annotation for getAllDomains would use the
 * "allDomains" cache name specified in the method level annotation.
 * <pre><code>
 * package my.app;
 * 
 * &#64;CacheDefaults(cacheName="domainCache")
 * public class DomainDao {
 *   &#64;CacheResult
 *   public Domain getDomain(String domainId, int index) {
 *     ...
 *   }
 * 
 *   &#64;CacheRemove
 *   public void deleteDomain(String domainId, int index) {
 *     ...
 *   }
 * 
 *   &#64;CacheResult(cacheName="allDomains")
 *   public List&lt;Domain&gt; getAllDomains() {
 *     ...
 *   }
 * }
 * </code></pre>
 *
 * @author Rick Hightower
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDefaults {

  /**
   * The default name of the cache for the annotated class
   * <p>
   * If not specified defaults to: package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
   * <p>
   * Applicable for {@link CacheResult}, {@link CachePut}, {@link CacheRemove}, and {@link CacheRemoveAll}
   */
  @Nonbinding String cacheName() default "";

  /**
   * The {@link CacheResolverFactory} used to find the {@link CacheResolver} to use at runtime.
   * <p>
   * The default resolver pair will resolve the cache by name from the default {@link CacheManager}
   * <p>
   * Applicable for {@link CacheResult}, {@link CachePut}, {@link CacheRemove}, and {@link CacheRemoveAll}
   */
  @Nonbinding Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;

  /**
   * The {@link CacheKeyGenerator} to use to generate the {@link GeneratedCacheKey} for interacting
   * with the specified Cache.
   * <p>
   * Defaults to a key generator that uses {@link Arrays#deepHashCode(Object[])} and
   * {@link Arrays#deepEquals(Object[], Object[])} with the array returned by
   * {@link CacheKeyInvocationContext#getKeyParameters()}
   * <p>
   * Applicable for {@link CacheResult}, {@link CachePut}, and {@link CacheRemove}
   *
   * @see CacheKey
   */
  @Nonbinding Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
}
