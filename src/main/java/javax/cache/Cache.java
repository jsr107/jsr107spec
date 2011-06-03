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
import java.util.Map;
import java.util.concurrent.Future;

/**
 * A Cache provides temporary storage for later fase retrieval.
 * <p/>
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
 * Cache implements {@link Iterable}, providing support for simplified iteration. However
 * iteration should be used with caution. It is an O(n) operation and may be
 * slow on large or distributed caches.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K, V>>, Lifecycle {
    /**
     * Gets an entry from the cache.
     * <p/>
     * A return value of
     * {@code null} does not <i>necessarily</i> indicate that the cache
     * contains no mapping for the key; it's also possible that the cache
     * explicitly maps the key to {@code null}.  The {@link #containsKey(Object)}
     * operation may be used to distinguish these two cases.
     *
     * @param key the key whose associated value is to be returned
     * @return the element, or null, if it does not exist.
     * @throws IllegalStateException if the cache is not {@link Status#STARTED}
     * @throws NullPointerException  if the key is null
     * @throws CacheException        if there is a problem fetching the value
     */
    V get(Object key) throws CacheException;


    /**
     * The getAll method will return, from the cache, a {@link Map} of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception will be thrown.
     * <p/>
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and
     * not finding the object in the cache. In both cases a null is returned.
     *
     * @param keys The keys whose associated values are to be returned.
     * @return The entries for the specified keys.
     * @throws NullPointerException if keys is null or if keys contains a null
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
     *            null is permitted but the cache will always return null
     * @return <tt>true</tt> if this map contains a mapping for the specified key
     */
    boolean containsKey(Object key);

    /**
     * The load method provides a means to "pre load" the cache. This method
     * will, asynchronously, load the specified object into the cache using
     * the associated {@link CacheLoader}.
     * If the object already exists in the cache, no action is taken and null is returned.
     * If no loader is associated with the cache, and specific loader is null,
     * no object will be loaded into the cache and null is returned.
     * If a problem is encountered during the retrieving or loading of the object, an exception
     * must be propagated on {@link java.util.concurrent.Future#get()} as a {@link java.util.concurrent.ExecutionException}
     * <p/>
     * If the "arg" argument is set, the arg object will be passed to the
     * {@link CacheLoader#load(K, Object)} method.  The cache will not dereference the object. If
     * no "arg" value is provided a null will be passed to the load method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and not
     * finding the object in the cache. In both cases a null is returned.
     *
     * @param key the key
     * @param specificLoader a specific loader to use. If null the default loader is used.
     * @param loaderArgument provision for additional parameters to be passed to the loader
     * @return a Future which can be used to monitor execution.
     * @throws NullPointerException if key is null.
     */
    Future<V> load(K key, CacheLoader<K, V> specificLoader, Object loaderArgument);

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
     * {@link CacheLoader#loadAll(java.util.Collection, Object)} method.
     * The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     *
     * @param keys the keys
     * @param specificLoader a specific loader to use. If null the default loader is used.
     * @param loaderArgument provision for additional parameters to be passed to the loader
     * @return a Future which can be used to monitor execution
     * @throws NullPointerException if keys is null or if keys contains a null.
     */
    Future<Map<K, V>> loadAll(Collection<? extends K> keys, CacheLoader<K, V> specificLoader, Object loaderArgument);

    /**
     * Returns the {@link CacheStatisticsMBean} object associated with the cache.
     * May return null if the cache does not support statistics gathering.
     * @return the CacheStatisticsMBean
     */
    CacheStatisticsMBean getCacheStatistics();

    /**
     * Associates the specified value with the specified key in this cache
     * If the cache previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A cache
     * <tt>c</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) c.containsKey(k)} would return
     * <tt>true</tt>.)
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     *
     * @throws NullPointerException if key is null
     * @see java.util.Map#put(Object, Object)
     */
    void put(K key, V value);

    /**
     * Copies all of the mappings from the specified map to this cache.
     * The effect of this call is equivalent to that
     * of calling {@link #put(Object,Object) put(k, v)} on this cache once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.  The behavior of this operation is undefined if the
     * specified cache or map is modified while the operation is in progress.
     *
     * @param map mappings to be stored in this cache
     * @throws NullPointerException if map is null or if map contains null keys.
     * @see java.util.Map#putAll(java.util.Map)
     */
    void putAll(java.util.Map<? extends K, ? extends V> map);

    /**
     * If the specified key is not already associated
     * with a value, associate it with the given value.
     * This is equivalent to
     * <pre>
     *   if (!cache.containsKey(key)) {}
     *       cache.put(key, value);
     *       return true;
     *   } else
     *       return false;</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return true if a value was set.
     * @throws NullPointerException if key is null
     * @see java.util.concurrent.ConcurrentMap#putIfAbsent(Object, Object)
     */
    boolean putIfAbsent(K key, V value);

    /**
     * Removes the mapping for a key from this cache if it is present.
     * More formally, if this cache contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The cache can contain at most one such mapping.)
     *
     * <p>Returns <tt>true</tt> if this cache previously associated the key,
     * or <tt>false</tt> if the cache contained no mapping for the key.
     *
     * <p>The cache will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the cache
     * @return returns false if there was no matching key
     * @throws NullPointerException if key is null
     * @see java.util.Map#remove(Object)
     */
    boolean remove(Object key);

    /**
     * Removes the entry for a key only if currently mapped to a given value.
     * <p/>
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(value)) {
     *       V oldValue = cache.get(key);
     *       cache.remove(key);
     *       return oldValue;
     *   } else return null;</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is associated
     * @return <tt>true</tt> if the value was removed
     * @throws UnsupportedOperationException if the <tt>getAndRemove</tt> operation
     *                                       is not supported by this cache
     * @throws ClassCastException            if the key or value is of an inappropriate
     *                                       type for this cache (optional)
     * @throws NullPointerException          if the specified key is null.
     * @see java.util.Map#remove(Object)
     */
    V getAndRemove(Object key);

    /**
     * Replaces the entry for a key only if currently mapped to a given value.
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(oldValue)) {
     *       cache.put(key, newValue);
     *       return true;
     *   } else return false;</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is associated
     * @param oldValue value expected to be associated with the specified key
     * @param newValue value to be associated with the specified key
     * @return <tt>true</tt> if the value was replaced
     * @throws NullPointerException if the specified key is null
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object, Object)
     */
    boolean replace(K key, V oldValue, V newValue);

    /**
     * Replaces the entry for a key only if currently mapped to some value.
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key)) {
     *       return cache.put(key, value);
     *   } else return null;</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return <tt>true</tt> if the value was replaced
     * @throws NullPointerException if the specified key is null
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
    boolean replace(K key, V value);

    /**
     * Replaces the entry for a key only if currently mapped to some value.
     * This is equivalent to
     * <pre>
     *   if (cache.containsKey(key)) {
     *       return cache.getAndReplace(key, value);
     *   } else return null;</pre>
     * except that the action is performed atomically.
     *
     * @param key key with which the specified value is associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with the specified key, or
     *         <tt>null</tt> if there was no mapping for the key.
     *         (A <tt>null</tt> return can also indicate that the cache
     *         previously associated <tt>null</tt> with the key,
     *         if the implementation supports null values.)
     * @throws NullPointerException if the specified key is null
     * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
     */
    V getAndReplace(K key, V value);

    /**
     * Removes entries for the specified keys
     * <p/>
     *
     * @param keys the keys to remove
     */
    void removeAll(Collection<? extends K> keys);

    /**
     * Removes all of the mappings from this cache.
     * The cache will be empty after this call returns.
     * <p/>
     * This is potentially an expensive operation.
     * <p/>
     */
    void removeAll();

    /**
     * Returns a CacheConfiguration.
     * <p/>
     * Whether the configuration is mutable after the cache has {@link Status#STARTED} is up to the implementation,
     * however the confiuration cannot be changed unless the changes are applied to the cache.
     *
     * @return the {@link CacheConfiguration}
     */
    CacheConfiguration getConfiguration();

    /**
     * Adds a listener to the notification service. No guarantee is made that listeners will be
     * notified in the order they were added.
     * <p/>
     *
     * @param cacheEntryListener The listener to add. A listener may be added only once, so the same listener with two difference scopes
     * is not allowed.
     * @param scope              The notification scope. If this parameter is null, the {@link NotificationScope#ALL} scope is used.
     * @return true if the listener is being added and was not already added
     */
    boolean registerCacheEntryListener(CacheEntryListener cacheEntryListener, NotificationScope scope);

    /**
     * Removes a call back listener.
     *
     * @param cacheEntryListener the listener to remove
     * @return true if the listener was present
     */
    boolean unregisterCacheEntryListener(CacheEntryListener cacheEntryListener);


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
         * different implementations of the <tt>Cache.Entry</tt> interface.
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
