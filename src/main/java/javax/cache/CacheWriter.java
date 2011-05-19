package javax.cache;


import java.util.Collection;

/**
 * A CacheWriter is used for write-through and write-behind caching to a underlying resource.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface CacheWriter<K, V> {


    /**
     * Write the specified value under the specified key to the underlying store.
     * This method is intended to support both key/value creation and value update for a specific key.
     *
     * @param entry the entry to be written
     * @throws CacheException
     */
    void write(Cache.Entry<K, V> entry) throws CacheException;

    /**
     * Write the specified entries to the underlying store. This method is intended to support both insert and update.
     * If this operation fails (by throwing an exception) after a partial success,
     * the convention is that entries which have been written successfully are to be removed from the specified entries,
     * indicating that the write operation for the entries left in the map has failed or has not been attempted.
     *
     * @param entries the entries to be written
     * @throws CacheException
     */
    void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheException;


    /**
     * Delete the cache entry from the store
     *
     * @param entry the cache entry that is used for the delete operation
     * @throws CacheException
     */
    void delete(Cache.Entry<K, V> entry) throws CacheException;


    /**
     * Remove data and keys from the underlying store for the given collection of keys, if present. If this operation fails
     * (by throwing an exception) after a partial success, the convention is that keys which have been erased successfully
     * are to be removed from the specified keys, indicating that the erase operation for the keys left in the collection
     * has failed or has not been attempted.
     *
     * @param entries the entries that have been removed from the cache
     * @throws CacheException
     */
    void deleteAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws CacheException;

}