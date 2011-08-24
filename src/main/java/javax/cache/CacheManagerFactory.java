/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.ServiceProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * A factory for creating CacheManagers using the SPI conventions in the JDK's {@link ServiceLoader}
 * <p/>
 * For a provider to be discovered by the CacheManagerFactory, it's jar must contain a resource
 * called:
 * <pre>
 *   META-INF/services/javax.cache.spi.ServiceProvider
 * </pre>
 * containing the class name implementing {@link javax.cache.spi.ServiceProvider}
 * <p/>
 * e.g. For the reference implementation:
 * <p/>
 * "javax.cache.implementation.RIServiceFactory"
 * <p/>
 * The CacheManagerFactory also keeps track of all CacheManagers created by the factory. Subsequent calls
 * to {@link #getCacheManager()} return the same CacheManager.
 *
 * @author Yannis Cosmadopoulos
 * @see java.util.ServiceLoader
 * @see javax.cache.spi.ServiceProvider
 * @since 1.0
 */
public final class CacheManagerFactory {
    /**
     * The name of the default cache manager.
     * This is the name of the CacheManager returned when {@link #getCacheManager()} is invoked.
     * The default CacheManager is always created.
     */
    public static final String DEFAULT_CACHE_MANAGER_NAME = "__default__";

    /**
     * No public constructor as all methods are static.
     */
    private CacheManagerFactory() {
    }

    /**
     * Get the default cache manager.
     * The default cache manager is named {@link #DEFAULT_CACHE_MANAGER_NAME}
     *
     * @return the default cache manager
     * @throws IllegalStateException if no ServiceProvider was found
     */
    public static CacheManager getCacheManager() {
        return getCacheManager(DEFAULT_CACHE_MANAGER_NAME);
    }

    /**
     * Get the default cache manager.
     * The default cache manager is named {@link #DEFAULT_CACHE_MANAGER_NAME}
     *
     * @param classLoader the ClassLoader that should be used in converting values into Java Objects. May be null.
     * @return the default cache manager
     * @throws IllegalStateException if no ServiceProvider was found
     */
    public static CacheManager getCacheManager(ClassLoader classLoader) {
        return getCacheManager(classLoader, DEFAULT_CACHE_MANAGER_NAME);
    }

    /**
     * Get a named cache manager using the default cache loader as specified by
     * the implementation (see {@link javax.cache.spi.ServiceProvider#getDefaultClassLoader()}
     *
     * @param name the name of the cache manager
     * @return the named cache manager
     * @throws NullPointerException  if name is null
     * @throws IllegalStateException if no ServiceProvider was found
     */
    public static CacheManager getCacheManager(String name) {
        return CacheManagerFactorySingleton.INSTANCE.getCacheManager(name);
    }

    /**
     * Get a named cache manager.
     * <p/>
     * The first time a name is used, a new CacheManager is created.
     * Subsequent calls will return the same cache manager.
     * <p/>
     * During creation, the name of the CacheManager is passed through to {@link javax.cache.spi.ServiceProvider}
     * so that an implementation it to concrete implementations may use it to point to a specific configuration
     * used to configure the CacheManager. This allows CacheManagers to have different configurations. For example,
     * one CacheManager might be configured for standalone operation and another might be configured to participate
     * in a cluster.
     * <p/>
     * Generally, It makes sense that a CacheManager is associated with a ClassLoader. I.e. all caches emanating
     * from the CacheManager, all code including key and value classes must be present in that ClassLoader.
     * <p/>
     * Secondly, the CacheManagerFactory may be in a different ClassLoader than the
     * CacheManager (i.e. the CacheManagerFactory may be shared in an application server setting).
     * <p/>
     * For this purpose a ClassLoader may be specified. If specified it will be used for all conversion between
     * values and Java Objects. While Java's in-built serialization may be used other schemes may also be used.
     * Either way the specified ClassLoader will be used.
     * <p/>
     * The name parameter may be used to associate a configuration with this CacheManager instance.
     *
     * @param classLoader the ClassLoader that should be used in converting values into Java Objects.
     * @param name        the name of this cache manager
     * @return the new cache manager
     * @throws NullPointerException  if classLoader or name is null
     * @throws IllegalStateException if no ServiceProvider was found
     */
    public static CacheManager getCacheManager(ClassLoader classLoader, String name) {
        return CacheManagerFactorySingleton.INSTANCE.getCacheManager(classLoader, name);
    }

    /**
     * Reclaims all resources obtained from this factory.
     * <p/>
     * All cache managers obtained from the factory are shutdown.
     * <p/>
     * Subsequent requests from this factory will return different cache managers than would have been obtained before
     * shutdown. So for example
     * <pre>
     *  CacheManager cacheManager = factory.getCacheManager();
     *  assertSame(cacheManager, factory.getCacheManager());
     *  factory.shutdown();
     *  assertNotSame(cacheManager, factory.getCacheManager());
     * </pre>
     * @return true if found, false otherwise
     */
    public static boolean shutdown() {
        return CacheManagerFactorySingleton.INSTANCE.shutdown();
    }

    /**
     * Reclaims all resources for a ClassLoader from this factory.
     * <p/>
     * All cache managers linked to the specified CacheLoader obtained from the factory are shutdown.
     * @param classLoader the class loader for which managers will be shut down
     * @return true if found, false otherwise
     */
    public static boolean shutdown(ClassLoader classLoader) {
        return CacheManagerFactorySingleton.INSTANCE.shutdown(classLoader);
    }

    /**
     * Reclaims all resources for a ClassLoader from this factory.
     * <p/>
     * the named cache manager obtained from the factory is shutdown.
     * @param classLoader the class loader for which managers will be shut down
     * @param name the name of the cache manager
     * @return true if found, false otherwise
     */
    public static boolean shutdown(ClassLoader classLoader, String name) {
        return CacheManagerFactorySingleton.INSTANCE.shutdown(classLoader, name);
    }

    /**
     * Returns the status.
     *
     * @return the status
     */
    public static Status getStatus() {
        return CacheManagerFactorySingleton.INSTANCE.getStatus();
    }

    /**
     * Indicates whether a optional feature is supported by this implementation
     *
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    public static boolean isSupported(OptionalFeature optionalFeature) {
        return CacheManagerFactorySingleton.INSTANCE.isSupported(optionalFeature);
    }

    /**
     * Singleton with implementation
     */
    private enum CacheManagerFactorySingleton {

        /**
         * The singleton instance.
         */
        INSTANCE;

        private final ServiceProvider serviceFactory;
        private final HashMap<ClassLoader, HashMap<String, CacheManager>> cacheManagers = new HashMap<ClassLoader, HashMap<String, CacheManager>>();
        private volatile Status status;

        private CacheManagerFactorySingleton() {
            status = Status.UNINITIALISED;
            ServiceLoader<ServiceProvider> serviceLoader = ServiceLoader.load(ServiceProvider.class);
            Iterator<ServiceProvider> it = serviceLoader.iterator();
            serviceFactory = it.hasNext() ? it.next() : null;
            status = Status.STARTED;
        }

        private ServiceProvider getServiceFactory() {
            if (serviceFactory == null) {
                throw new IllegalStateException("No ServiceProvider found in classpath.");
            }
            return serviceFactory;
        }

        /**
         * Get a named cache manager using the default cache loader as specified by
         * the implementation (see {@link javax.cache.spi.ServiceProvider#getDefaultClassLoader()}
         *
         * @param name the name of the cache manager
         * @return the named cache manager
         * @throws NullPointerException  if name is null
         * @throws IllegalStateException if no ServiceProvider was found
         */
        public CacheManager getCacheManager(String name) {
            ClassLoader cl = getServiceFactory().getDefaultClassLoader();
            return getCacheManager(cl, name);
        }

        /**
         * Get the cache manager for the specified name and class loader.
         * <p/>
         * If there is no cache manager associated, it is created.
         *
         * @param classLoader associated with the cache manager.
         * @param name the name of the cache manager
         * @return the new cache manager
         * @throws NullPointerException if classLoader or name is null
         * @throws IllegalStateException if no ServiceProvider was found
         */
        public CacheManager getCacheManager(ClassLoader classLoader, String name) {
            if (classLoader == null) {
                throw new NullPointerException("classLoader");
            }
            if (name == null) {
                throw new NullPointerException("name");
            }
            synchronized (cacheManagers) {
                HashMap<String, CacheManager> map = cacheManagers.get(classLoader);
                if (map == null) {
                    map = new HashMap<String, CacheManager>();
                    cacheManagers.put(classLoader, map);
                }
                CacheManager cacheManager = map.get(name);
                if (cacheManager == null) {
                    cacheManager = getServiceFactory().createCacheManager(classLoader, name);
                    map.put(name, cacheManager);
                }
                return cacheManager;
            }
        }

        /**
         * Reclaims all resources obtained from this factory.
         * <p/>
         * All cache managers obtained from the factory are shutdown.
         * <p/>
         * Subsequent requests from this factory will return different cache managers than would have been obtained before
         * shutdown. So for example
         * <pre>
         *  CacheManager cacheManager = factory.getCacheManager();
         *  assertSame(cacheManager, factory.getCacheManager());
         *  factory.shutdown();
         *  assertNotSame(cacheManager, factory.getCacheManager());
         * </pre>
         * @return true if found, false otherwise
         */
        public boolean shutdown() {
            status = Status.STOPPING;
            Iterator<HashMap<String, CacheManager>> iterator;
            synchronized (cacheManagers) {
                iterator = new ArrayList<HashMap<String, CacheManager>>(cacheManagers.values()).iterator();
                cacheManagers.clear();
            }
            status = Status.STARTED;
            boolean hasElements = iterator.hasNext();
            while (iterator.hasNext()) {
                HashMap<String, CacheManager> cacheManagerMap = iterator.next();
                iterator.remove();
                shutdown(cacheManagerMap);
            }
            return hasElements;
        }

        /**
         * Reclaims all resources for a ClassLoader from this factory.
         * <p/>
         * All cache managers linked to the specified CacheLoader obtained from the factory are shutdown.
         * @param classLoader the class loader for which managers will be shut down
         * @return true if found, false otherwise
         */
        public boolean shutdown(ClassLoader classLoader) {
            HashMap<String, CacheManager> cacheManagerMap;
            synchronized (cacheManagers) {
                cacheManagerMap = cacheManagers.remove(classLoader);
            }
            if (cacheManagerMap != null) {
                shutdown(cacheManagerMap);
            }
            return cacheManagerMap != null;
        }

        /**
         * Reclaims all resources for a ClassLoader from this factory.
         * <p/>
         * the named cache manager obtained from the factory is shutdown.
         * @param classLoader the class loader for which managers will be shut down
         * @param name the name of the cache manager
         * @return true if found, false otherwise
         */
        public boolean shutdown(ClassLoader classLoader, String name) {
            CacheManager cacheManager;
            synchronized (cacheManagers) {
                HashMap<String, CacheManager> cacheManagerMap = cacheManagers.get(classLoader);
                cacheManager = cacheManagerMap.remove(name);
                if (cacheManagerMap.isEmpty()) {
                    cacheManagers.remove(classLoader);
                }
            }
            if (cacheManager != null) {
                shutdown(cacheManager);
            }
            return cacheManager != null;
        }

        /**
         * Returns the status.
         *
         * @return the status
         */
        public Status getStatus() {
            return status;
        }

        /**
         * Indicates whether a optional feature is supported by this implementation
         *
         * @param optionalFeature the feature to check for
         * @return true if the feature is supported
         */
        public boolean isSupported(OptionalFeature optionalFeature) {
            if (serviceFactory == null) {
                throw new IllegalStateException("ServiceProvider");
            }
            return serviceFactory.isSupported(optionalFeature);
        }

        private void shutdown(Map<String, CacheManager> cacheManagerMap) {
            Iterator<CacheManager> iterator = cacheManagerMap.values().iterator();
            while (iterator.hasNext()) {
                CacheManager cacheManager = iterator.next();
                iterator.remove();
                shutdown(cacheManager);
            }
        }

        private void shutdown(CacheManager cacheManager) {
            try {
                cacheManager.shutdown();
            } catch (Exception e) {
                // best effort on shutdown
            }
        }
    }
}
