/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When a method annotated with {@link CacheRemoveEntry} is invoked a {@link CacheKey} will be generated and
 * {@link javax.cache.Cache#remove(Object)} will be invoked on the specified cache.
 *
 * @author Eric Dalquist
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheRemoveEntry {

    /**
     * (Optional) name of the cache.
     * <p/>
     * Defaults to ClassName.methodName
     */
    String cacheName() default "";

    /**
     * (Optional) When {@link javax.cache.Cache#remove(Object)}  should be called. If true it is called after the annotated method
     * invocation completes successfully. If false it is called before the annotated method is invoked.
     * <p/>
     * Defaults to true.
     */
    boolean afterInvocation() default true;

    /**
     * (Optional) The {@link CacheResolver} to use to find the {@link javax.cache.Cache} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    Class<? extends CacheResolver> cacheResovler() default CacheResolver.class;

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call {@link javax.cache.Cache#remove(Object)}
     * <p/>
     * Defaults to {@link DefaultCacheKeyGenerator}
     */
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default DefaultCacheKeyGenerator.class;
}
