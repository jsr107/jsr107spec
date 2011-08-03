/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.Cache;
import javax.cache.CacheConfiguration;
import javax.cache.CacheManager;

/**
 * Interface that should be implemented by a CacheManager factory provider.
 *
 * It is invoked by the {@link javax.cache.CacheManagerFactory} class to create
 * a {@link CacheManager}
 *
 * @see javax.cache.CacheManagerFactory
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public interface CacheManagerFactoryProvider {

    /**
     * Called by the {@link javax.cache.CacheManagerFactory} class when a
     * new CacheManager needs to be created.
     * <p/>
     * An implementation of this interface must have a public no-arg constructor.
     * <p/>
     * The name may be used to associate a configuration with this CacheManager instance.
     *
     * @param name the name of this cache manager
     * @return a new cache manager.
     * @throws NullPointerException if name is null
     */
    CacheManager createCacheManager(String name);

    /**
     * Creates an unitialised cache instance with the given name and default configuration.
     * The returned cache much be added to a {@link CacheManager} to be started and used.
     * <p/>
     * todo Greg this only exists to allow TCK testing of the new creational pattern.
     * @param name the cache name
     * @return a new cache
     */
    <K, V> Cache<K, V> createCache(String name);

    /**
     * Create a mutable {@link javax.cache.CacheConfiguration} instance.
     * The configuration returned should have the default values.
     *
     * @return a cache configuration
     */
    CacheConfiguration createCacheConfiguration();
}
