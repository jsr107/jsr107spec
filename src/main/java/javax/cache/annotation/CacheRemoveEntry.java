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
 * When a method annotated with {@link CacheRemoveEntry} is invoked a {@link CacheKey} will be generated and
 * {@link javax.cache.Cache#remove(Object)} will be invoked on the specified cache.
 * <p/>
 * Example of removing a specific Domain object from the "domainCache". A {@link CacheKey} will be generated
 * from the String and int parameters and used to call {@link javax.cache.Cache#remove(Object)} after
 * the deleteDomain method completes successfully.
 * <p><blockquote><pre>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheRemoveEntry(cacheName="domainCache")
 *   public void deleteDomain(String domainId, int index) {
 *     ...
 *   }
 * }
 * </pre></blockquote></p>
 * Exception Handling, only used if {@link #afterInvocation()} is true.
 * <ol>
 *  <li>If {@link #evictFor()} and {@link #noEvictFor()} are both empty then all exceptions prevent the remove</li>
 *  <li>If {@link #evictFor()} is specified and {@link #noEvictFor()} is not specified then only exceptions 
 *      which pass an instanceof check against the evictFor list result in a remove</li>
 *  <li>If {@link #noEvictFor()} is specified and {@link #evictFor()} is not specified then all exceptions 
 *      which do not pass an instanceof check against the noEvictFor result in a remove</li>
 *  <li>If {@link #evictFor()} and {@link #noEvictFor()} are both specified then exceptions which pass an
 *      instanceof check against the evictFor list but do not pass an instanceof check against the noEvictFor
 *      list result in a remove</li>
 * </ol>
 * 
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.0
 * @see CacheKeyParam
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemoveEntry {

    /**
     * (Optional) name of the cache.
     * <p/>
     * If not specified defaults first to {@link CacheDefaults#cacheName()} an if that is not set it
     * an unspecified exception will be thrown by the provider.
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) When {@link javax.cache.Cache#remove(Object)}  should be called. If true it is called after the annotated method
     * invocation completes successfully. If false it is called before the annotated method is invoked.
     * <p/>
     * Defaults to true.
     * <p/>
     * If true and the annotated method throws an exception the put will not be executed.
     */
    @Nonbinding
    boolean afterInvocation() default true;

    /**
     * (Optional) The {@link CacheResolverFactory} to use to find the {@link CacheResolver} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call
     * {@link javax.cache.Cache#remove(Object)}
     * <p/>
     * Defaults to a key generator that uses {@link java.util.Arrays#deepHashCode(Object[])} and 
     * {@link java.util.Arrays#deepEquals(Object[], Object[])} with the array returned by
     * {@link CacheKeyInvocationContext#getKeyParameters()}
     */
    @Nonbinding
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
    
    /**
     * Defines zero (0) or more exception {@link Class classes}, which must be a
     * subclass of {@link Throwable}, indicating which exception types must cause
     * a cache evict. Only used if {@link #afterInvocation()} is true.
     */
    Class<? extends Throwable>[] evictFor() default { };

    /**
     * Defines zero (0) or more exception {@link Class Classes}, which must be a
     * subclass of {@link Throwable}, indicating which exception types must <b>not</b>
     * cause a cache evict. Only used if {@link #afterInvocation()} is true.
     */
    Class<? extends Throwable>[] noEvictFor() default { };
}
