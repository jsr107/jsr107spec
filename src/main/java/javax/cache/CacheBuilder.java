/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * A CacheBuilder is used for creating Caches.
 * A CacheBuilder is created by {@link CacheManager#createCacheBuilder(String)} and is associated with that
 * manager.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheBuilder<K, V> {

    /**
     * Create an instance of the named {@link Cache}.
     * <p/>
     * The Cache will be created, added to the caches controlled by its associated CacheManager and started.
     * If there is an existing Cache of the same name associated with this CacheManager when build is invoked,
     * the old Cache will be stopped.
     *
     * @throws InvalidConfigurationException thrown if the configuration is invalid
     * @return a new instance of the named cache
     * @see CacheManager#createCacheBuilder(String)
     */
    Cache<K, V> build();

    /**
     * Set the cache configuration.
     *
     * @param configuration the cache configuration
     * @return the builder
     */
    CacheBuilder<K, V> setCacheConfiguration(CacheConfiguration configuration);

    /**
     * Set the cache loader.
     *
     * @param cacheLoader the CacheLoader
     * @return the builder
     */
    CacheBuilder<K, V> setCacheLoader(CacheLoader<K, V> cacheLoader);
}
