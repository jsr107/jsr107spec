package javax.cache;

/**
 * This is an alternative proposal to {@link Cache} which does NOT extend Map.
 * It is based on {@link java.util.concurrent.ConcurrentMap} but adjusts some
 * method signatures to better suit a distributed system by, for example,
 * not returning values on put or remove.
 *
 *
 * OPEN ISSUES:
 * - should all methods throw CacheException?
 * - resolve overlap/conflict between inner interface Entry and CacheEntry
 * - Cache Statistics? JMX?
 * - do we need the Iterable methods?
 *
 * These methods are ?blocking synchronous?. We need to define what that means.
 *
 * TODO: this is only partially done
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K, V>> {

    /**
     * Cache specific things
     */

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
    java.util.Map<K, V> getAll(java.util.Collection keys);

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
    void load(Object key);

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
    void loadAll(java.util.Collection keys);

    /**
     * Returns the CacheEntry object associated with the object identified by
     * "key". If the object is not in the cache a null is returned.
     */
    CacheEntry<K, V> getCacheEntry(Object key);

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

    // ************************************
    // Methods below based on java.util.Map
    // ************************************


    /**
     * @see java.util.Map#containsKey(Object)
     */
    boolean containsKey(K key);

    /**
     * TODO: Should this be K? Why does Map do this?
     * @see java.util.Map#get(Object)
     */
    V get(Object key);

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
     * Note: can fire an event on a listener.
     */
    void clear();

    // Views

    /**
     * Potentially expensive.
     * NOTE: Iterable instead of Set
     *
     * @see java.util.Map#keySet()
     */
//    Set<K> keySet();
    Iterable<K> keys();

    /**
     * Potentially expensive.
     * NOTE: Iterable instead of Collection
     *
     * @see java.util.Map#values()
     */
//    Collection<V> values();
    Iterable<V> values();

    /**
     * Potentially expensive.
     * NOTE: Iterable instead of Collection
     * TODO: Maybe CacheEntry is used instead of Entry
     *
     * @see java.util.Map#entrySet()
     */
//    Set<Map.Entry<K, V>> entrySet();
    Iterable<Entry<K, V>> entries();

    /**
     * TODO: Maybe CacheEntry should extend this
     * TODO: Maybe CacheEntry is used instead of this
     *
     * @see java.util.Map.Entry
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
         * @see java.util.Map.Entry#setValue(Object)
         */
//    	V setValue(V value);
        void setValue(V value);

        /**
         * @see java.util.Map.Entry#setValue(Object)
         */
//    	V setValue(V value);
        V setValueAndReturnPreviousValue(V value);

//    	boolean equals(Object o);
//    	int hashCode();
    }

    // Comparison and hashing

//    boolean equals(Object o);

//    int hashCode();

    /**
     * @return the CacheConfiguration, which is immutable
     */
    CacheConfiguration getCacheConfiguration();

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
