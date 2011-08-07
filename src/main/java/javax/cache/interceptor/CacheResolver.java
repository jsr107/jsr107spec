/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.interceptor;

import javax.cache.Cache;
import java.lang.reflect.Method;

/**
 * Determines the {@link Cache} to use for a specified cache name and annotated method at
 * initialization time. Implementations MUST be thread-safe
 *
 * @author Eric Dalquist
 * @since 1.0
 */
public interface CacheResolver {

    /**
     * Resolve the {@link Cache} to use for the specified cache name and {@link Method} that has been
     * annotated with {@link CacheResult}, {@link CachePut}, {@link CacheRemoveEntry}, or {@link CacheRemoveAll}.
     * 
     * @param cacheName The name of the cache specified in the annotation
     * @param method    The annotated method
     * @return The {@link Cache} instance to be used by the intercepter
     */
    <K, V> Cache<K, V> resolveCache(String cacheName, Method method);
}
