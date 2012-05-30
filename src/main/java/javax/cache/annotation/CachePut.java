/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.annotation;

import javax.enterprise.util.Nonbinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When a method annotated with {@link CachePut} is invoked a {@link CacheKey} will be generated and
 * {@link javax.cache.Cache#put(Object, Object)} will be invoked on the specified cache storing the value
 * marked with {@link CacheValue}. Null values are cached by default but this behavior can be disabled via
 * the {@link #cacheNull()} property.
 * <p/>
 * The default behavior is to call {@link javax.cache.Cache#put(Object, Object)} after the annotated method is invoked,
 * this behavior can be changed by setting {@link #afterInvocation()} to false in which case {@link javax.cache.Cache#put(Object, Object)}
 * will be called before the annotated method is invoked. 
 * <p/>
 * Example of caching the Domain object with a key generated from the String and int parameters. The
 * {@link CacheValue} annotation is used to designate which parameter should be stored in the "domainDao"
 * cache.
 * <p><blockquote><pre>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CachePut(cacheName="domainCache")
 *   public void updateDomain(String domainId, int index, &#64;CacheValue Domain domain) {
 *     ...
 *   }
 * }
 * </pre></blockquote></p>
 * Exception Handling, only used if {@link #afterInvocation()} is true.
 * <ol>
 *  <li>If {@link #cacheFor()} and {@link #noCacheFor()} are both empty then all exceptions prevent the put</li>
 *  <li>If {@link #cacheFor()} is specified and {@link #noCacheFor()} is not specified then only exceptions 
 *      which pass an instanceof check against the cacheFor list result in a put</li>
 *  <li>If {@link #noCacheFor()} is specified and {@link #cacheFor()} is not specified then all exceptions 
 *      which do not pass an instanceof check against the noCacheFor result in a put</li>
 *  <li>If {@link #cacheFor()} and {@link #noCacheFor()} are both specified then exceptions which pass an
 *      instanceof check against the cacheFor list but do not pass an instanceof check against the noCacheFor
 *      list result in a put</li>
 * </ol>
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.0
 * @see CacheValue
 * @see CacheKeyParam
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CachePut {

    /**
     * (Optional) name of the cache.
     * <p/>
     * If not specified defaults first to {@link CacheDefaults#cacheName()} an if that is not set it
     * defaults to: package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) When {@link javax.cache.Cache#put(Object, Object)} should be called. If true it is called after the annotated method
     * invocation completes successfully. If false it is called before the annotated method is invoked.
     * <p/>
     * Defaults to true.
     * <p/>
     * If true and the annotated method throws an exception the rules governing {@link #cacheFor()} and {@link #noCacheFor()}
     * will be followed.
     */
    @Nonbinding
    boolean afterInvocation() default true;

    /**
     * (Optional) If set to false null {@link CacheValue} values will not be cached. If true (the default) null {@link CacheValue}
     * values will be cached.
     * <p/>
     * Defaults to true
     */
    @Nonbinding
    boolean cacheNull() default true;

    /**
     * (Optional) The {@link CacheResolverFactory} used to find the {@link CacheResolver} to use at runtime.
     * <p/>
     * The default resolver pair will resolve the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the {@link CacheKey} for interacting
     * with the specified Cache.
     * <p/>
     * Defaults to a key generator that uses {@link java.util.Arrays#deepHashCode(Object[])} and 
     * {@link java.util.Arrays#deepEquals(Object[], Object[])} with the array returned by
     * {@link CacheKeyInvocationContext#getKeyParameters()}
     * 
     * @see CacheKeyParam
     */
    @Nonbinding
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
    
    /**
     * Defines zero (0) or more exception {@link Class classes}, which must be a
     * subclass of {@link Throwable}, indicating which exception types <b>must</b> cause
     * the parameter to be cached. Only used if {@link #afterInvocation()} is true.
     */
    @Nonbinding
    Class<? extends Throwable>[] cacheFor() default { };

    /**
     * Defines zero (0) or more exception {@link Class Classes}, which must be a
     * subclass of {@link Throwable}, indicating which exception types <b>must not</b>
     * cause the parameter to be cached. Only used if {@link #afterInvocation()} is true.
     */
    @Nonbinding
    Class<? extends Throwable>[] noCacheFor() default { };
}
