/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.ServiceFactory;
import java.util.ServiceLoader;

/**
 * Singleton used to access Cache top level elements.
 *
 * For a provider to hook into the Factory, the jar must contain a file:
 * <pre>
 *   META-INF/services/javax.cache.spi.ServiceFactory
 * </pre>
 * containing the class name implementing {@link ServiceFactory}
 * @see ServiceLoader
 *
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public enum Factory {
    /**
     * the singleton instance.
     */
    instance;

    private final ServiceFactory serviceFactory;
    private CacheManager cacheManager;

    private Factory() {
        serviceFactory = getServiceFactory();
    }

    private ServiceFactory getServiceFactory() {
        ServiceLoader<ServiceFactory> serviceLoader = ServiceLoader.load(ServiceFactory.class);
        for (ServiceFactory serviceFactory : serviceLoader) {
            return serviceFactory;
        }
        return null;
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
