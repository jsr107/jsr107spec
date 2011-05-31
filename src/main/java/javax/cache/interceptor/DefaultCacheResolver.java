/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.interceptor;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * Default {@link CacheResolver} that uses the default {@link CacheManager} and finds the {@link Cache}
 * using {@link CacheManager#getCache(String)}.
 *
 * @author Eric Dalquist
 */
public class DefaultCacheResolver implements CacheResolver {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final CacheManager cacheManager;

    /**
     * Constructs the resolver
     * @param cacheManager the cache manager to use
     */
    public DefaultCacheResolver(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * @see javax.cache.interceptor.CacheResolver#resolveCacheManger(java.lang.String, java.lang.reflect.Method)
     */
    public <K, V> Cache<K, V> resolveCacheManger(String cacheName, Method method) {

        Cache<K, V> cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            logger.warning("No Cache named '" + cacheName + "' was found in the CacheManager, a copy of the default cache will be created.");
            cache = cacheManager.createCache(cacheName);
        }
        return cache;
    }

}
