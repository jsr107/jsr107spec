/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.ServiceFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * A factory for creating CacheManager using the SPI conventions in the JDK's {@link ServiceLoader}
 *
 * For a provider to be discovered by the CacheManagerFactory, it's jar must contain a resource
 * called:
 * <pre>
 *   META-INF/services/javax.cache.spi.ServiceFactory
 * </pre>
 * containing the class name implementing {@link ServiceFactory}
 *
 * e.g. For the reference implementation:
 *
 * "javax.cache.implementation.RIServiceFactory"
 *
 * @see java.util.ServiceLoader
 * @see ServiceFactory
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public enum CacheManagerFactory {
    /**
     * The singleton instance using the Joshua Bloc enum-based singleton pattern.
     */
    INSTANCE;

    /**
     * The name of the default cache manager.
     * This is the name of the CacheManager returned when {@link #getCacheManager()} is invoked.
     * The default CacheManager is always created.
     */
    public static final String DEFAULT_CACHE_MANAGER_NAME = "default";

    private final ServiceFactory serviceFactory;
    private final HashMap<String, CacheManager> cacheManagers = new HashMap<String, CacheManager>();

    private CacheManagerFactory() {
        serviceFactory = getServiceFactory();
    }

    private ServiceFactory getServiceFactory() {
        ServiceLoader<ServiceFactory> serviceLoader = ServiceLoader.load(ServiceFactory.class);
        Iterator<ServiceFactory> it = serviceLoader.iterator();
        return it.hasNext() ? it.next() : null;
    }

    /**
     * Get the default cache manager.
     * The default cache manager is named {@link #DEFAULT_CACHE_MANAGER_NAME}
     *
     * @return the default cache manager
     * @throws IllegalStateException if no ServiceFactory was found
     */
    public CacheManager getCacheManager() {
        return getCacheManager(DEFAULT_CACHE_MANAGER_NAME);
    }

    /**
     * Get a named cache manager.
     * The first time a name is used, the cache manager will be created.
     * Subsequent calls will return the same cache manager.
     *
     * @param name the name of this cache manager
     * @return the new cache manager
     * @throws NullPointerException if name is null
     * @throws IllegalStateException if no ServiceFactory was found
     */
    public CacheManager getCacheManager(String name) {
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (serviceFactory == null) {
            throw new IllegalStateException("ServiceFactory");
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
}
