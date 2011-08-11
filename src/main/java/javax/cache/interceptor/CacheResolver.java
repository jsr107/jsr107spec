/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.interceptor;

import java.lang.annotation.Annotation;

import javax.cache.Cache;

/**
 * Determines the {@link Cache} to use for an annotated method. Implementations MUST be
 * thread-safe.
 *
 * @author Eric Dalquist
 * @since 1.0
 * @see CacheResolverFactory
 */
public interface CacheResolver {

    /**
     * Resolve the {@link Cache} to use for this {@link CacheInvocationContext} and {@link CacheKey}.
     * 
     * the specified cache name and {@link java.lang.reflect.Method} that has been
     * annotated with {@link CacheResult}, {@link CachePut}, {@link CacheRemoveEntry}, or {@link CacheRemoveAll}.
     * 
     * @param cacheInvocationContext The context data for the method invocation
     * @return The {@link Cache} instance to be used by the intercepter
     */
    <K, V> Cache<K, V> resolveCache(CacheInvocationContext<Annotation> cacheInvocationContext);
    
}
