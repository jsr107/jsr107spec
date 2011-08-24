/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;


/**
 * Allows the configuration of cacheName, cacheResolver and cacheKeyResolver at the class level.
 * The same settings at the method level will override this.
 * This allows you to have defaults at the class level.
 * <p/>
 * Example of specifying a default cache name that is used by the getDomain and deleteDomain
 * interceptors. The inteceptor for getAllDomains would use the "allDomains" cache specified
 * in the method level annotation.
 * <p><blockquote><pre>
 * package my.app;
 * 
 * &#64;CacheDefaults(cacheName="domainCache")
 * public class DomainDao {
 *   &#64;CacheResult
 *   public Domain getDomain(String domainId, int index) {
 *     ...
 *   }
 *   
 *   &#64;CacheRemoveEntry
 *   public void deleteDomain(String domainId, int index) {
 *     ...
 *   }
 *   
 *   &#64;CacheResult(cacheName="allDomains")
 *   public List<Domain> getAllDomains() {
 *     ...
 *   }
 * }
 * </pre></blockquote></p>
 * 
 * @author Rick Hightower
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDefaults {

    /**
     * (Optional) default name of the cache for the annotated class
     * <p/>
     * If not specified defaults to: package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) The {@link CacheResolverFactory} to use to find the {@link CacheResolver} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used when
     * interacting with the {@link javax.cache.Cache}
     * <p/>
     * Defaults to a key generator that uses {@link java.util.Arrays#deepHashCode(Object[])} and 
     * {@link java.util.Arrays#deepEquals(Object[], Object[])} with the array returned by
     * {@link CacheKeyInvocationContext#getKeyParameters()}
     */
    @Nonbinding
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;

}
