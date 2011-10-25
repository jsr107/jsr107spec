/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.event.CacheEntryListener;
import javax.cache.event.NotificationScope;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * A Cache provides storage of data for later fast retrieval.
 * <p/>
 * This Cache interface is based on {@link java.util.concurrent.ConcurrentMap} with some modifications for
 * fast distributed performance.
 * <p/>
 * A Cache does not allow null keys or values. Attempts to store a null value or
 * to use a null key either in a get or put operation will result in a {@link NullPointerException}.
 * <p/>
 * Caches use generics throughout providing a level of type safety akin to the collections package.
 * <p/>
 * Cache implements {@link Iterable} for {@link Cache.Entry}, providing support for simplified iteration.
 * However iteration should be used with caution. It is an O(n) operation and may be
 * slow on large or distributed caches.
 * <p/>
 * The Cache API also provides:
 * <ul>
 * <li>read-through caching</li>
 * <li>write-through caching</li>
 * <li>cache loading</li>
 * <li>cache listeners</li>
 * <li>statistics</li>
 * <li>lifecycle</li>
 * <li>configuration</li>
 * </ul>
 * Though not visible in the Cache interface caches may be optionally transactional.
 * <p/>
 * User programs may make use of caching annotations to interact with a cache.
 * <p/>
 * A simple example of how to use a cache is:
 * <pre>
 * String cacheName = "sampleCache";
 * CacheManager cacheManager = Caching.getCacheManager();
 * Cache&lt;Integer, Date&gt; cache = cacheManager.getCache(cacheName);
 * if (cache == null) {
 *   cache = cacheManager.&lt;Integer,Date&gt;createCacheBuilder(cacheName).build();
 * }
 * Date value1 = new Date();
 * Integer key = 1;
 * cache.put(key, value1);
 * Date value2 = cache.get(key);
 * </pre>
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K, V>>, CacheLifecycle {
    /**
     * Gets an entry from the cache.
     * <p/>
     *
     * @param key the key whose associated value is to be returned
     * @return the element, or null, if it does not exist.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws NullPointerException  if the key is null
     * @throws CacheException        if there is a problem fetching the value
     * @see java.util.Map#get(Object)
     */
    V get(Object key);


    /**
     * The getAll method will return, from the cache, a {@link Map} of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception will be thrown.
     * <p/>
     *
     * @param keys The keys whose associated values are to be returned.
     * @return The entries for the specified keys.
     * @throws NullPointerException if keys is null or if keys contains a null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem fetching the values.
     */
    Map<K, V> getAll(Collection<? extends K> keys);


    /**
     * Returns <tt>true</tt> if this cache contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this cache contains a mapping for a key <tt>k</tt> such that
     * <tt>key.equals(k)</tt>.  (There can be at most one such mapping.)
     * <p/>
     *
     * @param key key whose presence in this cache is to be tested.
     * @return <tt>true</tt> if this map contains a mapping for the specified key
     * @throws NullPointerException if key is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       it there is a problem checking the mapping
     * @see java.util.Map#containsKey(Object)
     */
    boolean containsKey(Object key);

    /**
     * The load method provides a means to "pre load" the cache. This method
     * will, asynchronously, load the specified object into the cache using
     * the associated {@link CacheLoader}.
     * If the object already exists in the cache, no action is taken and null is returned.
     * If no loader is associated with the cache
     * no object will be loaded into the cache and null is returned.
     * If a problem is encountered during the retrieving or loading of the object, an exception
     * must be propagated on {@link java.util.concurrent.Future#get()} as a {@link java.util.concurrent.ExecutionException}
     * <p/>
     * @param key            the key
     * @return a Future which can be used to monitor execution.
     * @throws NullPointerException if key is null.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the load
     */
    Future<V> load(K key);

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
     *
     * @param keys           the keys
     * @return a Future which can be used to monitor execution
     * @throws NullPointerException if keys is null or if keys contains a null.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the load
     */
    Future<Map<K, V>> loadAll(Collection<? extends K> keys);

    /**
     * Returns the {@link CacheStatistics} MXBean associated with the cache.
     * May return null if the cache does not support statistics gathering.
     *
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @return the CacheStatisticsMBean
     */
    CacheStatistics getStatistics();

    /**
     * Associates the specified value with the specified key in this cache
     * If the cache previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A cache
     * <tt>c</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) c.containsKey(k)} would return
     * <tt>true</tt>.)
     * <p/>
     * In contrast to the corresponding Map operation, does not return
     * the previous value.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @throws NullPointerException if key is null or if value is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the put
     * @see java.util.Map#put(Object, Object)
     * @see #getAndPut(Object, Object)
     * @see #getAndReplace(Object, Object)
     */
    void put(K key, V value);

    /**
     * Atomically associates the specified value with the specified key in this cache
     * <p/>
     * If the cache previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A cache
     * <tt>c</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) c.containsKey(k)} would return
     * <tt>true</tt>.)
     * <p/>
     * The the previous value is returned, or null if there was no value associated
     * with the key previously.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the value associated with the key at the start of the operation or null if none was associated
     * @throws NullPointerException if key is null or if value is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the put
     * @see java.util.Map#put(Object, Object)
     * @see #put(Object, Object)
     * @see #getAndReplace(Object, Object)
     */
    V getAndPut(K key, V value);

    /**
     * Copies all of the mappings from the specified map to this cache.
     * The effect of this call is equivalent to that
     * of calling {@link #put(Object, Object) put(k, v)} on this cache once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.
     * The order in which the individual puts will occur is undefined.
     * The behavior of this operation is undefined if the specified cache or map is modified while the
     * operation is in progress.
     *
     * @param map mappings to be stored in this cache
     * @throws NullPointerException if map is null or if map contains null keys or values.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the put
     * @see java.util.Map#putAll(java.util.Map)
     */
    void putAll(java.util.Map<? extends K, ? extends V> map);

    /**
     * Atomically associates the specified key with the given value if it is
     * not already associated with a value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (!cache.containsKey(key)) {}
     *       cache.put(key, value);
     *       return true;
     *   } else {
     *       return false;
     *   }</pre>
     * except that the action is performed atomically.
     *
     * In contrast to the corresponding ConcurrentMap operation, does not return
     * the previous value.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return true if a value was set.
     * @throws NullPointerException if key is null or value is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the put
     * @see java.util.concurrent.ConcurrentMap#putIfAbsent(Object, Object)
     */
    boolean putIfAbsent(K key, V value);

    /**
     * Removes the mapping for a key from this cache if it is present.
     * More formally, if this cache contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The cache can contain at most one such mapping.)
     * <p/>
     * <p>Returns <tt>true</tt> if this cache previously associated the key,
     * or <tt>false</tt> if the cache contained no mapping for the key.
     * <p/>
     * <p>The cache will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the cache
     * @return returns false if there was no matching key
     * @throws NullPointerException if key is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the put
     * @see java.util.Map#remove(Object)
     */
    boolean remove(Object key);


    /**
     * Atomically removes the mapping for a key only if currently mapped to the given value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(oldValue)) {
     *       cache.remove(key);
     *       return true;
     *   } else {
     *       return false;
     *   }</pre>
     * except that the action is performed atomically.
     *
     * @param key key whose mapping is to be removed from the cache
     * @param oldValue value expected to be associated with the specified key
     * @return returns false if there was no matching key
     * @throws NullPointerException if key is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem doing the put
     * @see java.util.Map#remove(Object)
     */
    boolean remove(Object key, V oldValue);

    /**
     * Atomically removes the entry for a key only if currently mapped to a given value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key)) {
     *       V oldValue = cache.get(key);
     *       cache.remove(key);
     *       return oldValue;
     *   } else {
     *       return null;
     *   }</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is associated
     * @return the value if one existed or null if no mapping existed for this key
     * @throws NullPointerException          if the specified key or value is null.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException                if there is a problem during the remove
     * @see java.util.Map#remove(Object)
     */
    V getAndRemove(Object key);

    /**
     * Atomically replaces the entry for a key only if currently mapped to a given value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(oldValue)) {
     *       cache.put(key, newValue);
     *       return true;
     *   } else {
     *       return false;
     *   }</pre>
     * except that the action is performed atomically.
     *
     * @param key      key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return <tt>true</tt> if the value was replaced
     * @throws NullPointerException if key is null or if the values are null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem during the replace
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object, Object)
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * Atomically replaces the entry for a key only if currently mapped to some value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key)) {
     *       cache.put(key, value);
     *       return true;
     *   } else {
     *       return false;
     *   }</pre>
     * except that the action is performed atomically.
     *
     * In contrast to the corresponding ConcurrentMap operation, does not return
     * the previous value.
     *
     * @param key   key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return <tt>true</tt> if the value was replaced
     * @throws NullPointerException if key is null or if value is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem during the replace
     * @see #getAndReplace(Object, Object)
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
    boolean replace(K key, V value);

    /**
     * Atomically replaces the entry for a key only if currently mapped to some value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key)) {
     *       V value = cache.get(key, value);
     *       cache.put(key, value);
     *       return value;
     *   } else {
     *       return null;
     *   }</pre>
     * except that the action is performed atomically.
     *
     * @param key   key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         <tt>null</tt> if there was no mapping for the key.
     * @throws NullPointerException if key is null or if value is null
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem during the replace
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
    V getAndReplace(K key, V value);

    /**
     * Removes entries for the specified keys.
     * <p/>
     * The order in which the individual removes will occur is undefined.
     * <p/>
     *
     * @param keys the keys to remove
     * @throws NullPointerException if keys is null or if it contains a null key
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException       if there is a problem during the remove
     */
    void removeAll(Collection<? extends K> keys);

    /**
     * Removes all of the mappings from this cache.
     * <p/>
     * The order in which the individual removes will occur is undefined.
     * This is potentially an expensive operation.
     * <p/>
     *
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws CacheException if there is a problem during the remove
     * @see java.util.Map#clear()
     */
    void removeAll();

    /**
     * Returns a CacheConfiguration.
     * <p/>
     * When status is {@link Status#STARTED} an implementation must respect the following:
     * <ul>
     * <li>Statistics must be mutable when status is {@link Status#STARTED} ({@link CacheConfiguration#setStatisticsEnabled(boolean)})</li>
     * </ul>
     * <p/>
     * If an implementation permits mutation of configuration to a running cache, those changes must be reflected
     * in the cache. In the case where mutation is not allowed {@link InvalidConfigurationException} must be thrown on
     * an attempt to mutate the configuration.
     *
     * @return the {@link CacheConfiguration} of this cache
     */
    CacheConfiguration getConfiguration();

    /**
     * Adds a listener to the notification service. No guarantee is made that listeners will be
     * notified in the order they were added.
     * <p/>
     *
     * @param cacheEntryListener The listener to add. A listener may be added only once, so the same listener with two difference scopes
     *                           is not allowed.
     * @param scope              The notification scope.
     * @param synchronous        whether to listener should be invoked synchronously
     * @return true if the listener is being added and was not already added
     * @throws NullPointerException if any of the arguments are null.
     */
    boolean registerCacheEntryListener(CacheEntryListener<K, V> cacheEntryListener, NotificationScope scope, boolean synchronous);

    /**
     * Removes a call back listener.
     *
     * @param cacheEntryListener the listener to remove
     * @return true if the listener was present
     */
    boolean unregisterCacheEntryListener(CacheEntryListener<?, ?> cacheEntryListener);

    /**
     * Return the name of the cache.
     *
     * @return the name of the cache.
     */
    String getName();

    /**
     * Gets the CacheManager managing this cache.
     * <p/>
     * A cache can be in only one CacheManager.
     *
     * @return the manager
     */
    CacheManager getCacheManager();
    
    /**
     * Return an object of the specified type to allow access to the provider-specific API. If the provider's
     * implementation does not support the specified class, the {@link IllegalArgumentException} is thrown.
     * 
     * @param cls he class of the object to be returned. This is normally either the underlying implementation class or an interface that it implements. 
     * @return an instance of the specified class 
     * @throws IllegalArgumentException if the provider doesn't support the specified class.
     */
    <T> T unwrap(java.lang.Class<T> cls);

    /**
     * {@inheritDoc}
     * The ordering of the entries is undefined
     */
    Iterator<Cache.Entry<K, V>> iterator();
    /**
     * A cache entry (key-value pair).
     */
    interface Entry<K, V> {

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         */
        K getKey();

        /**
         * Returns the value stored in the cache when this entry was created.
         *
         * @return the value corresponding to this entry
         */
        V getValue();
    }
}
