/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.configuration.Configuration;
import javax.cache.management.CacheMXBean;
import javax.cache.spi.CachingProvider;
import java.io.Closeable;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.util.Properties;

/**
 * A {@link CacheManager} provides a means of establishing, configuring,
 * acquiring, closing and destroying uniquely named {@link Cache}s.
 * <p>
 * {@link Cache}s produced and owned by a {@link CacheManager} typically share
 * common infrastructure, for example, a common {@link ClassLoader} and
 * implementation specific {@link Properties}.
 * <p>
 * Implementations of {@link CacheManager} may additionally provide and share
 * external resources between the {@link Cache}s being managed, for example,
 * the content of the managed {@link Cache}s may be stored in the same cluster.
 * <p>
 * By default {@link CacheManager} instances are typically acquired through the
 * use of a {@link CachingProvider}.  Implementations however may additionally
 * provide other mechanisms to create, acquire, manage and configure
 * {@link CacheManager}s, including:
 * <ul>
 * <li>making use of {@link java.util.ServiceLoader}s,</li>
 * <li>permitting the use of the <code>new</code> operator to create a
 * concrete implementation, </li>
 * <li>providing the construction through the use of one or more
 * builders, and</li>
 * <li>through the use of dependency injection.</li>
 * </ul>
 * <p>
 * The default {@link CacheManager} however can always be acquired using the
 * default configured {@link CachingProvider} obtained by the {@link Caching}
 * class.  For example:
 * <pre><code>
 * CachingProvider provider = Caching.getCachingProvider();
 * CacheManager manager = provider.getCacheManager();
 * </code></pre>
 * <p>
 * Within a Java process {@link CacheManager}s and the {@link Cache}s they
 * manage are scoped and uniquely identified by a {@link URI},  the meaning of
 * which is implementation specific.   To obtain the default {@link URI},
 * {@link ClassLoader} and {@link Properties} for an implementation, consult the
 * {@link CachingProvider} class.
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @see Caching
 * @see CachingProvider
 * @see Cache
 * @since 1.0
 */
public interface CacheManager extends Closeable {

  /**
   * Get the {@link CachingProvider} that created and is responsible for
   * the {@link CacheManager}.
   *
   * @return the CachingProvider or <code>null</code> if the {@link CacheManager}
   *         was created without using a {@link CachingProvider}
   */
  CachingProvider getCachingProvider();

  /**
   * Get the URI of the {@link CacheManager}.
   *
   * @return the URI of the {@link CacheManager}
   */
  URI getURI();

  /**
   * Get the {@link ClassLoader} used by the {@link CacheManager}.
   *
   * @return  the {@link ClassLoader} used by the {@link CacheManager}
   */
  ClassLoader getClassLoader();

  /**
   * Get the {@link Properties} that were used to create this
   * {@link CacheManager}.
   * <p>
   * Implementations are not required to re-configure the
   * {@link CacheManager} should modifications to the returned
   * {@link Properties} be made.
   *
   * @return the Properties used to create the {@link CacheManager}
   */
  Properties getProperties();

  /**
   * Creates a named {@link Cache} at runtime.
   * <p>
   * If a {@link Cache} with the specified name is known to the {@link
   * CacheManager}, a CacheException is thrown.
   * <p>
   * If a {@link Cache} with the specified name is unknown the {@link
   * CacheManager}, one is created according to the provided {@link Configuration}
   * after which it becomes managed by the {@link CacheManager}.
   * <p>
   * Prior to a {@link Cache} being created, the provided {@link Configuration}s is
   * validated within the context of the {@link CacheManager} properties and
   * implementation.
   * <p>
   * Implementers should be aware that the {@link Configuration} may be used to
   * configure other {@link Cache}s.
   * <p>
   * There's no requirement on the part of a developer to call this method for
   * each {@link Cache} an application may use.  Implementations may support
   * the use of declarative mechanisms to pre-configure {@link Cache}s, thus
   * removing the requirement to configure them in an application.  In such
   * circumstances a developer may simply call either the
   * {@link #getCache(String)} or {@link #getCache(String, Class, Class)}
   * methods to acquire a previously established or pre-configured {@link Cache}.
   *
   * @param cacheName     the name of the {@link Cache}
   * @param configuration a {@link Configuration} for the {@link Cache}
   * @throws IllegalStateException         if the {@link CacheManager}
   *                                       {@link #isClosed()}
   * @throws CacheException                if there was an error configuring the
   *                                       {@link Cache}, which includes trying
   *                                       to create a cache which already exists.
   * @throws IllegalArgumentException      if the configuration is invalid
   * @throws UnsupportedOperationException if the configuration specifies
   *                                       an unsupported feature
   * @throws NullPointerException          if the cache configuration or name
   *                                       is null
   * @throws SecurityException             when the operation could not be performed
   *                                       due to the current security settings
   */
  <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName,
                                                                C configuration)
      throws IllegalArgumentException;


  /**
   * Looks up a managed {@link Cache} given its name.
   * <p>
   * This method must be used for {@link Cache}s that were configured with
   * runtime key and value types. Use {@link #getCache(String)} for
   * {@link Cache}s where these were not specified.
   * <p>
   * Implementations must ensure that the key and value types are the same as
   * those configured for the {@link Cache} prior to returning from this method.
   * <p>
   * Implementations may further perform type checking on mutative cache operations and
   * throw a {@link ClassCastException} if said checks fail.
   * <p>
   * Implementations that support declarative mechanisms for pre-configuring
   * {@link Cache}s may return a pre-configured {@link Cache} instead of
   * <code>null</code>.
   *
   * @param cacheName the name of the managed {@link Cache} to acquire
   * @param keyType   the expected {@link Class} of the key
   * @param valueType the expected {@link Class} of the value
   * @return the Cache or null if it does exist or can't be pre-configured
   * @throws IllegalStateException    if the {@link CacheManager}
   *                                  is {@link #isClosed()}
   * @throws IllegalArgumentException if the specified key and/or value types are
   *                                  incompatible with the configured cache.
   * @throws SecurityException        when the operation could not be performed
   *                                  due to the current security settings
   */
  <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyType, Class<V> valueType);

  /**
   * Looks up a managed {@link Cache} given its name.
   * <p>
   * This method may only be used to acquire {@link Cache}s that were
   * configured without runtime key and value types, or were configured
   * to use Object.class key and value types.
   * <p>
   * Use the {@link #getCache(String, Class, Class)} method to acquire
   * {@link Cache}s that were configured with specific runtime types.
   * <p>
   * Implementations must check if key and value types were configured
   * for the requested {@link Cache}. If either the keyType or valueType of the
   * configured {@link Cache} were specified (other than <code>Object.class</code>)
   * an {@link IllegalArgumentException} will be thrown.
   * <p>
   * Implementations that support declarative mechanisms for pre-configuring
   * {@link Cache}s may return a pre-configured {@link Cache} instead of
   * <code>null</code>.
   *
   * @param cacheName the name of the cache to look for
   * @return the Cache or null if it does exist or can't be pre-configured
   * @throws IllegalStateException    if the CacheManager is {@link #isClosed()}
   * @throws IllegalArgumentException if the {@link Cache} was configured with
   *                                  specific types, this method cannot be used
   * @throws SecurityException        when the operation could not be performed
   *                                  due to the current security settings
   * @see #getCache(String, Class, Class)
   */
  <K, V> Cache<K, V> getCache(String cacheName);

  /**
   * Obtains an {@link Iterable} over the names of {@link Cache}s managed by the
   * {@link CacheManager}.
   * <p>
   * {@link java.util.Iterator}s returned by the {@link Iterable} are immutable.
   * Any modification of the {@link java.util.Iterator}, including remove, will
   * raise an {@link IllegalStateException}.  If the {@link Cache}s managed by
   * the {@link CacheManager} change, the {@link Iterable} and
   * associated {@link java.util.Iterator}s are not affected.
   * <p>
   * {@link java.util.Iterator}s returned by the {@link Iterable} may not provide
   * all of the {@link Cache}s managed by the {@link CacheManager}.  For example:
   * Internally defined or platform specific {@link Cache}s that may be accessible
   * by a call to {@link #getCache(String)} or {@link #getCache(String, Class, Class)}
   * may not be present in an iteration.
   *
   * @return an {@link Iterable} over the names of managed {@link Cache}s.
   * @throws IllegalStateException if the {@link CacheManager}
   *                               is {@link #isClosed()}
   * @throws SecurityException     when the operation could not be performed
   *                               due to the current security settings
   */
  Iterable<String> getCacheNames();

  /**
   * Destroys a specifically named and managed {@link Cache}.  Once destroyed
   * a new {@link Cache} of the same name but with a different {@link
   * Configuration} may be configured.
   * <p>
   * This is equivalent to the following sequence of method calls:
   * <ol>
   * <li>{@link Cache#clear()}</li>
   * <li>{@link Cache#close()}</li>
   * </ol>
   * followed by allowing the name of the {@link Cache} to be used for other
   * {@link Cache} configurations.
   * <p>
   * From the time this method is called, the specified {@link Cache} is not
   * available for operational use. An attempt to call an operational method on
   * the {@link Cache} will throw an {@link IllegalStateException}.
   *
   * @param cacheName the cache to destroy
   * @throws IllegalStateException if the {@link CacheManager}
   *                               {@link #isClosed()}
   * @throws NullPointerException  if cacheName is null
   * @throws SecurityException     when the operation could not be performed
   *                               due to the current security settings
   */
  void destroyCache(String cacheName);

  /**
   * Controls whether management is enabled. If enabled the {@link CacheMXBean}
   * for
   * each cache is registered in the platform MBean server. The platform
   * MBeanServer is obtained using
   * {@link ManagementFactory#getPlatformMBeanServer()}.
   * <p>
   * Management information includes the name and configuration information for
   * the cache.
   * <p>
   * Each cache's management object must be registered with an ObjectName that
   * is unique and has the following type and attributes:
   * <p>
   * Type:
   * <code>javax.cache:type=Cache</code>
   * <p>
   * Required Attributes:
   * <ul>
   * <li>CacheManager the name of the CacheManager
   * <li>Cache the name of the Cache
   * </ul>
   *
   * @param cacheName the name of the cache to register
   * @param enabled   true to enable management, false to disable.
   * @throws IllegalStateException if the {@link CacheManager} or
   *                               {@link Cache} {@link #isClosed()}
   * @throws SecurityException     when the operation could not be performed
   *                               due to the current security settings
   */
  void enableManagement(String cacheName, boolean enabled);

  /**
   * Enables or disables statistics gathering for a managed {@link Cache} at
   * runtime.
   * <p>
   * Each cache's statistics object must be registered with an ObjectName that
   * is unique and has the following type and attributes:
   * <p>
   * Type:
   * <code>javax.cache:type=CacheStatistics</code>
   * <p>
   * Required Attributes:
   * <ul>
   * <li>CacheManager the name of the CacheManager
   * <li>Cache the name of the Cache
   * </ul>
   *
   * @param cacheName the name of the cache to register
   * @param enabled   true to enable statistics, false to disable.
   * @throws IllegalStateException if the {@link CacheManager} or
   *                               {@link Cache} {@link #isClosed()}
   * @throws NullPointerException  if cacheName is null
   * @throws SecurityException when the operation could not be performed
   *                           due to the current security settings
   */
  void enableStatistics(String cacheName, boolean enabled);

  /**
   * Closes the {@link CacheManager}.
   * <p>
   * For each {@link Cache} managed by the {@link CacheManager}, the
   * {@link Cache#close()} method will be invoked, in no guaranteed order.
   * <p>
   * If a {@link Cache#close()} call throws an exception, the exception will be
   * ignored.
   * <p>
   * After executing this method, the {@link #isClosed()} method will return
   * <code>true</code>.
   * <p>
   * All attempts to close a previously closed {@link CacheManager} will be
   * ignored.
   *
   * @throws SecurityException when the operation could not be performed
   *                           due to the current security settings
   */
  void close();

  /**
   * Determines whether the {@link CacheManager} instance has been closed. A
   * {@link CacheManager} is considered closed if;
   * <ol>
   * <li>the {@link #close()} method has been called</li>
   * <li>the associated {@link #getCachingProvider()} has been closed, or</li>
   * <li>the {@link CacheManager} has been closed using the associated
   * {@link #getCachingProvider()}</li>
   * </ol>
   * <p>
   * This method generally cannot be called to determine whether the
   * {@link CacheManager} is valid or invalid. A typical client can determine
   * that a {@link CacheManager} is invalid by catching any exceptions that
   * might be thrown when an operation is attempted.
   *
   * @return true if this {@link CacheManager} instance is closed; false if it
   *         is still open
   */
  boolean isClosed();

  /**
   * Provides a standard mechanism to access the underlying concrete caching
   * implementation to provide access to further, proprietary features.
   * <p>
   * If the provider's implementation does not support the specified class,
   * the {@link IllegalArgumentException} is thrown.
   *
   * @param clazz the proprietary class or interface of the underlying concrete
   *              {@link CacheManager}. It is this type which is returned.
   * @return an instance of the underlying concrete {@link CacheManager}
   * @throws IllegalArgumentException if the caching provider doesn't support the
   *                                  specified class.
   * @throws SecurityException        when the operation could not be performed
   *                                  due to the current security settings
   */
  <T> T unwrap(java.lang.Class<T> clazz);
}
