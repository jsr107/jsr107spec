/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When a method annotated with {@link CacheResult} is invoked a {@link CacheKey} will be generated and
 * {@link javax.cache.Cache#get(Object)} is called before the invoked method actually executes. If a value is found in the
 * cache it is returned and the annotated method is never actually executed. If no value is found the
 * annotated method is invoked and the returned value is stored in the cache with the generated key.
 * <p/>
 * null return values and thrown exceptions are never cached.
 *
 * @author Eric Dalquist
 */
@Target( {ElementType.METHOD, ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheResult {

    /**
     * (Optional) name of the cache.
     * <p/>
     * Defaults to ClassName.methodName
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) If set to true the pre-invocation get is skipped and the annotated method is always executed with
     * the returned value being cached as normal. This is useful for create or update methods which should always
     * be executed and have their returned value placed in the cache.
     * <p/>
     * Defaults to false
     */
    @Nonbinding
    boolean skipGet() default false;

    /**
     * (Optional) The {@link CacheResolver} to use to find the {@link javax.cache.Cache} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolver> cacheResovler() default CacheResolver.class;

    /**
     */
    @Nonbinding
    Class<? extends Annotation>[] cacheResolverQualifiers() default { };

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call {@link javax.cache.Cache#get(Object)}
     * {@link javax.cache.Cache#put(Object, Object)}
     * <p/>
     * Defaults to {@link DefaultCacheKeyGenerator}
     */
    @Nonbinding
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default DefaultCacheKeyGenerator.class;

    /**
     */
    @Nonbinding
    Class<? extends Annotation>[] cacheKeyGeneratorQualifiers() default { };
}
