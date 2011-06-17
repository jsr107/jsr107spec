/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * A CacheBuilder is used for creating Caches.
 *
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheBuilder {

    /**
     * Create an instance of the named {@link Cache}.
     * Solely responsible for creating uninitialized cache instances.
     * Lookup and Lifecycle management are the responsibility of {@link CacheManager}.
     * Implementations may use a default configuration or use the cache name to select
     * a suitable configuration.
     *
     * @param cacheName the name of the cache
     * @param <K> the key type
     * @param <V> the value type
     * @throws InvalidConfigurationException thrown if the configuration is invalid
     * @throws NullPointerException is cacheName is null
     * @return a new instance of the named cache
     * @see CacheManager
     */
    <K, V> Cache<K, V> createCache(String cacheName);

    /**
     * Create an instance of the named {@link Cache}.
     * Solely responsible for creating uninitialized cache instances.
     * Lookup and Lifecycle management are the responsibility of {@link CacheManager}.
     *
     * @param cacheName the name of the cache
     * @param configuration the configuration for the new cache
     * @param <K> the key type
     * @param <V> the value type
     * @throws InvalidConfigurationException thrown if the configuration is invalid
     * @throws NullPointerException is cacheName is null or configuration is null
     * @return a new instance of the named cache
     * @see CacheManager
     */
    <K, V> Cache<K, V> createCache(String cacheName, CacheConfiguration configuration);
}
