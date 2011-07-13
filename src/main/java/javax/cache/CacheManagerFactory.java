/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.ServiceFactory;
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
 * Subsequent calls to {@link #getCacheManager()} will return a singleton instance of a CacheManager.
 * In a caching framework singletons are often useful. However this API supports creation of
 * non-singleton CacheManager implementations by simply newing them.
 *
 * @see ServiceLoader
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public enum CacheManagerFactory {

    /**
     * The singleton instance using the Joshua Bloc enum-based singleton pattern.
     */
    INSTANCE;

    private final ServiceFactory serviceFactory;
    private CacheManager cacheManager;

    private CacheManagerFactory() {
        serviceFactory = getServiceFactory();
    }

    private ServiceFactory getServiceFactory() {
        ServiceLoader<ServiceFactory> serviceLoader = ServiceLoader.load(ServiceFactory.class);
        Iterator<ServiceFactory> it = serviceLoader.iterator();
        return it.hasNext() ? it.next() : null;
    }

    /**
     * Get the cache manager.
     *
     * @return the cache manager
     */
    public CacheManager getCacheManager() {
        if (cacheManager == null) {
            synchronized (this) {
                if (cacheManager == null && serviceFactory != null) {
                    cacheManager = serviceFactory.createCacheManager();
                }
            }
        }
        return cacheManager;
    }

}
