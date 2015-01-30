/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When a method annotated with {@link CacheRemoveAll} is invoked all elements in
 * the specified cache will be removed via the
 * {@link Cache#removeAll()} method
 * <p>
 * The default behavior is to call {@link Cache#removeAll()} after the
 * annotated method is invoked, this behavior can be changed by setting {@link
 * #afterInvocation()} to false in which case {@link Cache#removeAll()}
 * will be called before the annotated method is invoked.
 * <p>
 * Example of removing all Domain objects from the "domainCache". {@link
 * Cache#removeAll()} will be called after deleteAllDomains() returns
 * successfully.
 * <pre><code>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheRemoveAll(cacheName="domainCache")
 *   public void deleteAllDomains() {
 *     ...
 *   }
 * }
 * </code></pre>
 * <p>
 * Exception Handling, only used if {@link #afterInvocation()} is true.
 * <ol>
 * <li>If {@link #evictFor()} and {@link #noEvictFor()} are both empty then all
 * exceptions prevent the removeAll</li>
 * <li>If {@link #evictFor()} is specified and {@link #noEvictFor()} is not
 * specified then only exceptions that pass an instanceof check against the
 * evictFor list result in a removeAll</li>
 * <li>If {@link #noEvictFor()} is specified and {@link #evictFor()} is not
 * specified then all exceptions that do not pass an instanceof check against the
 * noEvictFor result in a removeAll</li>
 * <li>If {@link #evictFor()} and {@link #noEvictFor()} are both specified then
 * exceptions that pass an instanceof check against the evictFor list but do not
 * pass an instanceof check against the noEvictFor list result in a removeAll</li>
 * </ol>
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheRemoveAll {

  /**
   * /**
   * The name of the cache.
   * <p>
   * If not specified defaults first to {@link CacheDefaults#cacheName()} and if
   * that is not set it defaults to:
   * package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
   */
  @Nonbinding String cacheName() default "";

  /**
   * When {@link Cache#removeAll()} should be called. If true it is called after
   * the annotated method invocation completes successfully. If false it is called
   * before the annotated method is invoked.
   * <p>
   * Defaults to true.
   * <p>
   * If true and the annotated method throws an exception the put will not be
   * executed.
   */
  @Nonbinding boolean afterInvocation() default true;

  /**
   * The {@link CacheResolverFactory} used to find the {@link CacheResolver} to
   * use at runtime.
   * <p>
   * The default resolver pair will resolve the cache by name from the default
   * {@link CacheManager}
   */
  @Nonbinding Class<? extends CacheResolverFactory> cacheResolverFactory()
      default CacheResolverFactory.class;

  /**
   * Defines zero (0) or more exception {@link Class classes}, that must be a
   * subclass of {@link Throwable}, indicating the exception types that must
   * cause a cache eviction. Only used if {@link #afterInvocation()} is true.
   */
  @Nonbinding Class<? extends Throwable>[] evictFor() default {};

  /**
   * Defines zero (0) or more exception {@link Class Classes}, that must be a
   * subclass of {@link Throwable}, indicating the exception types that must
   * <b>not</b> cause a cache eviction. Only used if {@link #afterInvocation()} is
   * true.
   */
  @Nonbinding Class<? extends Throwable>[] noEvictFor() default {};
}
