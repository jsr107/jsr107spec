/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * A CacheBuilder is used for creating Caches.

 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public interface CacheBuilder<K, V> {

    /**
     * Create an instance of the named {@link Cache}.
     * Solely responsible for creating uninitialized cache instances.
     * Lookup and Lifecycle management are the responsibility of {@link CacheManager}.
     * Implementations may use a default configuration or use the cache name to select
     * a suitable configuration.
     *
     * @throws InvalidConfigurationException thrown if the configuration is invalid
     * @return a new instance of the named cache
     * @see CacheManager
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
