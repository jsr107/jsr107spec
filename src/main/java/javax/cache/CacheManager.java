/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import java.util.List;

/**
 * A CacheManager is used for looking up Caches and controls their lifecycle.
 *
 * @author Greg Luck
 */
public interface CacheManager {



    /**
     * Adds an unitialised {@link Cache} to the CacheManager.
     * <p/>
     * @param cache the cache to add
     * @throws IllegalStateException if the cache is not {@link Status#UNITIALISED} before this method is called.
     * @throws CacheException        if there was an error adding the cache to the CacheManager
     */
    void addCache(Cache<?, ?> cache) throws IllegalStateException, CacheException;

    /**
     * Creates a {@link Cache} cache using defaults.
     * <p/>
     *
     * @param cacheName the cache name
     * @return the newly create cache
     * @throws CacheException  if there was an error adding the cache to the CacheManager
     */
     <K, V> Cache<K, V> createCache(String cacheName) throws CacheException;

    /**
     * Creates a {@link Cache} in the the CacheManager from the provided configuration.
     * <p/>
     * @param configuration The configuration.
     * @throws InvalidConfigurationException thrown if the configuration is invalid
     * @throws CacheException        if there was an error adding the cache to the CacheManager
     */
    <K, V> Cache<K, V> createCache(CacheConfiguration configuration) throws CacheException;

    /**
     * Checks whether a cache of type ehcache exists.
     * <p/>
     *
     * @param cacheName the cache name to check for
     * @return true if it exists
     */
    boolean cacheExists(String cacheName);


    /**
     * Returns a list of the names of the caches.
     *
     * @return the names
     */
    List<String> getCacheNames();

    /**
     * Looks up a named cache.
     *
     * @param cacheName the name of the cache to look for
     * @return the Cache or null if it does exist
     * @throws IllegalStateException if the Cache is not {@link Status#STARTED}
     */
    <K, V> Cache<K, V> getCache(String cacheName);

    /**
     * Looks up a named cache, creating it using defaults if it does not exist.
     *
     * @param cacheName the name of the cache to look for
     * @return the Cache or null if it does exist
     * @throws CacheException if there was an error adding the cache to the CacheManager
     */
    <K, V> Cache<K, V> getOrCreateCache(String cacheName);

    /**
     * Remove a cache from the CacheManager. The cache will be is disposed of.
     *
     * @param cacheName the cache name
     * @return true if the cache was removed
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     */
    boolean removeCache(String cacheName) throws IllegalStateException;


    /**
     * This method will return a TransactionManager which can only be used for
     * local transactions, not XA Transactions.
     *
     * @return the UserTransaction
     */
    javax.transaction.UserTransaction getUserTransaction();


    /**
     * Shuts down the CacheManager.
     * <p/>
     * Each cache will be shut down in no guaranteed order. While caches are being shut down their status and the status of
     * CacheManager is {@link Status#STOPPING}. As they are shut down their status is change to {@link Status#STOPPED}. Finally
     * the CacheManager's status is changed to {@link Status#STOPPED}
     * <p/>
     * A {@link IllegalStateException} will be thrown if an operation is performed on CacheManager or any contained Cache while
     * they are stopping or are a stopped.
     * <p/>
     * A given CacheManager instance cannot be restarted after it has been stopped. A new one must be created.
     */
    void shutdown();

}
