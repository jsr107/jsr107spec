/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.event.CacheEntryListener;
import javax.cache.event.NotificationScope;

/**
 * A CacheBuilder is used for creating Caches.
 * A CacheBuilder is created by {@link CacheManager#createCacheBuilder(String)} and is associated with that
 * manager.
 *
 * Additional configuration methods may be available on a builder instance by casting to a concrete implementation.
 *
 * @param <K> the key type
 * @param <V> the value type
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
     * @return a new instance of the named cache
     * @throws InvalidConfigurationException thrown if the configuration is invalid
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

    /**
     * Registers a listener. Can be invoked multiple times.
     *
     * @param cacheEntryListener the listener
     * @param scope              the notification scope.
     * @param synchronous        whether to listener should be invoked synchronously
     * @return the builder
     * @throws NullPointerException if any of the arguments are null.
     */
    CacheBuilder<K, V> registerCacheEntryListener(CacheEntryListener<K, V> cacheEntryListener, NotificationScope scope, boolean synchronous);


}
