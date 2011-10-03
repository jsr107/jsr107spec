/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.event.CacheEntryListener;
import javax.cache.event.NotificationScope;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;

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
     * @throws InvalidConfigurationException thrown if the configuration is invalid. Examples include if
     * read through has been set to true but no cache loader is specified, or if no cache writer is specified but
     * write through has been set.
     * @see CacheManager#createCacheBuilder(String)
     */
    Cache<K, V> build();

    /**
     * Set the cache loader.
     *
     * @param cacheLoader the CacheLoader
     * @return the builder
     * @throws NullPointerException if cacheLoader is null.
     */
    CacheBuilder<K, V> setCacheLoader(CacheLoader<K, V> cacheLoader);

    /**
     * Set the cache writer.
     *
     * @param cacheWriter the CacheWriter
     * @return the builder
     * @throws NullPointerException if cacheWriter is null.
     */
    CacheBuilder<K, V> setCacheWriter(CacheWriter<K, V> cacheWriter);

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

    /**
     * Sets whether the cache is store-by-value cache.
     *
     * @param storeByValue the value for storeByValue
     * @return the builder
     * @throws IllegalStateException if the configuration can no longer be changed
     * @throws InvalidConfigurationException if the cache does not support store by reference
     * @see CacheConfiguration#isStoreByValue()
     */
    CacheBuilder<K, V> setStoreByValue(boolean storeByValue);

    /**
     * Sets whether transaction are enabled for this cache.
     *
     * @param isolationLevel - the default isolation level of this cache
     * @param mode - the mode (Local or XA) for this cache
     * @return the builder
     * @throws InvalidConfigurationException if the cache does not support transactions, or the isolation level {@link IsolationLevel#TX_NONE}
     * @see CacheConfiguration#isTransactionEnabled()
     */
    CacheBuilder<K, V> setTransactionEnabled(IsolationLevel isolationLevel, Mode mode);

    /**
     * Sets whether statistics gathering is enabled  on this cache.
     *
     * @param enableStatistics true to enable statistics, false to disable
     * @return the builder
     * @see CacheConfiguration#setStatisticsEnabled(boolean)
     */
    CacheBuilder<K, V> setStatisticsEnabled(boolean enableStatistics);

    /**
     * Sets whether the cache is a read-through cache.
     *
     * @param readThrough the value for readThrough
     * @return the builder
     * @throws IllegalStateException if the configuration can no longer be changed
     * @see CacheConfiguration#setReadThrough(boolean)
     */
    CacheBuilder<K, V> setReadThrough(boolean readThrough);

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     *
     * @param writeThrough set to true for a write-through cache
     * @return the builder
     * @see CacheConfiguration#setWriteThrough(boolean)
     */
    CacheBuilder<K, V> setWriteThrough(boolean writeThrough);

    /**
     * Sets the cache expiration
     *
     * @param timeToLive the time to live
     * @return the builder
     * @see CacheConfiguration#setExpiry(javax.cache.CacheConfiguration.Duration)
     * @throws NullPointerException if size is timeToLive
     */
    CacheBuilder<K, V> setExpiry(CacheConfiguration.Duration timeToLive);

    /**
     * Sets the size
     *
     * @param size the size
     * @return the builder
     * @see CacheConfiguration#setSize(javax.cache.CacheConfiguration.Size)
     * @throws NullPointerException if size is null
     */
    CacheBuilder<K, V> setSize(CacheConfiguration.Size size);
}
