/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.CacheManagerFactoryProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * A factory for creating CacheManagers using the SPI conventions in the JDK's {@link ServiceLoader}
 *
 * For a provider to be discovered by the CacheManagerFactory, it's jar must contain a resource
 * called:
 * <pre>
 *   META-INF/services/javax.cache.spi.CacheManagerFactoryProvider
 * </pre>
 * containing the class name implementing {@link javax.cache.spi.CacheManagerFactoryProvider}
 *
 * e.g. For the reference implementation:
 *
 * "javax.cache.implementation.RIServiceFactory"
 *
 * The CacheManagerFactory also keeps track of all CacheManagers created by the factory. Subsequent calls
 * to {@link #getCacheManager()} return the same CacheManager.
 *
 * @see java.util.ServiceLoader
 * @see javax.cache.spi.CacheManagerFactoryProvider
 *
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public enum CacheManagerFactory {

    /**
     * The singleton instance.
     */
    INSTANCE;

    /**
     * The name of the default cache manager.
     * This is the name of the CacheManager returned when {@link #getCacheManager()} is invoked.
     * The default CacheManager is always created.
     */
    public static final String DEFAULT_CACHE_MANAGER_NAME = "__default__";

    private final CacheManagerFactoryProvider serviceFactory;
    private final HashMap<String, CacheManager> cacheManagers = new HashMap<String, CacheManager>();

    private CacheManagerFactory() {
        serviceFactory = getServiceFactory();
    }

    private CacheManagerFactoryProvider getServiceFactory() {
        ServiceLoader<CacheManagerFactoryProvider> serviceLoader = ServiceLoader.load(CacheManagerFactoryProvider.class);
        Iterator<CacheManagerFactoryProvider> it = serviceLoader.iterator();
        return it.hasNext() ? it.next() : null;
    }

    /**
     * Get the default cache manager.
     * The default cache manager is named {@link #DEFAULT_CACHE_MANAGER_NAME}
     *
     * @return the default cache manager
     * @throws IllegalStateException if no CacheManagerFactoryProvider was found
     */
    public CacheManager getCacheManager() {
        return getCacheManager(DEFAULT_CACHE_MANAGER_NAME);
    }

    /**
     * Get a named cache manager.
     * <p/>
     * The first time a name is used, a new CacheManager is created.
     * Subsequent calls will return the same cache manager.
     * <p/>
     * During creation, the name of the CacheManager is passed through to {@link CacheManagerFactoryProvider}
     * so that an implementation it to concrete implementations may use it to point to a specific configuration
     * used to configure the CacheManager. This allows CacheManagers to have different configurations. For example,
     * one CacheManager might be configured for standalone operation and another might be configured to participate
     * in a cluster.
     *
     * The name may be used to associate a configuration with this CacheManager instance.
     *
     *
     * @param name the name of this cache manager
     * @return the new cache manager
     * @throws NullPointerException if name is null
     * @throws IllegalStateException if no CacheManagerFactoryProvider was found
     */
    public CacheManager getCacheManager(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (serviceFactory == null) {
            throw new IllegalStateException("CacheManagerFactoryProvider");
        } else {
            synchronized (cacheManagers) {
                CacheManager cacheManager = cacheManagers.get(name);
                if (cacheManager == null) {
                    cacheManager = serviceFactory.createCacheManager(name);
                    cacheManagers.put(name, cacheManager);
                }
                return cacheManager;
            }
        }
    }

    /**
     * Create a mutable {@link javax.cache.CacheConfiguration} instance.
     * The configuration returned should have the default values.
     *
     * @return a cache configuration
     */
    public CacheConfiguration createCacheConfiguration() {
        if (serviceFactory == null) {
            throw new IllegalStateException("CacheManagerFactoryProvider");
        }
        return serviceFactory.createCacheConfiguration();
    }

    /**
     * Creates an unitialised cache instance with the given name and default configuration.
     * The returned cache much be added to a {@link CacheManager} to be started and used.
     * <p/>
     * todo Greg this only exists to allow TCK testing of the new creational pattern.
     * @param name the cache name
     * @return a new cache
     */
    public <K, V> Cache<K, V> createCache(String name) {
        if (serviceFactory == null) {
            throw new IllegalStateException("CacheManagerFactoryProvider");
        }
        return serviceFactory.createCache(name);
    }


    /**
     * Indicates whether a optional feature is supported by this implementation
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    public boolean isSupported(OptionalFeature optionalFeature) {
        if (serviceFactory == null) {
            throw new IllegalStateException("CacheManagerFactoryProvider");
        }
        return serviceFactory.isSupported(optionalFeature);
    }
}
