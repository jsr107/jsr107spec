/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import java.net.URI;
import java.util.Properties;

/**
 * Provides mechanisms to create, request and later manage the life-cycle of
 * configured CacheManagers, identified by URIs and scoped by ClassLoaders.
 * <p/>
 * The meaning and semantics of the URI used to identify a CacheManager is
 * defined by CachingProvider implementations.  For applications to remain
 * implementation independent, they should avoid attempting to create URIs and
 * instead use the URI returned by {@link #getDefaultURI()}.
 *
 * @author Brian Oliver
 */
public interface CachingProvider {

  /**
   * Requests a CacheManager configured according to the vendor specific URI
   * be made available that uses the provided ClassLoader for loading underlying
   * classes.
   * <p/>
   * Multiple calls to this method with the same URI and ClassLoader
   * <strong>must</strong> return the same CacheManager instance, accept if a
   * previously returned CacheManager has been closed.
   *
   * @param uri         a vendor specific URI for the CacheManager (null means
   *                    use {@link #getDefaultURI()})
   * @param classLoader the ClassLoader to use for the CacheManager (null means
   *                    use {@link #getDefaultClassLoader()})
   * @param properties  the Properties for the CachingProvider to
   *                    create the CacheManager (null means no vendor specific
   *                    Properties are required)
   * @throws javax.cache.CacheException when a CacheManager for the specified
   *                                    arguments could not be produced
   */
  CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties);

  /**
   * Obtains the default ClassLoader that will be used by the CachingProvider.
   *
   * @return the default ClassLoader for the CachingProvider and javax.caching
   *         API calls
   */
  ClassLoader getDefaultClassLoader();

  /**
   * Obtains the default URI for the CachingProvider.
   * <p/>
   * Use this method to obtain a suitable URI for the CachingProvider.
   *
   * @return the default URI for the CachingProvider
   */
  URI getDefaultURI();

  /**
   * Obtains the default Properties for the CachingProvider.
   * <p/>
   * Use this method to obtain a suitable Properties for the CachingProvider.
   *
   * @return the default Properties for the CachingProvider
   */
  Properties getDefaultProperties();

  /**
   * Requests a CacheManager configured according to the vendor specific URI
   * be made available that uses the provided ClassLoader for loading underlying
   * classes.
   * <p/>
   * Multiple calls to this method with the same URI and ClassLoader
   * <strong>must</strong> return the same CacheManager instance, accept if a
   * previously returned CacheManager has been closed.
   *
   * @param uri         a vendor specific URI for the CacheManager (null means
   *                    use {@link #getDefaultURI()})
   * @param classLoader the ClassLoader to use for the CacheManager (null means
   *                    use {@link #getDefaultClassLoader()})
   * @throws javax.cache.CacheException when a CacheManager for the specified
   *                                    arguments could not be produced
   */
  CacheManager getCacheManager(URI uri, ClassLoader classLoader);

  /**
   * Requests a CacheManager configured according to the {@link #getDefaultURI()}
   * and {@link #getDefaultProperties()} be made available that using the
   * {@link #getDefaultClassLoader()} for loading underlying classes.
   * <p/>
   * Multiple calls to this method <strong>must</strong> return the same
   * CacheManager instance, accept if a previously returned CacheManager
   * has been closed.
   */
  CacheManager getCacheManager();

  /**
   * Closes all of the CacheManager instances and associated resources created
   * and maintained by the CachingProvider across all ClassLoaders.
   * <p/>
   * After closing the CachingProvider will still be operational.  It may still
   * be used for acquiring CacheManager instances, though those will now be
   * new.
   */
  void close();

  /**
   * Closes all CacheManager instances and associated resources created by
   * the CachingProvider using the specified ClassLoader.
   * <p/>
   * After closing the CachingProvider will still be operational.  It may still
   * be used for acquiring CacheManager instances, though those will now be
   * new for the specified ClassLoader.
   *
   * @param classLoader the ClassLoader to release
   */
  void close(ClassLoader classLoader);

  /**
   * Closes all CacheManager instances and associated resources created by
   * the CachingProvider for the specified URI and ClassLoader.
   *
   * @param uri         the URI to release
   * @param classLoader the ClassLoader to release
   */
  void close(URI uri, ClassLoader classLoader);

  /**
   * Determines whether an optional feature is supported by the CachingProvider.
   *
   * @param optionalFeature the feature to check for
   * @return true if the feature is supported
   */
  boolean isSupported(OptionalFeature optionalFeature);
}
