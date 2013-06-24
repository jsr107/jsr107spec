/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
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
 * A Cache provides storage of data for later fast retrieval.
 * <p/>
 * This Cache interface is inspired by the {@link java.util.concurrent.ConcurrentMap}
 * API with some modifications to permit high performance in distributed
 * implementations.
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
 * <li>configuration</li>
 * </ul>
 * Though not visible in the Cache interface caches may be optionally transactional.
 * <p/>
 * User programs may make use of caching annotations to interact with a cache.
 * <p/>
 * A simple example of how to use a cache is:
 * <pre>
 * String cacheName = "sampleCache";
 * CachingProvider provider = Caching.getCachingProvider();
 * CacheManager manager = provider.getCacheManager();
 * Cache&lt;Integer, Date&gt; cache = manager.getCache(cacheName);
 * if (cache == null) {
 *   Configuration config = new MutableConfiguration()
 *   cache = manager.configureCache(cacheName, config);
 * }
 * Date value1 = new Date();
 * Integer key = 1;
 * cache.put(key, value1);
 * Date value2 = cache.get(key);
 * </pre>
 * <p/>
 * <h1>Consistency</h1>
 * <h2>Default Consistency</h2>
 * In default consistency, consistency is described as if there exists a locking
 * mechanism on each key.
 * <p/>
 * If a cache operation gets an exclusive read and write lock on a key, then all
 * subsequent operations on that key will block until that lock is released. The
 * consequences are that operations performed by a thread happen-before read or
 * mutation operations performed by another thread, including threads in different
 * Java Virtual Machines.
 * <h2>Transactional Consistency</h2>
 * Where a cache is transactional it will take on the semantics of the Transaction
 * Isolation Level configured.
 * <h2>Further Consistency Modes</h2>
 * An implementation may support additional consistency models.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @since 1.0
 */
public interface Cache<K, V> extends Iterable<Cache.Entry<K,
    V>>, Closeable {
  /**
   * Gets an entry from the cache.
   * <p/>
   * If the cache is configured read-through, and get would return null because the entry
   * is missing from the cache, the Cache's {@link javax.cache.integration.CacheLoader} is called which will attempt
   * to load the entry.
   * <p/>
   * <h1>Effects:</h1>
   * <ul>
   * <li>Expiry - updates expiry time based on the Configuration ExpiryPolicy.</li>
   * <li>Read-Through - will use the {@link javax.cache.integration.CacheLoader} if enabled and key not present in cache</li>
   * </ul>
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
   * @see java.util.Map#get(Object)
   */
  V get(K key);

  /**
   * The getAll method will return, from the cache, a {@link Map} of the objects
   * associated with the Collection of keys in argument "keys".
   * <p/>
   * If the cache is configured read-through, and a get would return null because an entry
   * is missing from the cache, the Cache's {@link javax.cache.integration.CacheLoader} is called which will attempt
   * to load the entry. This is done for each key in the collection for which this is the case.
   * If an entry cannot be loaded for a given key, the key will not be present in the returned Map.
   * <p/>
   *
   * @param keys The keys whose associated values are to be returned.
   * @return A map of entries that were found for the given keys. Keys not found in the cache are not in the returned map.
   * @throws NullPointerException  if keys is null or if keys contains a null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem fetching the values
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and any key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   */
  Map<K, V> getAll(Set<? extends K> keys);

  /**
   * Returns <tt>true</tt> if this cache contains a mapping for the specified
   * key.  More formally, returns <tt>true</tt> if and only if
   * this cache contains a mapping for a key <tt>k</tt> such that
   * <tt>key.equals(k)</tt>.  (There can be at most one such mapping.)
   * <p/>
   *
   * @param key key whose presence in this cache is to be tested.
   * @return <tt>true</tt> if this map contains a mapping for the specified key
   * @throws NullPointerException  if key is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        it there is a problem checking the mapping
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   * @see java.util.Map#containsKey(Object)
   */
  boolean containsKey(K key);

  /**
   * This method provides a means to "pre-load" objects into the cache. It will,
   * asynchronously, load the specified objects into the cache using the associated
   * cache loader for the given keys.
   * <p/>
   * If an entry for a key already exists in the Cache, a value will be loaded
   * if and only if <code>replaceExistingValues</code> is true.   If no loader is
   * configured for the cache, no objects will be loaded.  If a problem is
   * encountered during the retrieving or loading of the objects,
   * an exception is provided to the {@link CompletionListener}.  Once the
   * operation has completed, the specified CompletionListener is notified.
   * <p/>
   * Implementations may choose to load multiple keys from the provided
   * iterable in parallel.  Iteration must not occur in parallel, thus
   * allow for non-thread-safe Iterables, but loading may.
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
   *                               which has been configured for the {@link Cache}
   */
  void loadAll(Iterable<? extends K> keys, boolean replaceExistingValues,
               CompletionListener completionListener);

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
   * @return the value associated with the key at the start of the operation or null if none was associated
   * @throws NullPointerException  if key is null or if value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible with
   *                               those which have been configured for the
   *                               {@link Cache}
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
   * <p/>
   * The order in which the individual puts occur is undefined.
   * <p/>
   * The behavior of this operation is undefined if entries in the cache
   * corresponding to entries in the map are modified or removed while this
   * operation is in progress. or if map is modified while the operation is in
   * progress.
   * <p/>
   * In Default Consistency mode, individual puts are done atomically but not
   * the entire putAll.
   *
   * @param map mappings to be stored in this cache
   * @throws NullPointerException  if map is null or if map contains null keys or values.
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put.
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and any of the key or value types are
   *                               incompatible with those that have been
   *                               configured for the {@link Cache}
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
   * @throws NullPointerException  if key is null or value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
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
   * @throws NullPointerException  if key is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem doing the put
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   * @see java.util.Map#remove(Object)
   */
  boolean remove(K key);

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
   * @see java.util.Map#remove(Object)
   */
  boolean remove(K key, V oldValue);

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
   * @throws NullPointerException  if the specified key or value is null.
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the remove
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key type is incompatible with that
   *                               which has been configured for the {@link Cache}
   * @see java.util.Map#remove(Object)
   */
  V getAndRemove(K key);

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
   * @throws NullPointerException  if key is null or if the values are null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the replace
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those which have been configured for the
   *                               {@link Cache}
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
   * @throws NullPointerException  if key is null or if value is null
   * @throws IllegalStateException if the cache is {@link #isClosed()}
   * @throws CacheException        if there is a problem during the replace
   * @throws ClassCastException    if the implementation supports and is
   *                               configured to perform runtime-type-checking,
   *                               and the key or value types are incompatible
   *                               with those that have been configured for the
   *                               {@link Cache}
   * @see #getAndReplace(Object, Object)
   * @see java.util.concurrent.ConcurrentMap#replace(Object, Object)
   */
  boolean replace(K key, V value);

  /**
   * Atomically replaces the value for a given key if and only if there is a
   * value currently mapped by the key.
   * <p/>
   * This is equivalent to
   * <pre>
   *   if (cache.containsKey(key)) {
   *       V oldValue = cache.get(key);
   *       cache.put(key, value);
   *       return oldValue;
   *   } else {
   *       return null;
   *   }</pre>
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
   * <p/>
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
   * Returns an immutable Configuration object.
   *
   * @return the {@link javax.cache.configuration.Configuration} of this cache
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
   * Gets the CacheManager managing this cache.
   * <p/>
   * A cache can be in only one CacheManager.
   *
   * @return the manager
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
   * {@link javax.cache.event.CacheEntryListener}s or {@link javax.cache.expiry.ExpiryPolicy}
   * configured to the {@link Cache} that implement {@link java.io.Closeable}
   * will be closed.</ul>
   * <uL>all resources allocated to the {@link Cache} by the {@link CacheManager}
   * will be freed.</ul>
   * <p/>
   * A closed {@link Cache} instance cannot be reused.  Only new instances may
   * be acquired via a {@link CacheManager} or as prescribed by an implementation.
   */
  void close();

  /**
   * Determines whether this Cache instance has been closed. A Cache is considered
   * closed if;
   * <ol>
   * <li>the {@link #close()} method has been called</li>
   * <li>the associated {@link #getCacheManager()} has been closed, or</li>
   * <li>the Cache has been removed from the associated {@link #getCacheManager()}</li>
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
   * Provides a standard way to access the underlying concrete caching implementation to provide access
   * to further, proprietary features.
   * <p/>
   * If the provider's implementation does not support the specified class, the {@link IllegalArgumentException} is thrown.
   *
   * @param clazz the proprietary class or interface of the underlying concrete cache. It is this type which is returned.
   * @return an instance of the underlying concrete cache
   * @throws IllegalArgumentException if the caching provider doesn't support the specified class.
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
   * {@link java.util.Iterator#next()} may return null if the entry is no longer present.
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
     * @throws IllegalArgumentException if the caching provider doesn't support the specified class.
     */
    <T> T unwrap(Class<T> clazz);
  }

  /**
   * An accessor and mutator to the underlying Cache
   *
   * @param <K>
   * @param <V>
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
     * <p/>
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
