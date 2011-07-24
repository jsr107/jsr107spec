/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Allows the configuration of cacheName, cacheResolver and cacheKeyResolver at the class level.
 * The same settings at the method level will override this.
 * This allows you to have defaults at the class level.
 * <p/>
 *
 * @author Rick Hightower
 * @since 1.7
 */
@Target( {ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheConfig {

    /**
     * (Optional) name of the cache.
     * <p/>
     * Defaults to ClassName.methodName(argument type, argument type)
     */
    @Nonbinding
    String cacheName() default "";

    /**
     * (Optional) The {@link CacheResolver} to use to find the {@link javax.cache.Cache} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link javax.cache.CacheManager}
     */
    @Nonbinding
    Class<? extends CacheResolver> cacheResovler() default CacheResolver.class;

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call {@link javax.cache.Cache#get(Object)}
     * {@link javax.cache.Cache#put(Object, Object)}
     * <p/>
     * Defaults to {@link CacheKeyGenerator}
     */
    @Nonbinding
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;

}
