package javax.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * <p/>
 * A cache, being a mechanism for efficient temporary storage of objects
 * for the purpose of improving the overall performance of an application
 * system, should not be necessary for the application to function correctly,
 * it only improves the performance.
 * <p/>
 * A cache could be scoped, for examples to a JVM, all JVMs on a node, all
 * nodes in a cluster, etc. Operations that are scoped to a cache such as put
 * or load would affect all JVMs in the cache.  So the object loaded in 1 JVM
 * would be equally available to all other JVMs in the cache.
 * <p/>
 * Objects are identified in the cache by a key. A key can be any Java
 * object that implements the equals and  hashcode methods. If the object is
 * to be distributed or persisted (if supported) it must implement
 * serializable.
 * <p/>
 * Each object in the cache will have a <code>CacheEntry<code> object associated with
 * it. This object will encapsulate the metadata associated with the cached
 * object. Mainly it represents the object statistics.
 * <p/>
 * "CacheStatistics" represents the read-only statistics of the cache,
 * while "CacheAttributes" represents the user settable attributes of the
 * cache.
 *
 * @param <K> the type of keys maintained by this Cache
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> extends Map<K, V> {

    //todo should all methods throw CacheException?


    /**
     * The getAll method will return, from the cache, a Map of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception will be thrown.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.loadAll method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and
     * not finding the object in the cache. In both cases a null is returned.
     */
    Map<K,V> getAll(Collection keys) throws CacheException;

    /**
     * The load method provides a means to "pre load" the cache. This method
     * will, asynchronously, load the specified object into the cache using
     * the associated cacheloader. If the object already exists in the cache,
     * no action is taken. If no loader is associated with the object, no object
     * will be loaded into the cache.  If a problem is encountered during the
     * retrieving or loading of the object, an exception should
     * be logged.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.load method.  The cache will not dereference the object. If
     * no "arg" value is provided a null will be passed to the load method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and not
     * finding the object in the cache. In both cases a null is returned.
     */
    void load(Object key) throws CacheException;

    /**
     * The loadAll method provides a means to "pre load" objects into the cache.
     * This method will, asynchronously, load the specified objects into the
     * cache using the associated cache loader. If the an object already exists
     * in the cache, no action is taken. If no loader is associated with the
     * object, no object will be loaded into the cache.  If a problem is
     * encountered during the retrieving or loading of the objects, an
     * exception (to be defined) should be logged.
     * The getAll method will return, from the cache, a Map of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception (to be defined) will be thrown.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.loadAll method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     */
    void loadAll(Collection keys) throws CacheException;

    /**
     * The peek method will return the object associated with "key" if it
     * currently exists (and is valid) in the cache. If not, a null is
     * returned.  With "peek" the CacheLoader will not be invoked and other
     * caches in the system will not be searched.
     */
    V peek(Object key);


    /**
     * Returns the CacheEntry object associated with the object identified by
     * "key". If the object is not in the cache a null is returned.
     */
    CacheEntry<K,V> getCacheEntry(Object key);

    /**
     * Returns the CacheStatistics object associated with the cache.
     * May return null if the cache does not support statistics gathering.
     */
    CacheStatistics getCacheStatistics();


    /**
     * The evict method will remove objects from the cache that are no longer
     * valid.  Objects where the specified expiration time has been reached.
     */
    void evict();

    /**
     * Add a listener to the list of cache listeners
     */
    void addListener(CacheListener listener);

    /**
     * Remove a listener from the list of cache listeners
     */
    void removeListener(CacheListener listener);
}
