/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache;

/**
 * Manages CacheManager instances.
 *
 * It is invoked by the {@link javax.cache.Caching} class to create
 * a {@link CacheManager}
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheManagerFactory {
    /**
     * Get a named cache manager using the default cache loader as specified by
     * the implementation.
     *
     * @param name the name of the cache manager
     * @return the named cache manager
     * @throws NullPointerException  if name is null
     */
    CacheManager getCacheManager(String name);

    /**
     * Get the cache manager for the specified name and class loader.
     * <p/>
     * If there is no cache manager associated, it is created.
     *
     * @param classLoader associated with the cache manager.
     * @param name        the name of the cache manager
     * @return the new cache manager
     * @throws NullPointerException  if classLoader or name is null
     */
    CacheManager getCacheManager(ClassLoader classLoader, String name);

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
    void close() throws CachingShutdownException;

    /**
     * Reclaims all resources for a ClassLoader from this factory.
     * <p/>
     * All cache managers linked to the specified CacheLoader obtained from the factory are shutdown.
     *
     * @param classLoader the class loader for which managers will be shut down
     * @return true if found, false otherwise
     * @throws CachingShutdownException if any of the individual shutdowns failed
     */
    boolean close(ClassLoader classLoader) throws CachingShutdownException;

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
    boolean close(ClassLoader classLoader, String name) throws CachingShutdownException;
}
