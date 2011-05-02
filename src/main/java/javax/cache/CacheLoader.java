package javax.cache;

import java.util.Collection;
import java.util.Map;

/**
 * User should implement this CacheLoader interface to
 * provide a loader object to load the objects into cache.
 *
 * 24/1/09 Changed so as to not throw CacheException, to ease implementation
 */
public interface CacheLoader<K,V>
{
    /**
     * loads an object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <P>
     *
     * @param key the key identifying the object being loaded
     *
     * @return The object that is to be stored in the cache.
     */
    public V load(Object key);

    /**
     * loads multiple object. Application writers should implement this
     * method to customize the loading of cache object. This method is called
     * by the caching service when the requested object is not in the cache.
     * <P>
     *
     * @param keys a Collection of keys identifying the objects to be loaded
     *
     * @return A Map of objects that are to be stored in the cache.
     */

    public Map<K,V> loadAll(Collection keys);

}
