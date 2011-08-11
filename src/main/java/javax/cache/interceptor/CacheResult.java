/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;


/**
 * When a method annotated with {@link CacheResult} is invoked a {@link CacheKey} will be generated and
 * {@link javax.cache.Cache#get(Object)} is called before the invoked method actually executes. If a value is found in the
 * cache it is returned and the annotated method is never actually executed. If no value is found the
 * annotated method is invoked and the returned value is stored in the cache with the generated key.
 * <p/>
 * null return values and thrown exceptions are never cached.
 * <p/>
 * Example of caching the Domain object with a key generated from the String and int parameters.
 * With no {@link #cacheName()} specified a cache name of "my.app.DomainDao.getDomain(java.lang.String,int)"
 * will be generated.
 * <p><blockquote><pre>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheResult
 *   public Domain getDomain(String domainId, int index) {
 *     ...
 *   }
 * }
 * </pre></blockquote></p>
 * Example using the {@link CacheKey} annotation so that only the domainId parameter is used in key
 * generation
 * <p><blockquote><pre>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheResult
 *   public Domain getDomain(@CacheKey String domainId, Monitor mon) {
 *     ...
 *   }
 * }
 * </pre></blockquote></p>
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.0
 */
@Target( {ElementType.METHOD, ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheResult {

    /**
     * (Optional) name of the cache.
     * <p/>
     * If not specified defaults first to {@link CacheDefaults#cacheName()} an if that is not set it
     * defaults to: package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) If set to true the pre-invocation get is skipped and the annotated method is always executed with
     * the returned value being cached as normal. This is useful for create or update methods which should always
     * be executed and have their returned value placed in the cache.
     * <p/>
     * Defaults to false
     * @see CachePut
     */
    @Nonbinding
    boolean skipGet() default false;

    /**
     * (Optional) The {@link CacheResolverFactory} to use to find the {@link CacheResolver} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call
     * {@link javax.cache.Cache#put(Object, Object)}
     * <p/>
     * Defaults to a key generator that uses {@link java.util.Arrays#deepHashCode(Object[])} and 
     * {@link java.util.Arrays#deepEquals(Object[], Object[])} with the array returned by
     * {@link CacheKeyInvocationContext#getKeyParameters()}
     */
    @Nonbinding
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
}
