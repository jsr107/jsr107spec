/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A {@link Cache} is a Map-like data structure that provides temporary storage
 * of application data.
 * <p/>
 * Like {@link Map}s, {@link Cache}s
 * <ol>
 *   <li>store key-value pairs, each referred to as an {@link Entry}</li>
 *   <li>allow use Java Generics to improve application type-safety</li>
 *   <li>are {@link Iterable}</li>
 * </ol>
 * <p/>
 * Unlike {@link Map}s, {@link Cache}s
 * <ol>
 *   <li>do not allow null keys or values.  Attempts to use <code>null</code>
 *       will result in a {@link NullPointerException}</li>
 *   <li>provide the ability to read values from a
 *       {@link javax.cache.integration.CacheLoader} (read-through-caching)
 *       when a value being requested is not in a cache</li>
 *   <li>provide the ability to write values to a
 *       {@link javax.cache.integration.CacheWriter} (write-through-caching)
 *       when a value being created/updated/removed from a cache</li>
 *   <li>provide the ability to observe cache entry changes</li>
 *   <li>may capture and measure operational statistics</li>
 *   <li>may be transactional</li>
 * </ol>
 * <p/>
 * A simple example of how to use a cache is:
 * <pre><code>
 * String cacheName = "sampleCache";
 * CachingProvider provider = Caching.getCachingProvider();
 * CacheManager manager = provider.getCacheManager();
 * Cache&lt;Integer, Date&gt; cache = manager.getCache(cacheName,
 *                                                     Integer.class,
 *                                                     Date.class);
 * Date value1 = new Date();
 * Integer key = 1;
 * cache.put(key, value1);
 * Date value2 = cache.get(key);
 * </code></pre>
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @since 1.0
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K, V>>, Closeable {
  /**
   * Gets an entry from the cache.
   * <p/>
   * If the cache is configured read-through, and get would return null because
   * the entry is missing from the cache, the Cache's
   * {@link javax.cache.integration.CacheLoader} is called which will attempt
   * to load the entry.
   *
   * @param key the key whose associated value is to be returned
   * @return the element, or null, if it does not exist.
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws NullPointerException  if the key is null
   * @throws CacheException        if there is a problem fetching the value
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   */
  V get(K key);

  /**
   * Gets a collection of entries from the {@link Cache}, returning them as
   * {@link Map} of the values associated with the set of keys requested.
   * <p/>
   * If the cache is configured read-through, and a get would return null
   * because an entry is missing from the cache, the Cache's
   * {@link javax.cache.integration.CacheLoader} is called which will attempt
   * to load the entry. This is done for each key in the set for which this is
   * the case. If an entry cannot be loaded for a given key, the key will not be
   * present in the returned Map.
   * <p/>
   *
   * @param keys The keys whose associated values are to be returned.
   * @return A map of entries that were found for the given keys. Keys not found
   *         in the cache are not in the returned map.
   * @throws NullPointerException  if keys is null or if keys contains a null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem fetching the values
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and any key type is incompatible with that
   *                               which has been configured for the
   *                               {@link Cache}
   */
  Map<K, V> getAll(Set<? extends K> keys);

  /**
   * Determines if the {@link Cache} contains an entry for the specified key.
   * <p/>
   * More formally, returns <tt>true</tt> if and only if this cache contains a
   * mapping for a key <tt>k</tt> such that <tt>key.equals(k)</tt>.
   * (There can be at most one such mapping.)
   *
   * @param key key whose presence in this cache is to be tested.
   * @return <tt>true</tt> if this map contains a mapping for the specified key
   * @throws NullPointerException  if key is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        it there is a problem checking the mapping
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the
   *                               {@link Cache}
   * @see java.util.Map#containsKey(Object)
   */
  boolean containsKey(K key);

  /**
   * Asynchronously loads the specified entries into the cache using the
   * configured {@link javax.cache.integration.CacheLoader} for the given keys.
   * <p/>
   * If an entry for a key already exists in the Cache, a value will be loaded
   * if and only if <code>replaceExistingValues</code> is true.   If no loader
   * is configured for the cache, no objects will be loaded.  If a problem is
   * encountered during the retrieving or loading of the objects,
   * an exception is provided to the {@link CompletionListener}.  Once the
   * operation has completed, the specified CompletionListener is notified.
   * <p/>
   * Implementations may choose to load multiple keys from the provided
   * {@link Set} in parallel.  Iteration however must not occur in parallel,
   * thus allow for non-thread-safe {@link Set}s to be used.
   * <p/>
   * The thread on which the completion listener is called is implementation
   * dependent. An implementation may also choose to serialize calls to
   * different CompletionListeners rather than use a thread per
   * CompletionListener.
   *
   * @param keys                   the keys to load
   * @param replaceExistingValues  when true existing values in the Cache will
   *                               be replaced by those loaded from a CacheLoader
   * @param completionListener     the CompletionListener (may be null)
   * @throws NullPointerException  if keys is null or if keys contains a null.
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        thrown if there is a problem performing the
   *                               load. This may also be thrown on calling if
   *                               their are insufficient threads available to
   *                               perform the load.
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and any key type is incompatible with that
   *                               which has been configured for the
   *                               {@link Cache}
   */
  void loadAll(Set<? extends K> keys, boolean replaceExistingValues,
               CompletionListener completionListener);

  /**
   * Associates the specified value with the specified key in the cache.
   * <p/>
   * If the {@link Cache} previously contained a mapping for the key, the old
   * value is replaced by the specified value.  (A cache
   * <tt>c</tt> is said to contain a mapping for a key <tt>k</tt> if and only
   * if {@link #containsKey(Object) c.containsKey(k)} would return
   * <tt>true</tt>.)
   *
   * @param key   key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   * @throws NullPointerException  if key is null or if value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those which have been configured for the
   *                               {@link Cache}
   * @see java.util.Map#put(Object, Object)
   * @see #getAndPut(Object, Object)
   * @see #getAndReplace(Object, Object)
   */
  void put(K key, V value);

  /**
   * Associates the specified value with the specified key in this cache,
   * returning an existing value if one existed.
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
   * @return the value associated with the key at the start of the operation or
   *         null if none was associated
   * @throws NullPointerException  if key is null or if value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those which have been configured for the
   *                               {@link Cache}
   * @see #put(Object, Object)
   * @see #getAndReplace(Object, Object)
   */
  V getAndPut(K key, V value);

  /**
   * Copies all of the entries from the specified map to the {@link Cache}.
   * <p/>
   * The effect of this call is equivalent to that of calling
   * {@link #put(Object, Object) put(k, v)} on this cache once for each mapping
   * from key <tt>k</tt> to value <tt>v</tt> in the specified map.
   * <p/>
   * The order in which the individual puts occur is undefined.
   * <p/>
   * The behavior of this operation is undefined if entries in the cache
   * corresponding to entries in the map are modified or removed while this
   * operation is in progress. or if map is modified while the operation is in
   * progress.
   * <p/>
   * In Default Consistency mode, individual puts occur atomically but not
   * the entire putAll.  Listeners may observe individual updates.
   *
   * @param map mappings to be stored in this cache
   * @throws NullPointerException  if map is null or if map contains null keys
   *                               or values.
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put.
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and any of the key or value types are
   *                               incompatible with those that have been
   *                               configured for the {@link Cache}
   */
  void putAll(java.util.Map<? extends K, ? extends V> map);

  /**
   * Atomically associates the specified key with the given value if it is
   * not already associated with a value.
   * <p/>
   * This is equivalent to:
   * <code>
   *   if (!cache.containsKey(key)) {}
   *       cache.put(key, value);
   *       return true;
   *   } else {
   *       return false;
   *   }
   * </code>
   * except that the action is performed atomically.
   *
   * @param key   key with which the specified value is to be associated
   * @param value value to be associated with the specified key
   * @return true if a value was set.
   * @throws NullPointerException  if key is null or value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   */
  boolean putIfAbsent(K key, V value);

  /**
   * Removes the mapping for a key from this cache if it is present.
   * <p/>
   * More formally, if this cache contains a mapping from key <tt>k</tt> to
   * value <tt>v</tt> such that
   * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
   * is removed.  (The cache can contain at most one such mapping.)
   * <p/>
   * <p>Returns <tt>true</tt> if this cache previously associated the key,
   * or <tt>false</tt> if the cache contained no mapping for the key.
   * <p/>
   * The cache will not contain a mapping for the specified key once the
   * call returns.
   *
   * @param key key whose mapping is to be removed from the cache
   * @return returns false if there was no matching key
   * @throws NullPointerException  if key is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the
   *                               {@link Cache}
   */
  boolean remove(K key);

  /**
   * Atomically removes the mapping for a key only if currently mapped to the
   * given value.
   * <p/>
   * This is equivalent to:
   * <code>
   *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(oldValue)) {
   *       cache.remove(key);
   *       return true;
   *   } else {
   *       return false;
   *   }
   * </code>
   * except that the action is performed atomically.
   *
   * @param key      key whose mapping is to be removed from the cache
   * @param oldValue value expected to be associated with the specified key
   * @return returns false if there was no matching key
   * @throws NullPointerException  if key is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   */
  boolean remove(K key, V oldValue);

  /**
   * Atomically removes the entry for a key only if currently mapped to a given
   * value.
   * <p/>
   * This is equivalent to:
   * <code>
   *   if (cache.containsKey(key)) {
   *       V oldValue = cache.get(key);
   *       cache.remove(key);
   *       return oldValue;
   *   } else {
   *       return null;
   *   }
   * </code>
   * except that the action is performed atomically.
   *
   * @param key key with which the specified value is associated
   * @return the value if one existed or null if no mapping existed for this key
   * @throws NullPointerException  if the specified key or value is null.
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the remove
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   */
  V getAndRemove(K key);

  /**
   * Atomically replaces the entry for a key only if currently mapped to a
   * given value.
   * <p/>
   * This is equivalent to:
   * <code>
   *   if (cache.containsKey(key) &amp;&amp; cache.get(key).equals(oldValue)) {
   *       cache.put(key, newValue);
   *       return true;
   *   } else {
   *       return false;
   *   }
   * </code>
   * except that the action is performed atomically.
   *
   * @param key      key with which the specified value is associated
   * @param oldValue value expected to be associated with the specified key
   * @param newValue value to be associated with the specified key
   * @return <tt>true</tt> if the value was replaced
   * @throws NullPointerException  if key is null or if the values are null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the replace
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those which have been configured for the
   *                               {@link Cache}
   */
  boolean replace(K key, V oldValue, V newValue);

  /**
   * Atomically replaces the entry for a key only if currently mapped to some value.
   * <p/>
   * This is equivalent to
   * <code>
   *   if (cache.containsKey(key)) {
   *       cache.put(key, value);
   *       return true;
   *   } else {
   *       return false;
   *   }</code>
   * except that the action is performed atomically.
   *
   * @param key   key with which the specified value is associated
   * @param value value to be associated with the specified key
   * @return <tt>true</tt> if the value was replaced
   * @throws NullPointerException  if key is null or if value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the replace
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   * @see #getAndReplace(Object, Object)
   */
  boolean replace(K key, V value);

  /**
   * Atomically replaces the value for a given key if and only if there is a
   * value currently mapped by the key.
   * <p/>
   * This is equivalent to
   * <code>
   *   if (cache.containsKey(key)) {
   *       V oldValue = cache.get(key);
   *       cache.put(key, value);
   *       return oldValue;
   *   } else {
   *       return null;
   *   }
   * </code>
   * except that the action is performed atomically.
   *
   * @param key   key with which the specified value is associated
   * @param value value to be associated with the specified key
   * @return the previous value associated with the specified key, or
   *         <tt>null</tt> if there was no mapping for the key.
   * @throws NullPointerException  if key is null or if value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the replace
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
   */
  V getAndReplace(K key, V value);

  /**
   * Removes entries for the specified keys.
   * <p/>
   * The order in which the individual removes will occur is undefined.
   *
   * @param keys the keys to remove
   * @throws NullPointerException  if keys is null or if it contains a null key
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the remove
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and any key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   */
  void removeAll(Set<? extends K> keys);

  /**
   * Removes all of the mappings from this cache.
   * <p/>
   * The order in which the individual removes will occur is undefined.
   * <p/>
   * This is potentially an expensive operation as listeners are invoked.
   * Use {@link #clear()} to avoid this.
   *
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the remove
   * @see #clear()
   */
  void removeAll();

  /**
   * Clears the contents of the cache, without notifying listeners or
   * {@link javax.cache.integration.CacheWriter}s.
   *
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the remove
   */
  void clear();

  /**
   * Obtains an immutable representation of the {@link Configuration} that
   * was used to configure the {@link Cache}.
   *
   * @return the {@link javax.cache.configuration.Configuration}
   */
  Configuration<K, V> getConfiguration();

  /**
   * Invokes an {@link EntryProcessor} against the {@link Entry} specified by
   * the provided key.  If an {@link Entry} does not exist for the specified
   * key, an attempt is made to loaded it (if a loader is configured) or an
   * empty {@link Entry} is created and used instead.
   *
   * @param key            the key to the entry
   * @param entryProcessor the {@link EntryProcessor} to invoke
   * @param arguments      additional arguments to pass to the
   *                       {@link EntryProcessor}
   * @return the result of the processing, if any, defined by the
   *         {@link EntryProcessor} implementation
   *
   * @throws NullPointerException  if key or {@link EntryProcessor} are null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if an exception occurred while executing
   *                               the {@link EntryProcessor} (the causing
   *                               exception will be wrapped by the
   *                               CacheException)
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   * @see EntryProcessor
   */
  <T> T invoke(K key,
               EntryProcessor<K, V, T> entryProcessor,
               Object... arguments);

  /**
   * Invokes an {@link EntryProcessor} against the set of {@link Entry}s
   * specified by the set of keys.  If an {@link Entry} does not exist for a
   * specified key, an attempt is made to loaded it (if a loader is configured)
   * or an empty {@link Entry} is created and used instead.
   * <p/>
   * The order in which the entries for the keys are processed is undefined.
   * Implementations may choose to process the entries in any order, including
   * concurrently.  Furthermore there is no guarantee implementations will
   * use the same {@link EntryProcessor} instance to process each entry, as
   * the case may be in a non-local cache topology.
   *
   * @param keys           the set of keys for entries to process
   * @param entryProcessor the {@link EntryProcessor} to invoke
   * @param arguments      additional arguments to pass to the
   *                       {@link EntryProcessor}
   * @return the map of results of the processing per key, if any, defined by the
   *         {@link EntryProcessor} implementation.  No mappings will be
   *         returned for {@link EntryProcessor}s that return a <code>null</code>
   *         value for a key
   *
   * @throws NullPointerException  if keys or {@link EntryProcessor} are null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if an exception occurred while executing
   *                               the {@link EntryProcessor} (the causing
   *                               exception will be wrapped by the
   *                               CacheException)
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   * @see EntryProcessor
   */
  <T> Map<K, T> invokeAll(Set<? extends K> keys,
                          EntryProcessor<K, V, T> entryProcessor,
                          Object... arguments);

  /**
   * Return the name of the cache.
   *
   * @return the name of the cache.
   */
  String getName();

  /**
   * Gets the {@link CacheManager} that owns and manages the {@link Cache}.
   *
   * @return the manager or <code>null</code> if the {@link Cache} is not
   *         managed
   */
  CacheManager getCacheManager();

  /**
   * Signals to the {@link CacheManager} that the {@link Cache} should no longer
   * be managed.
   * <p/>
   * After calling this method the following conditions will be met;
   * <li>
   * <ul>{@link Cache#isClosed()} will return true</ul>
   * <ul>attempting to use operational methods on the {@link Cache} will throw
   * an {@link IllegalStateException}.</ul>
   * <ul>the {@link javax.cache.Cache#getName()} no longer appear in the list of
   * cache names returned by {@link javax.cache.CacheManager#getCacheNames()}.</ul>
   * <ul>any {@link javax.cache.integration
   * .CacheLoader}, {@link javax.cache.integration.CacheWriter},
   * {@link javax.cache.event.CacheEntryListener}s or
   * {@link javax.cache.expiry.ExpiryPolicy}
   * configured to the {@link Cache} that implement {@link java.io.Closeable}
   * will be closed.</ul>
   * <uL>all resources allocated to the {@link Cache} by the {@link CacheManager}
   * will be freed.</ul>
   * <p/>
   * A closed {@link Cache} instance cannot be reused.
   */
  void close();

  /**
   * Determines whether this Cache instance has been closed. A Cache is
   * considered closed if;
   * <ol>
   * <li>the {@link #close()} method has been called</li>
   * <li>the associated {@link #getCacheManager()} has been closed, or</li>
   * <li>the Cache has been removed from the associated
   *     {@link #getCacheManager()}</li>
   * </ol>
   * <p/>
   * This method generally cannot be called to determine whether a Cache instance
   * is valid or invalid. A typical client can determine that a Cache is invalid
   * by catching any exceptions that might be thrown when an operation is attempted.
   *
   * @return true if this Cache instance is closed; false if it is still open
   */
  boolean isClosed();

  /**
   * Provides a standard way to access the underlying concrete caching
   * implementation to provide access to further, proprietary features.
   * <p/>
   * If the provider's implementation does not support the specified class,
   * the {@link IllegalArgumentException} is thrown.
   *
   * @param clazz the proprietary class or interface of the underlying concrete
   *              cache. It is this type which is returned.
   * @return an instance of the underlying concrete cache
   * @throws IllegalArgumentException if the caching provider doesn't support
   *         the specified class.
   */
  <T> T unwrap(java.lang.Class<T> clazz);

  /**
   * {@inheritDoc}
   * <p/>
   * The ordering of iteration over entries is undefined.
   * <p/>
   * During iteration, any entries that are a). read will have their appropriate
   * CacheEntryReadListeners notified and b). removed will have their appropriate
   * CacheEntryRemoveListeners notified.
   * <p/>
   * {@link java.util.Iterator#next()} may return null if the entry is no
   * longer present, has expired or has been evicted.
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

    /**
     * Provides a standard way to access the underlying concrete cache entry
     * implementation in order to provide access to further, proprietary features.
     * <p/>
     * If the provider's implementation does not support the specified class,
     * the {@link IllegalArgumentException} is thrown.
     *
     * @param clazz the proprietary class or interface of the underlying
     *              concrete cache. It is this type which is returned.
     * @return an instance of the underlying concrete cache
     * @throws IllegalArgumentException if the caching provider doesn't support
     *         the specified class.
     */
    <T> T unwrap(Class<T> clazz);
  }

  /**
   * A mutable representation of a {@link Cache} {@link Entry}.
   *
   * @param <K> the type of key
   * @param <V> the type of value
   */
  public interface MutableEntry<K, V> extends Entry<K, V> {

    /**
     * Checks for the existence of the entry in the cache
     *
     * @return true if the entry exists
     */
    boolean exists();

    /**
     * Removes the entry from the Cache
     */
    void remove();

    /**
     * Sets or replaces the value associated with the key
     * If {@link #exists} is false and setValue is called
     * then a mapping is added to the cache visible once the EntryProcessor
     * completes. Moreover a second invocation of {@link #exists()}
     * will return true.
     * <p/>
     *
     * @param value the value to update the entry with
     * @throws ClassCastException if the implementation supports and is
     *                            configured to perform runtime-type-checking,
     *                            and value type is incompatible with that
     *                            which has been configured for the
     *                            {@link Cache}
     */
    void setValue(V value);
  }

  /**
   * An invocable function that allows applications to perform compound
   * operations on a {@link Cache.Entry} atomically, according the the defined
   * consistency of a {@link Cache}.
   * <p/>
   * Any {@link Cache.Entry} mutations will not take effect until after the
   * {@link EntryProcessor#process(javax.cache.Cache.MutableEntry, Object...)}
   * method has completed execution.
   * <p/>
   * If an exception is thrown by an {@link EntryProcessor}, the exception will
   * be returned wrapped in an {@link CacheException}.  No changes will be made
   * to the {@link Cache.Entry}.
   * <p/>
   * Implementations may execute {@link EntryProcessor}s in situ, thus avoiding
   * locking, round-trips and expensive network transfers.
   * <p/>
   * {@link Cache.Entry} access, via a call to
   * {@link javax.cache.Cache.MutableEntry#getValue()}, will behave as if
   * {@link Cache#get(Object)} was called for the key.  This includes updating
   * necessary statistics, consulting the configured
   * {@link javax.cache.expiry.ExpiryPolicy} and loading from a configured
   * {@link javax.cache.integration.CacheLoader}.
   * <p/>
   * {@link Cache.Entry} mutation, via a call to
   * {@link javax.cache.Cache.MutableEntry#setValue(Object)}, will behave
   * as if {@link Cache#put(Object, Object)} was called for the key.  This
   * includes updating necessary statistics, consulting the configured
   * {@link javax.cache.expiry.ExpiryPolicy}, notifying
   * {@link javax.cache.event.CacheEntryListener}s and a writing to a configured
   * {@link javax.cache.integration.CacheWriter}.
   * <p/>
   * {@link Cache.Entry} removal, via a call to
   * {@link javax.cache.Cache.MutableEntry#remove()}, will behave
   * as if {@link Cache#remove(Object)} was called for the key.  This
   * includes updating necessary statistics, notifying
   * {@link javax.cache.event.CacheEntryListener}s and causing a delete on a
   * configured {@link javax.cache.integration.CacheWriter}.
   * <p/>
   * As implementations may choose to execute {@link EntryProcessor}s remotely,
   * {@link EntryProcessor}s, together with specified parameters and return
   * values, may be required to implement {@link java.io.Serializable}.
   *
   * @param <K> the type of keys maintained by this cache
   * @param <V> the type of cached values
   */
  public interface EntryProcessor<K, V, T> {

    /**
     * Process an entry.
     *
     * @param entry     the entry
     * @param arguments a number of arguments to the process.
     * @return the result of the processing, if any, which is user defined.
     */
    T process(Cache.MutableEntry<K, V> entry, Object... arguments);
  }
}
