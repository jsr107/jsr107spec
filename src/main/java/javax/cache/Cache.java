package javax.cache;

import javax.cache.listeners.CacheEntryListener;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * A Cache provides temporary storage for later fase retrieval.
 *
 * The interface is map-like and will be familiar. However the map-like
 * methods have been modified to enable efficient implementation of
 * distributed caches.
 * <p/>
 * The API provides the atomic operations from {@link java.util.concurrent.ConcurrentMap}.
 * <p/>
 * The API provides batch operations suited to network storage.
 * <p/>
 * OPEN ISSUES:
 * - should all methods throw CacheException? If not what?
 * - Cache Statistics? JMX?
 * - cache loading defined. warming which is in the cache lifecycle is not.
 * <p/>
 * These methods are ?blocking synchronous?. We need to define what that means.
 * <p/>
 * Cache implements Iterable, providing support for simplified iteration. However
 * iteration should be used with caution. It is an O(n) operation and may be
 * slow on large or distributed caches.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Greg Luck
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K, V>> {


    /**
     * A reserved word for cache names. It denotes a default configuration
     * which is applied to caches created without configuration.
     */
    static final String DEFAULT_CACHE_NAME = "__default__";

    /**
     * Gets an entry from the cache.
     * <p/>
     * A return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey(Object)}
     * operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the element, or null, if it does not exist.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws IllegalArgumentException if the key is null
     * @throws CacheException
     */
    V get(Object key) throws CacheException;


    /**
     * The getAll method will return, from the cache, a Map of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception will be thrown.
     * <p/>
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.loadAll method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     * <p/>
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and
     * not finding the object in the cache. In both cases a null is returned.
     *
     * @param keys The keys whose associated values are to be returned.
     * @return The entries for the specified keys.
     */
    Map<K, V> getAll(Collection<? extends K> keys);


    /**
     * Returns <tt>true</tt> if this cache contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this cache contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     * <p/>
     *
     * @param key key whose presence in this cache is to be tested.
     *  null is permitted but the cache will always return null
     * @return <tt>true</tt> if this map contains a mapping for the specified key
     */
    boolean containsKey(Object key);


    /**
     * The load method provides a means to "pre load" the cache. This method
     * will, asynchronously, load the specified object into the cache using
     * the associated cacheloader. If the object already exists in the cache,
     * no action is taken. If no loader is associated with the object, no object
     * will be loaded into the cache.  If a problem is encountered during the
     * retrieving or loading of the object, an exception should
     * be logged.
     * <p/>
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.load method.  The cache will not dereference the object. If
     * no "arg" value is provided a null will be passed to the load method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and not
     * finding the object in the cache. In both cases a null is returned.
     *
     * @param key
     * @param specificLoader a specific loader to use. If null the default loader is used.
     * @param loaderArgument provision for additional parameters to be passed to the loader
     * @return a Future which can be used to monitor execution
     */
    Future load(K key, CacheLoader specificLoader, Object loaderArgument);

    /**
     * The loadAll method provides a means to "pre load" objects into the cache.
     * This method will, asynchronously, load the specified objects into the
     * cache using the associated cache loader. If the an object already exists
     * in the cache, no action is taken. If no loader is associated with the
     * object, no object will be loaded into the cache.  If a problem is
     * encountered during the retrieving or loading of the objects, an
     * exception (to be defined) should be logged.
     * <p/>
     * The getAll method will return, from the cache, a Map of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception (to be defined) will be thrown.
     * <p/>
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.loadAll method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     *
     * @param keys
     * @param specificLoader a specific loader to use. If null the default loader is used.
     * @param loaderArgument provision for additional parameters to be passed to the loader
     * @return a Future which can be used to monitor execution
     */
    Future loadAll(Collection<? extends K> keys, CacheLoader specificLoader, Object loaderArgument);

    /**
     * Returns the CacheEntry object associated with the object identified by
     * "key". If the object is not in the cache a null is returned.
     */
    Entry<K, V> getCacheEntry(Object key);

    /**
     * Returns the CacheStatistics object associated with the cache.
     * May return null if the cache does not support statistics gathering.
     */
    CacheStatisticsMBean getCacheStatistics();

    /**
     * Add a listener to the list of cache listeners
     * TODO: maybe we want multiple addListeners - one per CacheEntryListener type
     */
    void addListener(CacheEntryListener listener);

    /**
     * Remove a listener from the list of cache listeners
     * TODO: maybe we want multiple removeListeners - one per CacheEntryListener type
     */
    void removeListener(CacheEntryListener listener);


    // Modification Operations

    /**
     * NOTE: different return value
     *
     * @see java.util.Map#put(Object, Object)
     */
    void put(K key, V value);


    /**
     * @see java.util.Map#putAll(java.util.Map)
     */
    void putAll(java.util.Map<? extends K, ? extends V> m);


    /**
     * NOTE: different return value
     *
     * @see java.util.concurrent.ConcurrentMap#putIfAbsent(Object, Object)
     */
    boolean putIfAbsent(K key, V value);

    /**
     * NOTE: different return value
     *
     * @return returns false if there was no matching key
     * @see java.util.Map#remove(Object)
     */
    boolean remove(Object key);

    /**
     * Removes the entry for a key only if currently mapped to a given value.
     *
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(value)) {
     *       cache.remove(key);
     *       return true;
     *   } else return false;</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is associated
     * @return <tt>true</tt> if the value was removed
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this cache
     * @throws ClassCastException if the key or value is of an inappropriate
     *         type for this cache (optional)
     * @throws NullPointerException if the specified key or value is null,
     *         and this cache does not permit null keys or values (optional)
     */
    V getAndRemove(Object key);

    /**
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object, Object)
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
    boolean replace(K key, V value);

    /**
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
    V getAndReplace(K key, V value);


    /**
     * Removes all of the mappings from this cache.
     * The cache will be empty after this call returns.
     * <p/>
     * This is potentially an expensive operation.
     * <p/>
     */
    void removeAll();

    /**
     * @return the CacheConfiguration, which is immutable
     */
    CacheConfiguration getConfiguration();

    /**
     * A cache entry (key-value pair).
     * <p/>
     * The <i>only</i> way to obtain a reference to a cache entry is from the iterator of Cache.
     * <p/>
     */
    interface Entry<K, V> {

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         */
        K getKey();

        /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * <tt>remove</tt> operation), the results of this call are undefined.
         *
         * @return the value corresponding to this entry
         */
        V getValue();


        /**
         * Compares the specified object with this entry for equality.
         * Returns <tt>true</tt> if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries <tt>e1</tt> and <tt>e2</tt> represent the same mapping
         * if<pre>
         *     (e1.getKey()==null ?
         *      e2.getKey()==null : e1.getKey().equals(e2.getKey()))  &amp;&amp;
         *     (e1.getValue()==null ?
         *      e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         * </pre>
         * This ensures that the <tt>equals</tt> method works properly across
         * different implementations of the <tt>Map.Entry</tt> interface.
         *
         * @param o object to be compared for equality with this cache entry
         * @return <tt>true</tt> if the specified object is equal to this cache
         *         entry
         */
        boolean equals(Object o);

        /**
         * Returns the hash code value for this cache entry.  The hash code
         * of a cache entry <tt>e</tt> is defined to be: <pre>
         *     (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *     (e.getValue()==null ? 0 : e.getValue().hashCode())
         * </pre>
         * This ensures that <tt>e1.equals(e2)</tt> implies that
         * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries
         * <tt>e1</tt> and <tt>e2</tt>, as required by the general
         * contract of <tt>Object.hashCode</tt>.
         *
         * @return the hash code value for this cache entry
         * @see Object#hashCode()
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        int hashCode();
    }

}
