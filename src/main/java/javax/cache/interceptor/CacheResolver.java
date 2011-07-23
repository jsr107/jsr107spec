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
 * Determines the {@link Cache} to use for a specified cache name and annotated method. Implementations MUST be thread-safe
 *
 * @author Eric Dalquist
 * @since 1.7
 */
public interface CacheResolver {

    /**
     * @param cacheName The name of the cache specified in the {@link CacheResult}, {@link CacheRemoveEntry}, or {@link CacheRemoveAll} annotation
     * @param method    The annotated method
     * @return The {@link Cache} instance to be used by the intercepter
     */
    <K, V> Cache<K, V> resolveCache(String cacheName, Method method);
}
