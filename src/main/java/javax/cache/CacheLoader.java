/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import java.util.Collection;
import java.util.Map;

/**
 * Used for read-through caching and loading data into a cache.
 * <p/>
 * See CacheWriter which is used for write-through caching.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheLoader<K, V> {

    /**
     * loads an object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <p/>
     *
     * @param key the key identifying the object being loaded
     * @param arg the argument passed
     * @return The object that is to be stored in the cache.
     */
    V load(K key, Object arg);

    /**
     * loads multiple object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <p/>
     *
     * @param keys keys identifying the values to be loaded
     * @param arg the argument passed
     * @return A Map of objects that are to be stored in the cache.
     */
    Map<K, V> loadAll(Collection<? extends K> keys, Object arg);

}
