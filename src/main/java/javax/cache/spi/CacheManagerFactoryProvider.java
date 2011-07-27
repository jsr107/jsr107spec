/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.Cache;
import javax.cache.CacheConfiguration;
import javax.cache.CacheManager;

/**
 * Interface that should be implemented by a CacheManager factory provider.
 *
 * It is invoked by the {@link javax.cache.CacheManagerFactory} class to create
 * a {@link CacheManager}
 *
 * @see javax.cache.CacheManagerFactory
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public interface CacheManagerFactoryProvider {

    /**
     * Called by the {@link javax.cache.CacheManagerFactory} class when a
     * new CacheManager needs to be created.
     *
     * An implementation of this interface must have a public no-arg constructor.
     *
     * @param name the name of this cache manager
     * @return a new cache manager.
     * @throws NullPointerException if name is null
     */
    CacheManager createCacheManager(String name);

    /**
     * Creates a cache instance.
     * <p/>
     * <em>TODO (yannis): Not clear why this is required.</em>
     *
     * @param name the cache name
     * @return a new cache
     */
    <K, V> Cache<K, V> createCache(String name);

    /**
     * Create a mutable {@link javax.cache.CacheConfiguration} instance.
     * The configuration returned should have the default values.
     *
     * @return a cache configuration
     */
    CacheConfiguration createCacheConfiguration();
}
