/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *  Copyright (c) 2011-2016 Terracotta, Inc.
 *  Copyright (c) 2011-2016 Oracle and/or its affiliates.
 *
 *  All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
 * The {@link Caching} class provides a convenient means for an application to
 * acquire an appropriate {@link CachingProvider} implementation.
 * <p>
 * While defined as part of the specification, its use is not required.
 * Applications and/or containers may instead choose to directly instantiate a
 * {@link CachingProvider} implementation based on implementation specific
 * instructions.
 * <p>
 * When using the {@link Caching} class, {@link CachingProvider} implementations
 * are automatically discovered when they follow the conventions outlined by the
 * Java Development Kit {@link ServiceLoader} class.
 * <p>
 * Although automatically discovered, applications that choose to use this class
 * should not make assumptions regarding the order in which implementations are
 * returned by the {@link #getCachingProviders()} or
 * {@link #getCachingProviders(ClassLoader)} methods.
 * <p>
 * For a {@link CachingProvider} to be automatically discoverable by the
 * {@link Caching} class, the fully qualified class name of the
 * {@link CachingProvider} implementation must be declared in the following
 * file:
 * <pre>
 *   META-INF/services/javax.cache.spi.CachingProvider
 * </pre>
 * This file must be resolvable via the class path.
 * <p>
 * For example, in the reference implementation the contents of this file are:
 * <code>org.jsr107.ri.RICachingProvider</code>
 * <p>
 * Alternatively when the fully qualified class name of a
 * {@link CachingProvider} implementation is specified using the system property
 * <code>javax.cache.spi.cachingprovider</code>, that implementation will be used
 * as the default {@link CachingProvider}.
 * <p>
 * All {@link CachingProvider}s that are automatically detected or explicitly
 * declared and loaded by the {@link Caching} class are maintained in an
 * internal registry.  Consequently when a previously loaded
 * {@link CachingProvider} is requested, it will be simply returned from the
 * internal registry, without reloading and/or instantiating the said
 * implementation again.
 * <p>
 * As required by some applications and containers, multiple co-existing
 * {@link CachingProvider}s implementations, from the same or different
 * implementors are permitted at runtime.
 * <p>
 * To iterate through those that are currently registered a developer may use
 * the following methods:
 * <ol>
 * <li>{@link #getCachingProviders()}</li>
 * <li>{@link #getCachingProviders(ClassLoader)}</li>
 * </ol>
 * To request a specific {@link CachingProvider} implementation, a developer
 * should use either the {@link #getCachingProvider(String)} or
 * {@link #getCachingProvider(String, ClassLoader)} method.
 * <p>
 * Where multiple {@link CachingProvider}s are present, the
 * {@link CachingProvider} returned by getters {@link #getCachingProvider()} and
 * {@link #getCachingProvider(ClassLoader)} is undefined and as a result a
 * {@link CacheException} will be thrown when attempted.
 *
 * @author Brian Oliver
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 * @see ServiceLoader
 * @see CachingProvider
 */
public final class Caching {

  /**
   * The <code>javax.cache.spi.cachingprovider</code> constant.
   */
  public static final String JAVAX_CACHE_CACHING_PROVIDER = "javax.cache" +
      ".spi.CachingProvider";

  /**
   * The {@link CachingProviderRegistry} that tracks the {@link CachingProvider}s.
   */
  private static final CachingProviderRegistry CACHING_PROVIDERS =
      new CachingProviderRegistry();

  /**
   * No public constructor as all methods are static.
   */
  private Caching() {
  }

  /**
   * Obtains the {@link ClassLoader} to use for API methods that don't
   * explicitly require a {@link ClassLoader} but internally require one.
   * <p>
   * By default this is the {@link Thread#getContextClassLoader()}.
   *
   * @return the default {@link ClassLoader}
   */
  public static ClassLoader getDefaultClassLoader() {
    return CACHING_PROVIDERS.getDefaultClassLoader();
  }

  /**
   * Set the {@link ClassLoader} to use for API methods that don't explicitly
   * require a {@link ClassLoader}, but internally use one.
   *
   * @param classLoader the {@link ClassLoader} or <code>null</code> if the
   *                    calling {@link Thread#getContextClassLoader()} should
   *                    be used
   */
  public static void setDefaultClassLoader(ClassLoader classLoader) {
    CACHING_PROVIDERS.setDefaultClassLoader(classLoader);
  }

  /**
   * Obtains the default {@link CachingProvider} available via the
   * {@link #getDefaultClassLoader()}.
   *
   * @return the {@link CachingProvider}
   * @throws CacheException    should zero, or more than one
   *                           {@link CachingProvider} be available on the
   *                           classpath, or it could not be loaded
   * @throws SecurityException when the operation could not be performed
   *                           due to the current security settings
   */
  public static CachingProvider getCachingProvider() {
    return CACHING_PROVIDERS.getCachingProvider();
  }

  /**
   * Obtains the single {@link CachingProvider} visible to the specified
   * {@link ClassLoader}.
   *
   * @param classLoader the {@link ClassLoader} to use for loading the
   *                    {@link CachingProvider}
   * @return the {@link CachingProvider}
   * @throws CacheException    should zero, or more than one
   *                           {@link CachingProvider} be available on the
   *                           classpath, or it could not be loaded
   * @throws SecurityException when the operation could not be performed
   *                           due to the current security settings
   * @see #getCachingProviders(ClassLoader)
   */
  public static CachingProvider getCachingProvider(ClassLoader classLoader) {
    return CACHING_PROVIDERS.getCachingProvider(classLoader);
  }

  /**
   * Obtains the {@link CachingProvider}s that are available via the
   * {@link #getDefaultClassLoader()}.
   * <p>
   * If a <code>javax.cache.spi.cachingprovider</code> system property is defined,
   * only that {@link CachingProvider} specified by that property is returned.
   * Otherwise all {@link CachingProvider}s that are available via a
   * {@link ServiceLoader} for {@link CachingProvider}s using the default
   * {@link ClassLoader} (including those previously requested via
   * {@link #getCachingProvider(String)}) are returned.
   *
   * @return an {@link Iterable} of {@link CachingProvider}s loaded by the
   *         specified {@link ClassLoader}
   */
  public static Iterable<CachingProvider> getCachingProviders() {
    return CACHING_PROVIDERS.getCachingProviders();
  }

  /**
   * Obtains the {@link CachingProvider}s that are available via the specified
   * {@link ClassLoader}.
   * <p>
   * If a <code>javax.cache.spi.cachingprovider</code> system property is defined,
   * only that {@link CachingProvider} specified by that property is returned.
   * Otherwise all {@link CachingProvider}s that are available via a
   * {@link ServiceLoader} for {@link CachingProvider}s using the specified
   * {@link ClassLoader} (including those previously requested via
   * {@link #getCachingProvider(String, ClassLoader)}) are returned.
   *
   * @param classLoader the {@link ClassLoader} of the returned
   *                    {@link CachingProvider}s
   * @return an {@link Iterable} of {@link CachingProvider}s loaded by the
   *         specified {@link ClassLoader}
   */
  public static Iterable<CachingProvider> getCachingProviders(
      ClassLoader classLoader) {
    return CACHING_PROVIDERS.getCachingProviders(classLoader);
  }

  /**
   * Obtain the {@link CachingProvider} that is implemented by the specified
   * fully qualified class name using the {@link #getDefaultClassLoader()}.
   * Should this {@link CachingProvider} already be loaded it is simply returned,
   * otherwise an attempt will be made to load and instantiate the specified
   * class (using a no-args constructor).
   *
   * @param fullyQualifiedClassName the fully qualified class name of the
   *                                {@link CachingProvider}
   * @return the {@link CachingProvider}
   * @throws CacheException    if the {@link CachingProvider} cannot be created
   * @throws SecurityException when the operation could not be performed
   *                           due to the current security settings
   */
  public static CachingProvider getCachingProvider(String fullyQualifiedClassName) {
    return CACHING_PROVIDERS.getCachingProvider(fullyQualifiedClassName);
  }

  /**
   * Obtain the {@link CachingProvider} that is implemented by the specified
   * fully qualified class name using the provided {@link ClassLoader}.
   * Should this {@link CachingProvider} already be loaded it is returned,
   * otherwise an attempt will be made to load and instantiate the specified
   * class (using a no-args constructor).
   *
   * @param fullyQualifiedClassName the fully qualified class name of the
   *                                {@link CachingProvider}
   * @param classLoader             the {@link ClassLoader} to load the
   *                                {@link CachingProvider}
   * @return the {@link CachingProvider}
   * @throws CacheException    if the {@link CachingProvider} cannot be created
   * @throws SecurityException when the operation could not be performed
   *                           due to the current security settings
   */
  public static CachingProvider getCachingProvider(String fullyQualifiedClassName,
                                                   ClassLoader classLoader) {
    return CACHING_PROVIDERS.getCachingProvider(fullyQualifiedClassName,
        classLoader);
  }


  /**
   * A convenience that method that looks up a managed {@link Cache} given its
   * name. using the default <code>CachingProvider</code> and <code>CacheManager
   * </code>. For the full range of <code>Cache</code> look up methods see
   * {@link CacheManager}.
   * <p>
   * This method must be used for {@link Cache}s that were configured with
   * runtime key and value types. Use {@link CacheManager#getCache(String)} for
   * {@link Cache}s where these were not specified.
   * <p>
   * Implementations must ensure that the key and value types are the same as
   * those configured for the {@link Cache} prior to returning from this method.
   * <p>
   * Implementations may further perform type checking on mutative cache operations
   * and throw a {@link ClassCastException} if these checks fail.
   * <p>
   * Implementations that support declarative mechanisms for pre-configuring
   * {@link Cache}s may return a pre-configured {@link Cache} instead of
   * <code>null</code>.
   * @param <K> the type of key
   * @param <V> the type of value
   * @param cacheName the name of the managed {@link Cache} to acquire
   * @param keyType   the expected {@link Class} of the key
   * @param valueType the expected {@link Class} of the value
   * @return the Cache or null if it does exist or can't be pre-configured
   * @throws IllegalStateException    if the CacheManager is
   *                                  {@link CacheManager#isClosed()}
   * @throws IllegalArgumentException if the specified key and/or value types are
   *                                  incompatible with the configured cache.
   * @throws SecurityException        when the operation could not be performed
   *                                  due to the current security settings
   * @see CacheManager#getCache(String, Class, Class)
   * @see CacheManager#getCache(String)
   */
  public static <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType,
                              Class<V> valueType) {

    return getCachingProvider().getCacheManager().getCache(cacheName, keyType,
        valueType);
  }

  /**
   * Maintains a registry of loaded {@link CachingProvider}s scoped by
   * {@link ClassLoader}.
   */
  private static class CachingProviderRegistry {

    /**
     * The {@link CachingProvider}s by Class Name organized by the
     * {@link ClassLoader} was used to load them.
     */
    private WeakHashMap<ClassLoader, LinkedHashMap<String, CachingProvider>>
        cachingProviders;

    /**
     * The default {@link ClassLoader}.  When <code>null</code> the
     * {@link Thread#getContextClassLoader()} will be used.
     */
    private volatile ClassLoader classLoader;

    /**
     * Constructs a CachingProviderManager.
     */
    public CachingProviderRegistry() {
      this.cachingProviders = new WeakHashMap<ClassLoader, LinkedHashMap<String,
          CachingProvider>>();
      this.classLoader = null;
    }

    /**
     * Obtains the {@link ClassLoader} to use for API methods that don't
     * explicitly require a {@link ClassLoader} but internally require one.
     * <p>
     * By default this is the {@link Thread#getContextClassLoader()}.
     * </p>
     * @return the default {@link ClassLoader}
     */
    public ClassLoader getDefaultClassLoader() {
      ClassLoader loader = classLoader;
      return loader == null ? Thread.currentThread().getContextClassLoader() : loader;
    }

    /**
     * Set the {@link ClassLoader} to use for API methods that don't explicitly
     * require a {@link ClassLoader}, but internally use one.
     *
     * @param classLoader the {@link ClassLoader} or <code>null</code> if the
     *                    calling {@link Thread#getContextClassLoader()} should
     *                    be used
     */
    public void setDefaultClassLoader(ClassLoader classLoader) {
      this.classLoader = classLoader;
    }

    /**
     * Obtains the only {@link CachingProvider} defined by the
     * {@link #getDefaultClassLoader()}.
     * <p>
     * Should zero or more than one {@link CachingProvider}s be available, a
     * CacheException is thrown.
     * </p>
     * @return the {@link CachingProvider}
     * @throws CacheException should zero or more than one
     *                        {@link CachingProvider} be available
     *                        or a {@link CachingProvider} could not be loaded
     * @see #getCachingProvider(ClassLoader)
     * @see #getCachingProviders(ClassLoader)
     */
    public CachingProvider getCachingProvider() {
      return getCachingProvider(getDefaultClassLoader());
    }

    /**
     * Obtains the only {@link CachingProvider} defined by the specified
     * {@link ClassLoader}.
     * <p>
     * Should zero or more than one {@link CachingProvider}s be available, a
     * CacheException is thrown.
     * </p>
     * @param classLoader the {@link ClassLoader} to use for loading the
     *                    {@link CachingProvider}
     * @return the {@link CachingProvider}
     * @throws CacheException should zero or more than one
     *                        {@link CachingProvider} be available
     *                        or a {@link CachingProvider} could not be loaded
     * @see #getCachingProviders(ClassLoader)
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
     * Obtain the {@link CachingProvider}s that are available via the
     * {@link #getDefaultClassLoader()}.
     * <p>
     * If a <code>javax.cache.spi.cachingprovider</code> system property is defined,
     * only that {@link CachingProvider} specified by that property is returned.
     * Otherwise all {@link CachingProvider}s that are available via a
     * {@link ServiceLoader} for {@link CachingProvider}s using the default
     * {@link ClassLoader} (and those explicitly requested via
     * {@link #getCachingProvider(String)}) are returned.
     * </p>
     * @return an {@link Iterable} of {@link CachingProvider}s loaded by the
     *         default {@link ClassLoader}
     */
    public Iterable<CachingProvider> getCachingProviders() {
      return getCachingProviders(getDefaultClassLoader());
    }

    /**
     * Obtain the {@link CachingProvider}s that are available via the specified
     * {@link ClassLoader}.
     * <p>
     * If a <code>javax.cache.spi.cachingprovider</code> system property is defined,
     * only that {@link CachingProvider} specified by that property is returned.
     * Otherwise all {@link CachingProvider}s that are available via a
     * {@link ServiceLoader} for {@link CachingProvider}s using the specified
     * {@link ClassLoader} (and those explicitly requested via
     * {@link #getCachingProvider(String, ClassLoader)}) are returned.
     * </p>
     * @param classLoader the {@link ClassLoader} of the returned
     *                    {@link CachingProvider}s
     * @return an {@link Iterable} of {@link CachingProvider}s loaded by the
     *         specified {@link ClassLoader}
     */
    public synchronized Iterable<CachingProvider> getCachingProviders(ClassLoader classLoader) {

      final ClassLoader serviceClassLoader = classLoader == null ? getDefaultClassLoader() : classLoader;
      LinkedHashMap<String, CachingProvider> providers = cachingProviders.get(serviceClassLoader);

      if (providers == null) {

        if (System.getProperties().containsKey(JAVAX_CACHE_CACHING_PROVIDER)) {
          String className = System.getProperty(JAVAX_CACHE_CACHING_PROVIDER);
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
     * Obtain the {@link CachingProvider} that is implemented by the specified
     * fully qualified class name using the {@link #getDefaultClassLoader()}.
     * Should this {@link CachingProvider} already be loaded it is simply
     * returned, otherwise an attempt will be made to load and instantiate the
     * specified class name (using a no-args constructor).
     *
     * @param fullyQualifiedClassName the fully qualified class name of the
     *                                {@link CachingProvider}
     * @return the {@link CachingProvider}
     * @throws CacheException when the {@link CachingProvider} can't be created
     */
    public CachingProvider getCachingProvider(String fullyQualifiedClassName) {
      return getCachingProvider(fullyQualifiedClassName, getDefaultClassLoader());
    }

    /**
     * Load and instantiate the {@link CachingProvider} with the specified
     * fully qualified class name using the provided {@link ClassLoader}
     *
     * @param fullyQualifiedClassName the name of the {@link CachingProvider}
     *                                class
     * @param classLoader             the {@link ClassLoader} to use
     * @return a new {@link CachingProvider} instance
     * @throws CacheException if the specified {@link CachingProvider} could not be
     *                        loaded
     *                        or the specified class is not a {@link
     *                        CachingProvider}
     */
    protected CachingProvider loadCachingProvider(String fullyQualifiedClassName, ClassLoader classLoader) throws CacheException {
      synchronized (classLoader) {
        try {
          Class<?> clazz = classLoader.loadClass(fullyQualifiedClassName);
          if (CachingProvider.class.isAssignableFrom(clazz)) {
            return ((Class<CachingProvider>) clazz).newInstance();
          } else {
            throw new CacheException("The specified class [" + fullyQualifiedClassName + "] is not a CachingProvider");
          }
        } catch (Exception e) {
          throw new CacheException("Failed to load the CachingProvider [" + fullyQualifiedClassName + "]", e);
        }
      }
    }

    /**
     * Obtain the {@link CachingProvider} that is implemented by the specified
     * fully qualified class name using the provided {@link ClassLoader}.
     * Should this {@link CachingProvider} already be loaded it is returned,
     * otherwise an attempt will be made to load and instantiate the specified
     * class (using a no-args constructor).
     *
     * @param fullyQualifiedClassName the fully qualified class name of the
     *                                {@link CachingProvider}
     * @param classLoader             the {@link ClassLoader} to load the
     *                                {@link CachingProvider}
     * @return the {@link CachingProvider}
     * @throws CacheException when the {@link CachingProvider} can't be created
     */
    public synchronized CachingProvider getCachingProvider(String fullyQualifiedClassName, ClassLoader classLoader) {
      ClassLoader serviceClassLoader = classLoader == null ? getDefaultClassLoader() : classLoader;

      LinkedHashMap<String, CachingProvider> providers = cachingProviders.get(serviceClassLoader);

      if (providers == null) {
        // first load the CachingProviders for the {@link ClassLoader}
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
