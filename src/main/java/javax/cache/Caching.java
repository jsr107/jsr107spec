/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.spi.CachingProvider;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ServiceLoader;
import java.util.WeakHashMap;

/**
 * The bootstrap and helper class for locating and managing CachingProvider
 * implementations.
 * <p/>
 * This implementation follows the SPI conventions outlined by the JDK
 * {@link ServiceLoader} class.
 * <p/>
 * For a CachingProvider to be automatically discovered by this class, the
 * CachingProvider implementation class name must be declared in the following
 * file:
 * <pre>
 *   META-INF/services/javax.cache.spi.CachingProvider
 * </pre>
 * that of which must be available on the classpath.  Alternatively if the
 * system property <code>javax.cache.cachingprovider</code> is defined, the
 * specified value is used as the name of the CachingProvider implementation.
 * <p/>
 * For example, in the reference implementation the contents of the file are:
 * <code>org.jsr107.ri.RICachingProvider</code>
 * <p/>
 * Multiple CachingProvider declarations are permitted, however in such circumstances
 * calls to {@link #getCachingProvider()} will raise an exception as this method
 * expects only a single CachingProvider to be configured.
 * <p/>
 * This class additionally keeps track of CachingProviders that have been previously
 * loaded.  Hence requests for previously loaded CachingProviders will simply be
 * returned, without reloading and/or instantiating them.
 * <p/>
 * Applications and/or Containers are not required to use this class to load
 * and/or manage CachingProviders.  They may instead choose to load and manage
 * CachingProviders independently of the services provided by this class.
 *
 * @author Brian Oliver
 *
 * @see java.util.ServiceLoader
 * @see javax.cache.spi.CachingProvider
 */
public final class Caching {

    /**
     * The javax.cache.cachingprovider constant.
     */
    public static final String JAVAX_CACHE_CACHINGPROVIDER = "javax.cache.cachingprovider";

    /**
     * The CachingProviderManager that manages the CachingProviders.
     */
    private static final CachingProviderManager CACHING_PROVIDERS = new CachingProviderManager();

    /**
     * No public constructor as all methods are static.
     */
    private Caching() {
    }

    /**
     * Obtains the ClassLoader to use for API methods that don't explicitly require
     * a ClassLoader but internally require one.
     * <p/>
     * By default this is the {@link Thread#getContextClassLoader()}.
     *
     * @return  the default ClassLoader
     */
    public static ClassLoader getDefaultClassLoader() {
        return CACHING_PROVIDERS.getDefaultClassLoader();
    }

    /**
     * Obtains the ClassLoader to use for API methods that don't explicitly require
     * a ClassLoader but internally require one.
     * <p/>
     * By default this is the {@link Thread#getContextClassLoader()}.
     *
     * @return  the default ClassLoader
     */
    public static CachingProvider getCachingProvider() {
        return CACHING_PROVIDERS.getCachingProvider();
    }

    /**
     * Obtains the only CachingProvider defined by the specified ClassLoader.
     * <p/>
     * Should zero or more than one CachingProviders be available, a CacheException is
     * thrown.
     *
     * @see #getCachingProviders(ClassLoader)
     *
     * @param classLoader  the ClassLoader to use for loading the CachingProvider
     *
     * @return the CachingProvider
     *
     * @throws CacheException should zero or more than one CachingProvider be available
     *                        or a CachingProvider could not be loaded
     */
    public static CachingProvider getCachingProvider(ClassLoader classLoader) {
        return CACHING_PROVIDERS.getCachingProvider(classLoader);
    }

    /**
     * Obtain the CachingProviders that are available via the {@link #getDefaultClassLoader()}.
     * <p/>
     * If a <code>javax.cache.cachingprovider</code> system property is defined,
     * only that CachingProvider specified by that property is returned.
     * Otherwise all CachingProviders that are available via a ServiceLoader
     * for CachingProviders using the default ClassLoader (and those explicitly
     * requested via {@link #getCachingProvider(String)}) are returned.
     *
     * @return an Iterable of CachingProviders loaded by the specified ClassLoader
     */
    public static Iterable<CachingProvider> getCachingProviders() {
        return CACHING_PROVIDERS.getCachingProviders();
    }

    /**
     * Obtain the CachingProviders that are available via the specified ClassLoader.
     * <p/>
     * If a <code>javax.cache.cachingprovider</code> system property is defined,
     * only that CachingProvider specified by that property is returned.
     * Otherwise all CachingProviders that are available via a ServiceLoader
     * for CachingProviders using the specified ClassLoader (and those explicitly
     * requested via {@link #getCachingProvider(String, ClassLoader)}) are
     * returned.
     *
     * @param classLoader  the ClassLoader of the returned CachingProviders
     *
     * @return an Iterable of CachingProviders loaded by the specified ClassLoader
     */
    public static Iterable<CachingProvider> getCachingProviders(ClassLoader classLoader) {
        return CACHING_PROVIDERS.getCachingProviders(classLoader);
    }

    /**
     * Obtain the CachingProvider that is implemented by the specified class
     * name using the {@link #getDefaultClassLoader()}.   Should this CachingProvider
     * already be loaded it is simply returned, otherwise an attempt will be
     * made to load and instantiate the specified class name (using a no-args constructor).
     *
     * @param fullyQualifiedClassName  the fully qualified class name of the CachingProvider
     *
     * @return the CachingProvider
     *
     * @throws CacheException  when the CachingProvider can't be created
     */
    public static CachingProvider getCachingProvider(String fullyQualifiedClassName) {
        return CACHING_PROVIDERS.getCachingProvider(fullyQualifiedClassName);
    }

    /**
     * Obtain the CachingProvider that is implemented by the specified class
     * name using the provided ClassLoader.   Should this CachingProvider already be
     * loaded it is returned, otherwise an attempt will be made to load and
     * instantiate the specified class name (using a no-args constructor).
     *
     * @param fullyQualifiedClassName  the fully qualified class name of the CachingProvider
     * @param classLoader              the ClassLoader to load the CachingProvider
     *
     * @return the CachingProvider
     *
     * @throws CacheException  when the CachingProvider can't be created
     */
    public static CachingProvider getCachingProvider(String fullyQualifiedClassName, ClassLoader classLoader) {
        return CACHING_PROVIDERS.getCachingProvider(fullyQualifiedClassName, classLoader);
    }

    /**
     * Manages one or more CachingProviders scoped by ClassLoader.
     */
    public static class CachingProviderManager {

        /**
         * The CachingProviders by Class Name organized by the ClassLoader was used to
         * load them.
         */
        private WeakHashMap<ClassLoader, LinkedHashMap<String, CachingProvider>> cachingProviders;

        /**
         * Constructs a CachingProviderManager.
         */
        public CachingProviderManager() {
            this.cachingProviders = new WeakHashMap<ClassLoader, LinkedHashMap<String, CachingProvider>>();
        }

        /**
         * Obtains the ClassLoader to use for API methods that don't explicitly require
         * a ClassLoader but internally require one.
         * <p/>
         * By default this is the {@link Thread#getContextClassLoader()}.
         *
         * @return  the default ClassLoader
         */
        public ClassLoader getDefaultClassLoader() {
            return Thread.currentThread().getContextClassLoader();
        }

        /**
         * Obtains the only CachingProvider defined by the {@link #getDefaultClassLoader()}.
         * <p/>
         * Should zero or more than one CachingProviders be available, a CacheException is
         * thrown.
         *
         * @see #getCachingProvider(ClassLoader)
         * @see #getCachingProviders(ClassLoader)
         *
         * @return the CachingProvider
         *
         * @throws CacheException should zero or more than one CachingProvider be available
         *                        or a CachingProvider could not be loaded
         */
        public CachingProvider getCachingProvider() {
            return getCachingProvider(getDefaultClassLoader());
        }

        /**
         * Obtains the only CachingProvider defined by the specified ClassLoader.
         * <p/>
         * Should zero or more than one CachingProviders be available, a CacheException is
         * thrown.
         *
         * @see #getCachingProviders(ClassLoader)
         *
         * @param classLoader  the ClassLoader to use for loading the CachingProvider
         *
         * @return the CachingProvider
         *
         * @throws CacheException should zero or more than one CachingProvider be available
         *                        or a CachingProvider could not be loaded
         */
        public CachingProvider getCachingProvider(ClassLoader classLoader) {
            Iterator<CachingProvider> iterator = getCachingProviders(classLoader).iterator();

            if (iterator.hasNext()) {
                CachingProvider provider = iterator.next();

                if (iterator.hasNext()) {
                    throw new CacheException("Multiple CachingProviders have been configured when only a single CachingProvider is expected");
                } else {
                    return provider;
                }
            } else {
                throw new CacheException("No CachingProviders have been configured");
            }
        }

        /**
         * Obtain the CachingProviders that are available via the {@link #getDefaultClassLoader()}.
         * <p/>
         * If a <code>javax.cache.cachingprovider</code> system property is defined,
         * only that CachingProvider specified by that property is returned.
         * Otherwise all CachingProviders that are available via a ServiceLoader
         * for CachingProviders using the default ClassLoader (and those explicitly
         * requested via {@link #getCachingProvider(String)}) are returned.
         *
         * @return an Iterable of CachingProviders loaded by the specified ClassLoader
         */
        public Iterable<CachingProvider> getCachingProviders() {
            return getCachingProviders(getDefaultClassLoader());
        }

        /**
         * Obtain the CachingProviders that are available via the specified ClassLoader.
         * <p/>
         * If a <code>javax.cache.cachingprovider</code> system property is defined,
         * only that CachingProvider specified by that property is returned.
         * Otherwise all CachingProviders that are available via a ServiceLoader
         * for CachingProviders using the specified ClassLoader (and those explicitly
         * requested via {@link #getCachingProvider(String, ClassLoader)}) are
         * returned.
         *
         * @param classLoader  the ClassLoader of the returned CachingProviders
         *
         * @return an Iterable of CachingProviders loaded by the specified ClassLoader
         */
        public synchronized Iterable<CachingProvider> getCachingProviders(ClassLoader classLoader) {

            final ClassLoader serviceClassLoader = classLoader == null ? getDefaultClassLoader() : classLoader;
            LinkedHashMap<String, CachingProvider> providers = cachingProviders.get(serviceClassLoader);

            if (providers == null) {

                if (System.getProperties().containsKey(JAVAX_CACHE_CACHINGPROVIDER)) {
                    String className = System.getProperty(JAVAX_CACHE_CACHINGPROVIDER);
                    providers = new LinkedHashMap<String, CachingProvider>();
                    providers.put(className, loadCachingProvider(className, serviceClassLoader));

                } else {
                    providers = AccessController.doPrivileged(new PrivilegedAction<LinkedHashMap<String, CachingProvider>>() {
                        @Override
                        public LinkedHashMap<String, CachingProvider> run() {
                            LinkedHashMap<String, CachingProvider> result = new LinkedHashMap<String, CachingProvider>();

                            ServiceLoader<CachingProvider> serviceLoader = ServiceLoader.load(CachingProvider.class, serviceClassLoader);
                            for (CachingProvider provider : serviceLoader) {
                                result.put(provider.getClass().getName(), provider);
                            }
                            return result;
                        }
                    });

                }

                cachingProviders.put(serviceClassLoader, providers);
            }

            return providers.values();
        }

        /**
         * Obtain the CachingProvider that is implemented by the specified class
         * name using the {@link #getDefaultClassLoader()}.   Should this CachingProvider
         * already be loaded it is simply returned, otherwise an attempt will be
         * made to load and instantiate the specified class name (using a no-args constructor).
         *
         * @param fullyQualifiedClassName  the fully qualified class name of the CachingProvider
         *
         * @return the CachingProvider
         *
         * @throws CacheException  when the CachingProvider can't be created
         */
        public CachingProvider getCachingProvider(String fullyQualifiedClassName) {
            return getCachingProvider(fullyQualifiedClassName, getDefaultClassLoader());
        }

        /**
         * Load and instantiate the CachingProvider with the specified class name
         * using the provided ClassLoader
         *
         * @param fullyQualifiedClassName  the name of the CachingProvider class
         * @param classLoader              the ClassLoader to use
         *
         * @return a new CachingProvider instance
         *
         * @throws CacheException if the specified CachingProvider could not be loaded
         *                        or the specified class is not a CachingProvider
         */
        protected CachingProvider loadCachingProvider(String fullyQualifiedClassName, ClassLoader classLoader) throws CacheException {
            synchronized (classLoader) {
                try {
                    Class<?> clazz = classLoader.loadClass(fullyQualifiedClassName);
                    if (CachingProvider.class.isAssignableFrom(clazz)) {
                        return ((Class<CachingProvider>)clazz).newInstance();
                    } else {
                        throw new CacheException("The specified class [" + fullyQualifiedClassName + "] is not a CachingProvider");
                    }
                } catch (Exception e) {
                    throw new CacheException("Failed to load the CachingProvider [" + fullyQualifiedClassName + "]", e);
                }
            }
        }

        /**
         * Obtain the CachingProvider that is implemented by the specified class
         * name using the provided ClassLoader.   Should this CachingProvider already be
         * loaded it is returned, otherwise an attempt will be made to load and
         * instantiate the specified class name (using a no-args constructor).
         *
         * @param fullyQualifiedClassName  the fully qualified class name of the CachingProvider
         * @param classLoader              the ClassLoader to load the CachingProvider
         *
         * @return the CachingProvider
         *
         * @throws CacheException  when the CachingProvider can't be created
         */
        public synchronized CachingProvider getCachingProvider(String fullyQualifiedClassName, ClassLoader classLoader) {
            ClassLoader serviceClassLoader = classLoader == null ? getDefaultClassLoader() : classLoader;

            LinkedHashMap<String, CachingProvider> providers = cachingProviders.get(serviceClassLoader);

            if (providers == null) {
                // first load the CachingProviders for the ClassLoader
                // this may automatically load the CachingProvider we desire
                getCachingProviders(serviceClassLoader);
                providers = cachingProviders.get(serviceClassLoader);
            }

            CachingProvider provider = providers.get(fullyQualifiedClassName);

            if (provider == null) {
                provider = loadCachingProvider(fullyQualifiedClassName, serviceClassLoader);
                providers.put(fullyQualifiedClassName, provider);
            }

            return provider;
        }
    }
}
