/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.transaction.UserTransaction;

/**
 * A CacheManager is used for looking up Caches and controls their lifecycle. It represents a collection of caches.
 * <p/>
 * To the extent that implementations have configuration at the CacheManager level, it is a way for these caches
 * to share common configuration. For example a CacheManager might be clustered so all caches in that CacheManager
 * will participate in the same cluster.
 * <p/>
 * <p/>
 * <p/>
 * <h2>Creation</h2>
 * Concrete implementations can be created in a number of ways:
 * <ul>
 * <li>Through a ServiceLoader using {@link Caching}</li>
 * <li>Simple creation with <code>new</code> of a concrete implementation, if supported by an implementation</li>
 * </ul>
 * <p/>
 * <h2>Lookup</h2>
 * If Caching was used for creation, it will keep track of all CacheManagers created.
 * <p/>
 * The default CacheManager can be obtained using <code>Caching.getCacheManager()</code>. This is a
 * useful idiom if you only want to use one CacheManager.
 * <p/>
 * Named CacheManagers can be obtained using <code>Caching.getCacheManager(name)</code>.
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheManager {

    /**
     * Get the name of this cache manager
     *
     * @return the name of this cache manager
     */
    String getName();

    /**
     * Returns the status of this CacheManager.
     * <p/>
     * Calls to this method will block while the state is changing.
     * @return one of {@link Status}
     */
    Status getStatus();


    /**
     * Creates a new {@link CacheBuilder} for the named cache to be managed by this cache manager.
     * <p/>
     * An example which creates a cache using default cache configuration is:
     * <pre>
     *    Cache&lt;Integer, Date&gt; myCache2 = cacheManager.
     *           &lt;Integer, Date&gt;createCacheBuilder("myCache2").
     *           build();
     * </pre>
     * <p/>
     * An example usage which programmatically sets many parameters of {@link CacheConfiguration},
     * specifies a {@link CacheLoader} and registrs listeners is:
     * <pre>
     *    Cache&lt;Integer, String&gt; myCache1 = cacheManager.
     *           &lt;Integer, String&gt;createCacheBuilder("myCache1").
     *           setCacheLoader(cl).
     *           setStoreByValue(true).
     *           setReadThrough(true).
     *           setWriteThrough(false).
     *           setStatisticsEnabled(true).
     *           setTransactionEnabled(false).
     *           registerCacheEntryListener(listener1, NotificationScope.LOCAL, false).
     *           registerCacheEntryListener(listener2, NotificationScope.LOCAL, false).
     *           build();
     * </pre>
     * <p/>
     * <p/>
     * The returned CacheBuilder is associated with this CacheManager.
     * The Cache will be created, added to the caches controlled by this CacheManager and started when
     * {@link javax.cache.CacheBuilder#build()} is called.
     * If there is an existing Cache of the same name associated with this CacheManager when build is invoked,
     * an exception is thrown.
     *
     * @param cacheName the name of the cache to build. A cache name must consist of at least one non-whitespace character.
     * @return the CacheBuilder for the named cache
     * @throws IllegalStateException if the CacheManager is not in {@link Status#STARTED} state.
     * @throws CacheException        if a cache with that name already exists or there was an error adding the cache to the CacheManager
     * @throws IllegalArgumentException if an illegal cache name is specified
     * @throws NullPointerException  if the cache name is null
     */
    <K, V> CacheBuilder<K, V> createCacheBuilder(String cacheName);

    /**
     * Looks up a named cache.
     *
     * @param cacheName the name of the cache to look for
     * @return the Cache or null if it does exist
     * @throws IllegalStateException if the CacheManager is not {@link Status#STARTED}
     */
    <K, V> Cache<K, V> getCache(String cacheName);

    /**
     * Returns an Iterable over the caches managed by this CacheManager.
     * The Iterable is immutable (iterator.remove will throw an IllegalStateException) and independent
     * of the cache manager; if the caches managed by the cache manager change the Iterable is not affected
     *
     * @return an Iterable over the managed Caches
     * @throws UnsupportedOperationException if an attempt it made to remove an element
     */
    Iterable<Cache<?, ?>> getCaches();

    /**
     * Remove a cache from the CacheManager. The cache will be stopped.
     *
     * @param cacheName the cache name
     * @return true if the cache was removed
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws NullPointerException if cacheName is null
     */
    boolean removeCache(String cacheName);

    /**
     * This method will return a UserTransaction.
     *
     * @return the UserTransaction.
     * @throws UnsupportedOperationException if JTA is not supported
     */
    UserTransaction getUserTransaction();


    /**
     * Indicates whether a optional feature is supported by this CacheManager.
     *
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    boolean isSupported(OptionalFeature optionalFeature);

    /**
     * Shuts down the CacheManager.
     * <p/>
     * For each cache in the cache manager the {@link javax.cache.Cache#stop()}
     * method will be invoked, in no guaranteed order.
     * If the stop throws an exception, the exception is ignored.
     * <p/>
     * Calls to {@link #getStatus()} will block until shutdown completes.
     * <p/>
     * On completion the CacheManager's status is changed to {@link Status#STOPPED},
     * and the manager's owned caches will be empty and {@link #getCaches()}
     * will return an empty collection.
     * <p/>
     * A given CacheManager instance cannot be restarted after it has been stopped. A new one must be created.
     * @throws IllegalStateException if an operation is performed on CacheManager while stopping or stopped.
     */
    void shutdown();
    
    /**
     * Return an object of the specified type to allow access to the provider-specific API. If the provider's
     * implementation does not support the specified class, the {@link IllegalArgumentException} is thrown.
     * 
     * @param cls the class of the object to be returned. This is normally either the
     *            underlying implementation class or an interface that it implements.
     * @return an instance of the specified class 
     * @throws IllegalArgumentException if the provider doesn't support the specified class.
     */
    <T> T unwrap(java.lang.Class<T> cls);
}
