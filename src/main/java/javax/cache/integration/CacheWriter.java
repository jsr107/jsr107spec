/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.integration;


import javax.cache.Cache;
import java.util.Collection;

/**
 * A CacheWriter is used for write-through to an external resource.
 * <p/>
 * Under Default Consistency, the non-batch writer methods are atomic with respect
 * to the corresponding cache operation.
 * <p/>
 * For batch methods under Default Consistency, the entire cache operation
 * is not required to be atomic in {@link Cache} and is therefore not required to
 * be atomic in the writer. As individual writer operations can fail, cache
 * operations are not required to occur until after the writer batch method has
 * returned or, in the case of partial success, thrown an exception. In the case
 * of
 * partial success, the collection of entries must contain only those entries
 * which failed.
 * <p/>
 * The entry passed into {@link #write(Cache.Entry)} is independent
 * of the cache mapping for that key, meaning that if the value changes in the
 * cache or is removed it does not change the said entry.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Greg Luck
 * @author Brian Oliver
 * @see CacheLoader
 * @since 1.0
 */
public interface CacheWriter<K, V> {


  /**
   * Write the specified value under the specified key to the external resource.
   * <p/>
   * This method is intended to support both key/value creation and value update
   * for a specific key.
   *
   * @param entry the entry to be written
   * @throws CacheWriterException if the write fails. If thrown the
   *                              cache mutation will not occur.
   */
  void write(Cache.Entry<? extends K, ? extends V> entry) throws CacheWriterException;

  /**
   * Write the specified entries to the external resource. This method is intended
   * to support both insert and update.
   * <p/>
   * The order in which individual writes occur is undefined, as
   * {@link Cache#putAll(java.util.Map)} also has undefined ordering.
   * <p/>
   * If this operation fails (by throwing an exception) after a partial success,
   * the writer must remove any successfully written entries from the entries
   * collection so that the caching implementation knows what succeeded and can
   * mutate the cache.
   *
   * @param entries a mutable collection to write. Upon invocation, it contains
   *                the entries to write for write-through. Upon return the
   *                collection must only contain entries that were not
   *                successfully written. (see partial success above)
   * @throws CacheWriterException if one or more of the writes fail. If
   *                              thrown cache mutations will occur for
   *                              entries which succeeded.
   */
  void writeAll(Collection<Cache.Entry<? extends K, ? extends V>> entries) throws
      CacheWriterException;


  /**
   * Delete the cache entry from the external resource.
   * <p/>
   * Expiry of a cache entry is not a delete hence will not cause this method to
   * be invoked.
   * <p/>
   * This method is invoked even if no mapping for the key exists.
   *
   * @param key the key that is used for the delete operation
   * @throws CacheWriterException if delete fails. If thrown the cache delete will
   *                              not occur.
   */
  void delete(Object key) throws CacheWriterException;


  /**
   * Remove data and keys from the external resource for the given collection of
   * keys, if present.
   * <p/>
   * The order in which individual deletes occur is undefined, as
   * {@link Cache#removeAll(java.util.Set)} also has undefined ordering.
   * <p/>
   * If this operation fails (by throwing an exception) after a partial success,
   * the writer must remove any successfully written entries from the entries
   * collection so that the caching implementation knows what succeeded and can
   * mutate the cache.
   * <p/>
   * Expiry of a cache entry is not a delete hence will not cause this method to
   * be invoked.
   * <p/>
   * This method is only invoked for keys that exist in the cache.
   *
   * @param keys a mutable collection of keys for entries to delete. Upon
   *             invocation, it contains the keys to delete for write-through.
   *             Upon return the collection must only contain the keys that were
   *             not successfully deleted. (see partial success above)
   * @throws CacheWriterException if one or more deletes fail. If thrown
   *                              cache deletes will occur for entries which
   *                              succeeded.
   */
  void deleteAll(Collection<?> keys) throws CacheWriterException;
}
