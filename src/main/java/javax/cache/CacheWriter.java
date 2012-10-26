/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;


import java.util.Collection;

/**
 * A CacheWriter is used for write-through to an underlying resource.
 * <p/>
 * The semantics of write-through when an <code>Exception</code> is thrown are implementation specific.
 * <p/>
 * The transactional semantics of write-through on a transactional cache are implementation specific.
 * <p/>
 * The entry passed into {@link #write(javax.cache.Cache.Entry)} is independent of the cache mapping for that key
 * meaning that if the value changes in the cache or is removed it does not change the said entry.
 *
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @since 1.0
 */
public interface CacheWriter<K, V> {


    /**
     * Write the specified value under the specified key to the underlying store.
     * This method is intended to support both key/value creation and value update for a specific key.
     *
     * @param entry the entry to be written
     * @throws CacheException if ????? TODO describe when
     */
    void write(Cache.Entry<K, V> entry);

    /**
     * Write the specified entries to the underlying store. This method is intended to support both insert and update.
     * If this operation fails (by throwing an exception) after a partial success,
     * the convention is that entries which have been written successfully are to be removed from the specified entries,
     * indicating that the write operation for the entries left in the map has failed or has not been attempted.
     *
     * @param entries the entries to be written
     * @throws CacheException if ????? TODO describe when
     */
    void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries);


    /**
     * Delete the cache entry from the store
     *
     * @param key the key that is used for the delete operation
     * @throws CacheException if ????? TODO describe when
     */
    void delete(Object key);


    /**
     * Remove data and keys from the underlying store for the given collection of keys, if present. If this operation fails
     * (by throwing an exception) after a partial success, the convention is that keys which have been erased successfully
     * are to be removed from the specified keys, indicating that the erase operation for the keys left in the collection
     * has failed or has not been attempted.
     * <p/>
     * Expiry of a cache entry is not a delete hence will not cause this method to be invoked.
     *
     * @param entries the entries that have been removed from the cache
     * @throws CacheException if ????? TODO describe when
     */
    void deleteAll(Collection<?> entries);

}
