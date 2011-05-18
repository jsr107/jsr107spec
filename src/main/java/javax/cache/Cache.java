package javax.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * This is an alternative proposal to {@link Cache} which does NOT extend Map.
 * It is based on {@link java.util.concurrent.ConcurrentMap} but adjusts some
 * method signatures to better suit a distributed system by, for example,
 * not returning values on put or remove.
 * <p/>
 * <p/>
 * OPEN ISSUES:
 * - should all methods throw CacheException?
 * - resolve overlap/conflict between inner interface Entry and CacheEntry
 * - Cache Statistics? JMX?
 * - do we need the Iterable methods?
 * - cache loading defined. warming which is in the cache lifecycle is not.
 * <p/>
 * These methods are ?blocking synchronous?. We need to define what that means.
 * <p/>
 * Cache extends Iterable, providing support for simplified iteration. Iteration
 * is an O(n) operation. Large caches may however take a long time to iterate.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K, V>> {

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
     * @throws IllegalStateException if the cache is not {@link Status#STATUS_ALIVE}
     */
    V get(Object key);


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
     * @param key key whose presence in this cache is to be tested. null is permitted but the cache will always return null
     * @return <tt>true</tt> if this map contains a mapping for the specified key
     */
    boolean containsKey(K key);


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
    Cache.Entry<K, V> getCacheEntry(Object key);

    /**
     * Returns the CacheStatistics object associated with the cache.
     * May return null if the cache does not support statistics gathering.
     */
    CacheStatistics getCacheStatistics();

    /**
     * Add a listener to the list of cache listeners
     */
    void addListener(CacheListener listener);

    /**
     * Remove a listener from the list of cache listeners
     */
    void removeListener(CacheListener listener);


    // Modification Operations

    /**
     * NOTE: different return value
     *
     * @see java.util.Map#put(Object, Object)
     */
    void put(K key, V value);


    /**
     * NOTE: different return value
     *
     * @see java.util.concurrent.ConcurrentMap#putIfAbsent(Object, Object)
     */
//    V putIfAbsent(K key, V value);
    boolean putIfAbsent(K key, V value);

    /**
     * NOTE: different return value
     *
     * @return returns false if there was no matching key
     * @see java.util.Map#remove(Object)
     */
    boolean remove(Object key);

    /**
     * @see java.util.Map#remove(Object)
     */
    V getAndRemove(Object key);

    /**
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object, Object)
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * NOTE: different return value
     *
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
//    V replace(K key, V value);
    boolean replace(K key, V value);

    /**
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
//    V replace(K key, V value);
    V replaceAndReturnPreviousValue(K key, V value);

    /**
     * @see java.util.concurrent.ConcurrentMap#remove(Object, Object)
     */
    boolean remove(Object key, Object value);

    // Bulk Operations

    /**
     * @see java.util.Map#putAll(java.util.Map)
     */
    void putAll(java.util.Map<? extends K, ? extends V> m);


    /**
     * Removes all of the mappings from this cache.
     * The cache will be empty after this call returns.
     * <p/>
     * This is potentially an expensive operation.
     * <p/>
     */
    void clear();


    /**
     * A cache entry (key-value pair). The <i>only</i> way to obtain a reference
     * to a cache entry is from the iterator of Cache.
     * <p/>
     * Todo evaluate following comment
     * These <tt>Cache.Entry</tt> objects are
     * valid <i>only</i> for the duration of the iteration; more formally,
     * the behavior of a map entry is undefined if the backing map has been
     * modified after the entry was returned by the iterator, except through
     * the <tt>setValue</tt> operation on the map entry.
     */
    interface Entry<K, V> {
        /**
         * @see java.util.Map.Entry#getKey()
         */
        K getKey();

        /**
         * @see java.util.Map.Entry#getValue()
         */
        V getValue();

        /**
         * NOTE: different signature
         *
         * @see java.util.Map.Entry#setValue(Object)
         */
//    	V setValue(V value);
        void setValue(V value);

        /**
         * @see java.util.Map.Entry#setValue(Object)
         */
//    	V setValue(V value);
        V setValueAndReturnPreviousValue(V value);


        //???? Everyting below is TBD

        int getHits();

        long getLastAccessTime();

        long getLastUpdateTime();

        long getCreationTime();

        long getExpirationTime();

        /**
         * Returns a version counter.
         * An implementation may use timestamps for this or an incrementing
         * number. Timestamps usually have issues with granularity and are harder
         * to use across clusteres or threads, so an incrementing counter is often safer.
         */
        long getVersion();

        boolean isValid();

        long getCost();


        /**
         * Compares the specified object with this map for equality.  Returns
         * <tt>true</tt> if the given object is also a map and the two maps
         * represent the same mappings.  More formally, two maps <tt>m1</tt> and
         * <tt>m2</tt> represent the same mappings if
         * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the
         * <tt>equals</tt> method works properly across different implementations
         * of the <tt>Map</tt> interface.
         *
         * @param o object to be compared for equality with this map
         * @return <tt>true</tt> if the specified object is equal to this map
         */
        boolean equals(Object o);

        /**
         * Returns the hash code value for this map.  The hash code of a map is
         * defined to be the sum of the hash codes of each entry in the map's
         * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
         * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
         * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
         * {@link Object#hashCode}.
         *
         * @return the hash code value for this map
         * @see Map.Entry#hashCode()
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        int hashCode();
    }

    // Comparison and hashing

//    boolean equals(Object o);

//    int hashCode();

    /**
     * @return the CacheConfiguration, which is immutable
     */
    CacheConfiguration getConfiguration();

    /**
     * Suspect potential high cost
     */

    /**
     * This is a potentially expensive operation in a distributed cache.
     * <p/>
     * This is not implemented by memcache.
     * <p/>
     * Terracotta ok.
     * <p/>
     * Coherence and grids. You need to go to each shard/partition.
     * <p/>
     * Should this be in JMX?
     *
     * @see java.util.Map#size()
     */
    int size();

    /**
     * @see java.util.Map#containsValue(Object)
     */
    boolean containsValue(Object value);

    /**
     * Suspect Usefulness only
     */

    /**
     * @see java.util.Map#isEmpty()
     */
    boolean isEmpty();
}
