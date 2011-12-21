/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.CacheManager;
import javax.cache.CachingShutdownException;
import javax.cache.OptionalFeature;

/**
 * Interface that should be implemented by a CacheManager factory provider.
 *
 * It is invoked by the {@link javax.cache.Caching} class to create
 * a {@link CacheManager}
 * <p/>
 * An implementation of this interface must have a public no-arg constructor.
 * <p/>
 * @see javax.cache.Caching
 *
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CachingProvider {

    /**
     * Get the cache manager for the specified name and class loader.
     * <p/>
     * If there is no cache manager associated, it is created.
     *
     * @param classLoader associated with the cache manager.
     * @param name        the name of the cache manager
     * @return the new cache manager
     * @throws NullPointerException  if classLoader or name is null
     * @throws IllegalStateException if no CachingProvider was found
     */
    public CacheManager getCacheManager(ClassLoader classLoader, String name);

    /**
     * Reclaims all resources obtained from this factory.
     * <p/>
     * All cache managers obtained from the factory are shutdown.
     * <p/>
     * Subsequent requests from this factory will return different cache managers than would have been obtained before
     * shutdown.
     *
     * @throws javax.cache.CachingShutdownException if any of the individual shutdowns failed
     */
    public void close() throws CachingShutdownException;

    /**
     * Reclaims all resources for a ClassLoader from this factory.
     * <p/>
     * All cache managers linked to the specified CacheLoader obtained from the factory are shutdown.
     *
     * @param classLoader the class loader for which managers will be shut down
     * @return true if found, false otherwise
     * @throws CachingShutdownException if any of the individual shutdowns failed
     */
    public boolean close(ClassLoader classLoader) throws CachingShutdownException;

    /**
     * Reclaims all resources for a ClassLoader from this factory.
     * <p/>
     * the named cache manager obtained from the factory is shutdown.
     *
     * @param classLoader the class loader for which managers will be shut down
     * @param name        the name of the cache manager
     * @return true if found, false otherwise
     * @throws CachingShutdownException if there is a problem shutting down a CacheManager
     */
    public boolean close(ClassLoader classLoader, String name) throws CachingShutdownException;

    /**
     * Called by the {@link javax.cache.Caching} class when a
     * new CacheManager needs to be created.
     * <p/>
     * The name may be used to associate a configuration with this CacheManager instance.
     *
     * @param classLoader the ClassLoader that should be used in converting values into Java Objects.
     * @param name the name of this cache manager
     * @return a new cache manager.
     * @throws NullPointerException if classLoader or name is null
     * @see javax.cache.Caching#getCacheManager(ClassLoader, String)
     */
    CacheManager createCacheManager(ClassLoader classLoader, String name);

    /**
     * Called by the {@link javax.cache.Caching} class when a
     * new CacheManager needs to be created and the ClassLoader is not specified.
     * <p/>
     * Possible strategies include the following:
     *<pre>
     *     Thread.currentThread().getContextClassLoader();
     *     getClass().getClassLoader();
     *     todo decide which one
     *</pre>
     *
     *
     * @return the default ClassLoader. This will never be null.
     * @see javax.cache.Caching#getCacheManager()
     */
    ClassLoader getDefaultClassLoader();

    /**
     * Indicates whether a optional feature is supported by this implementation
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    boolean isSupported(OptionalFeature optionalFeature);
}
