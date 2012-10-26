/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.event.CacheEntryListener;
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
     * @throws CacheException if a cache with that name already exists or there was an error adding the cache to the CacheManager
     */
    Cache<K, V> build();

    /**
     * Sets the cache loader.
     *
     * @param cacheLoader the CacheLoader
     * @return the builder
     * @throws NullPointerException if cacheLoader is null.
     */
    CacheBuilder<K, V> setCacheLoader(CacheLoader<K, ? extends V> cacheLoader);

    /**
     * Sets the cache writer.
     *
     * @param cacheWriter the CacheWriter
     * @return the builder
     * @throws NullPointerException if cacheWriter is null.
     */
    CacheBuilder<K, V> setCacheWriter(CacheWriter<? super K, ? super V> cacheWriter);

    /**
     * Registers a listener. Can be invoked multiple times.
     *
     * @param cacheEntryListener the listener
     * @return the builder
     * @throws NullPointerException if any of the arguments are null.
     */
    CacheBuilder<K, V> registerCacheEntryListener(CacheEntryListener<K, V> cacheEntryListener);

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
     * @param isolationLevel - the isolation level for this cache
     * @param mode - the mode (Local or XA) for this cache
     * @return the builder
     * @throws IllegalArgumentException if the cache does not support transactions,
     * or an attempt is made to set the isolation level to {@link IsolationLevel#NONE} or the mode to {@link Mode#NONE}.
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
     * Sets whether the cache is a read-through cache. If so a CacheLoader should be configured.
     *
     * @param readThrough the value for readThrough
     * @return the builder
     */
    CacheBuilder<K, V> setReadThrough(boolean readThrough);

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     *
     * @param writeThrough set to true for a write-through cache
     * @return the builder
     */
    CacheBuilder<K, V> setWriteThrough(boolean writeThrough);

    /**
     * Sets the cache expiration
     *
     * @param type whether based on creation/modification or last access time
     * @param duration the amount of time
     * @return the builder
     * @throws NullPointerException if size is duration
     */
    CacheBuilder<K, V> setExpiry(CacheConfiguration.ExpiryType type, CacheConfiguration.Duration duration);

    //todo #65
    /**
     *
     * Sets the cache expiration
     *
     * @param type whether based on creation/modification or last access time
     * @param duration the amount of time
     * @return the builder
     * @throws NullPointerException if size is duration
     */
    //CacheBuilder<K, V> setExpiry(CacheConfiguration.ExpiryPolicy policy);

}
