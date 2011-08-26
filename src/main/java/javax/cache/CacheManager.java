/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import java.util.Set;

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
 * <li>Through a ServiceLoader using {@link CacheManagerFactory}</li>
 * <li>Simple creation with <code>new</code> of a concrete implementation, if supported by an implementation</li>
 * </ul>
 * <p/>
 * <h2>Lookup</h2>
 * If the CacheManagerFactory was used for creation, the factory will keep track of all CacheManagers created.
 * <p/>
 * The default CacheManager can be obtained using <code>CacheManagerFactory.INSTANCE.getCacheManager()</code>. This is a
 * useful idiom if you only want to use one CacheManager.
 * <p/>
 * Named CacheManagers can be obtained using <code>CacheManagerFactory.INSTANCE.getCacheManager(name)</code>.
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
     * @return one of {@link Status}
     */
    Status getStatus();


    /**
     * Creates a new {@link CacheBuilder} for the named cache to be managed by this cache manager.
     * <p/>
     * An example usage which passes in a specific programmatic {@link CacheConfiguration} and specifies a {@link CacheLoader} is:
     * <pre>
     *    cacheManager.createCacheBuilder("myCache").
     *      setCacheConfiguration(config).
     *      setCacheLoader(cl).
     *      build();
     * </pre>
     * <p/>
     * An example which creates a cache using default cache configuration is:
     * <pre>
     *    cacheManager.createCacheBuilder("myCache2"). build();
     * </pre>
     * <p/>
     * <p/>
     * The returned CacheBuilder is associated with this CacheManager.
     * The Cache will be created, added to the caches controlled by this CacheManager and started when
     * {@link javax.cache.CacheBuilder#build()} is called.
     * If there is an existing Cache of the same name associated with this CacheManager when build is invoked,
     * the old Cache will be stopped.
     *
     * @param cacheName the name of the cache to build
     * @return the CacheBuilder for the named cache
     * @throws IllegalStateException if the cache is not {@link Status#UNINITIALISED} before this method is called.
     * @throws CacheException        if there was an error adding the cache to the CacheManager
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
     * Returns a set of caches managed by this CacheManager.
     * This set is immutable and independent of the cache manager; if the set of caches owned
     * by the cache manager change the set is not affected.
     *
     * @return the Caches or an empty set if there are none
     */
    Set<Cache> getCaches();


    /**
     * Remove a cache from the CacheManager. The cache will be stopped.
     *
     * @param cacheName the cache name
     * @return true if the cache was removed
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws NullPointerException if cacheName is null
     */
    boolean removeCache(String cacheName) throws IllegalStateException;


    /**
     * Create a mutable {@link javax.cache.CacheConfiguration} instance.
     * The configuration returned will have the default values defined for the CacheManager .
     *
     * @return a cache configuration
     */
    CacheConfiguration createCacheConfiguration();


    /**
     * This method will return a UserTransaction.
     *
     * @return the UserTransaction. This should be cast to javax.transaction.UserTransaction.
     * @throws UnsupportedOperationException is JTA is not supported
     */
    Object getUserTransaction();


    /**
     * Indicates whether a optional feature is supported by this CacheManager.
     *
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    public boolean isSupported(OptionalFeature optionalFeature);

    /**
     * Shuts down the CacheManager.
     * <p/>
     * For each cache in the cache manager the {@link javax.cache.Cache#stop()} method will be invoked, in no guaranteed order.
     * If the stop throws an exception, the exception will be consumed silently.
     * During the execution of the method the status of CacheManager is {@link Status#STOPPING}.
     * On completion the CacheManager's status is changed to {@link Status#STOPPED}, and the manager's owned caches will be empty &amp;
     * {@link #getCaches()} will return an empty collection.
     * <p/>
     * A given CacheManager instance cannot be restarted after it has been stopped. A new one must be created.
     * @throws IllegalStateException if an operation is performed on CacheManager while stopping or stopped.
     */
    void shutdown();
}
