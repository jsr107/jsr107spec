/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.CachingProvider;
import javax.transaction.UserTransaction;
import java.net.URI;
import java.util.Properties;

/**
 * A CacheManager is used for establishing, looking up and managing the lifecycle
 * of zero or more Caches.
 * <p/>
 * To the extent that implementations have configuration at the CacheManager level,
 * it is a way for these caches to share common configuration. For example a
 * CacheManager might be clustered so all caches in that CacheManager will
 * participate in the same cluster.
 * <p/>
 * <h2>Creation</h2>
 * Concrete implementations can be created in a number of ways:
 * <ul>
 * <li>Through a ServiceLoader using {@link Caching}</li>
 * <li>Simple creation with <code>new</code> of a concrete implementation, if
 * supported by an implementation</li>
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
 * @author Brian Oliver
 * @since 1.0
 */
public interface CacheManager {

    /**
     * Obtain the CachingProvider that created and is responsible for
     * this CacheManager.
     *
     * @return  the CachingProvider or <code>null</code> if the CacheManager
     *          was created without using a CachingProvider
     */
    CachingProvider getCachingProvider();

    /**
     * Get the URI of this CacheManager.
     *
     * @return the URI of this CacheManager
     */
    URI getURI();

    /**
     * Get the Properties that were used to create this CacheManager.
     *
     * @return the Properties used to create the CacheManager
     */
    Properties getProperties();

    /**
     * Returns the status of this CacheManager.
     * <p/>
     * Calls to this method will block while the state is changing.
     *
     * @return one of {@link Status}
     */
    Status getStatus();

    /**
     * Ensures that a {@link Cache} conforming to the specified {@link Configuration}
     * is being managed by the {@link CacheManager}.  If such a {@link Cache} is unknown
     * to the {@link CacheManager}, one is created and configured according to the provided
     * configuration, after which it becomes managed by the said {@link CacheManager}.
     * If such a {@link Cache} already exists, it is simply returned.
     * <p/>
     * Importantly {@link Configuration}s provided to this method are always
     * validated with in the context of the {@link CacheManager} implementation.
     * For example:  Attempting use a {@link Configuration} requiring transactional
     * support with an implementation that does not support transactions will result
     * in an {@link UnsupportedOperationException}.
     * <p/>
     * Note 1: Implementers of this method are required to make a copy of the provided
     * {@link Configuration} so that it may be further used to configure and
     * ensure other {@link Cache}s without causing side-effects.
     * <p/>
     * Note 2: There's no requirement on the part of a developer to call this method
     * for each {@link Cache} than an application may use.  This is simply because
     * when instantiated a {@link CacheManager} may be pre-configured with one or more
     * {@link Cache}s, thus meaning there's no requirement to "configure" them
     * in an application.  In such circumstances a developer may simply call
     * {@link #getCache(String)} to retrieve a pre-configured {@link Cache}.
     *
     * @param cacheName     the name of the cache
     * @param configuration the {@link Configuration}
     * @return a configured {@link Cache}
     * @throws IllegalStateException         if the CacheManager is not in {@link Status#STARTED} state
     * @throws CacheException                if there was an error adding the cache to the CacheManager
     * @throws IllegalArgumentException when the {@link Configuration} is invalid
     * @throws UnsupportedOperationException when the {@link Configuration} attempts
     *                                       to use an unsupported feature
     * @throws NullPointerException          if the cache configuration is null
     */
    <K, V> Cache<K, V> configureCache(String cacheName, Configuration<K, V> configuration) throws IllegalArgumentException;

    /**
     * Looks up a {@link Cache} given it's name.
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
     * @throws NullPointerException  if cacheName is null
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
     * Enables or disables statistics gathering for a cache at runtime.
     * <p/>
     * Each cache's statistics object must be registered with an ObjectName that is unique and has the following type and attributes:
     * <p/>
     * Type:
     * <code>javax.cache:type=CacheStatistics</code>
     * <p/>
     * Required Attributes:
     * <ul>
     * <li>CacheManager the name of the CacheManager
     * <li>Cache the name of the Cache
     * </ul>
     *
     * @param cacheName the name of the cache to register
     * @param enabled true to enable statistics, false to disable.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws NullPointerException  if cacheName is null
     */
    void enableStatistics(String cacheName, boolean enabled);


    /**
     * Controls whether management is enabled. If enabled the {@link CacheMXBean} for each cache is registered
     * in the platform MBean server. THe platform MBeanServer is obtained using {@link java.lang.management.ManagementFactory#getPlatformMBeanServer()}
     * <p/>
     * Managment information includes the name and configuration information for the cache.
     *
     Each cache's management object must be registered with an ObjectName that is unique and has the following type and attributes:
     * <p/>
     * Type:
     * <code>javax.cache:type=Cache</code>
     * <p/>
     * Required Attributes:
     * <ul>
     * <li>CacheManager the name of the CacheManager
     * <li>Cache the name of the Cache
     * </ul>
     *
     * @param cacheName the name of the cache to register
     * @param enabled true to enable management, false to disable.
     */
    void enableManagement(String cacheName, boolean enabled);



    /**
     * Closes the CacheManager.
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
     *
     * @throws IllegalStateException if an operation is performed on CacheManager while stopping or stopped.
     */
    void close();

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
