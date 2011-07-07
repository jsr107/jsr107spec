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
 * @see ServiceLoader
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public enum CacheManagerFactory {

    /**
     * The singleton instance.
     */
    instance;

    private final ServiceFactory serviceFactory;
    private CacheManager cacheManager;

    private CacheManagerFactory() {
        serviceFactory = getServiceFactory();
    }

    private ServiceFactory getServiceFactory() {
        ServiceLoader<ServiceFactory> serviceLoader = ServiceLoader.load(ServiceFactory.class);
        for (ServiceFactory sf : serviceLoader) {
            return sf;
        }
        return null;
    }

    /**
     * Get the cache manager.
     * todo how do we create multiple CacheManagers with this approach?
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
