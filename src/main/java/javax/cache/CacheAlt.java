package javax.cache;

/**
 * This is an alternative proposal to {@link Cache} which does NOT extend Map.
 * It is based on {@link java.util.concurrent.ConcurrentMap} but adjusts some
 * method signatures to better suit a distributed system by, for example,
 * not returning values on put or remove.
 *
 * OPEN ISSUES:
 * - do we use this :)
 * - should all methods throw CacheException?
 * - resolve overlap/conflict between inner interface Entry and CacheEntry
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public interface CacheAlt<K,V> {
    /**
     * @see Cache#getAll(java.util.Collection)
     */
    java.util.Map<K,V> getAll(java.util.Collection keys);
    /**
     * @see Cache#load(Object)
     */
    void load(Object key);
    /**
     * @see Cache#load(Object)
     */
    void loadAll(java.util.Collection keys);
    /**
     * @see Cache#getCacheEntry(Object)
     */
    CacheEntry<K,V> getCacheEntry(Object key);
    /**
     * @see Cache#getCacheEntry(Object)
     */
    CacheStatistics getCacheStatistics();
    /**
     * @see javax.cache.Cache#evict()
     */
    void evict();
    /**
     * @see javax.cache.Cache#addListener(CacheListener)
     */
    void addListener(CacheListener listener);
    /**
     * @see javax.cache.Cache#removeListener(CacheListener)
     */
    void removeListener(CacheListener listener);

    // ************************************
    // Methods below based on java.util.Map
    // ************************************

    // Query Operations

    /**
     * @see java.util.Map#size()
     */
    int size();

    /**
     * @see java.util.Map#isEmpty()
     */
    boolean isEmpty();

    /**
     * @see java.util.Map#containsKey(Object)
     */
    boolean containsKey(Object key);

//    /**
//     * @see java.util.Map#containsValue(Object)
//     */
//    boolean containsValue(Object value);

    /**
     * @see java.util.Map#get(Object)
     */
    V get(Object key);

    // Modification Operations

    /**
     * NOTE: different return value
     * @see java.util.Map#put(Object, Object)
     */
//    V put(K key, V value);
    void put(K key, V value);

    /**
     * @see java.util.Map#put(Object, Object)
     */
//    V put(K key, V value);
    V putAndReturnPreviousValue(K key, V value);

    /**
     * NOTE: different return value
     * @see java.util.concurrent.ConcurrentMap#putIfAbsent(Object, Object)
     */
//    V putIfAbsent(K key, V value);
    boolean putIfAbsent(K key, V value);

    /**
     * NOTE: different return value
     * @see java.util.Map#remove(Object)
     */
//    V remove(Object key);
    boolean remove(Object key);

    /**
     * @see java.util.Map#remove(Object)
     */
//    V remove(Object key);
    V removeAndReturnPreviousValue(Object key);

    /**
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object, Object)
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * NOTE: different return value
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
     * @see java.util.Map#clear()
     */
    void clear();


    // Views

    /**
     * NOTE: Iterator instead of Set
     * @see java.util.Map#keySet()
     */
//    Set<K> keySet();
    Iterable<K> keys();

    /**
     * NOTE: Iterator instead of Collection
     * @see java.util.Map#values()
     */
//    Collection<V> values();
    Iterable<V> values();

    /**
     * NOTE: Iterator instead of Collection
     * TODO: Maybe CacheEntry is used instead of Entry
     * @see java.util.Map#entrySet()
     */
//    Set<Map.Entry<K, V>> entrySet();
    Iterable<Entry<K, V>> entries();

    /**
     * TODO: Maybe CacheEntry should extend this
     * TODO: Maybe CacheEntry is used instead of this
     * @see java.util.Map.Entry
     */
    interface Entry<K,V> {
        /**
         * see java.util.Map.Entry#getKey
         */
        K getKey();
        /**
         * see java.util.Map.Entry#getValue
         */
        V getValue();

        /**
         * NOTE: different signature
         * see java.util.Map.Entry#setValue
         */
//    	V setValue(V value);
    	void setValue(V value);

        /**
         * see java.util.Map.Entry#setValue
         */
//    	V setValue(V value);
    	V setValueAndReturnPreviousValue(V value);

//    	boolean equals(Object o);
//    	int hashCode();
    }

    // Comparison and hashing

//    boolean equals(Object o);

//    int hashCode();
}
