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
 * When a method annotated with {@link CacheRemoveAll} is invoked all elements in the specified cache
 * will be removed via the {@link javax.cache.Cache#removeAll()} method
 * <p/>
 * Example of removing all Domain objects from the "domainCache". {@link javax.cache.Cache#removeAll()}
 * will be called after deleteAllDomains() returns successfully. 
 * <p><blockquote><pre>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheRemoveAll(cacheName="domainCache")
 *   public void deleteAllDomains() {
 *     ...
 *   }
 * }
 * </pre></blockquote></p>
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemoveAll {

    /**
     * (Optional) name of the cache.
     * <p/>
     * If not specified defaults first to {@link CacheDefaults#cacheName()} an if that is not set it
     * an unspecified exception will be thrown by the provider.
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) When {@link javax.cache.Cache#removeAll()} should be called. If true it is called after the annotated method
     * invocation completes successfully. If false it is called before the annotated method is invoked.
     * <p/>
     * Defaults to true.
     * <p/>
     * If true and the annotated method throws an exception the put will not be executed.
     */
    @Nonbinding
    boolean afterInvocation() default true;

    /**
     * (Optional) The {@link CacheResolverFactory} to use to find the {@link javax.cache.Cache} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolverFactory> cacheResolverFactory() default CacheResolverFactory.class;
}
