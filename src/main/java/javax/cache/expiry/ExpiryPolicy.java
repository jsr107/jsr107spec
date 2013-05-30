/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.expiry;

import javax.cache.Cache.Entry;

/**
 * Defines functions to determine when cache entries will expire based on
 * creation, access and modification operations.
 * <p/>
 * Each of the functions return a new {@link Duration} that of which specifies the
 * amount of time that must pass before a cache entry is considered expired.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author Brian Oliver
 * @author Greg Luck
 */
public interface ExpiryPolicy<K, V> {

  /**
   * Gets the duration before the newly Cache.Entry is considered expired.
   * <p/>
   * This method is called by the caching implementation after a Cache.Entry is
   * created, but before the said entry is added to a cache, to determine the
   * {@link Duration} before the said entry expires.  If a {@link Duration#ZERO}
   * is returned the Cache.Entry is considered to be already expired and will
   * not be added to the Cache.
   * <p/>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @param entry the cache entry that was created
   * @return the new duration until the entry expires
   */
  Duration getExpiryForCreatedEntry(Entry<? extends K, ? extends V> entry);

  /**
   * Gets the duration before the accessed Cache.Entry is considered expired.
   * <p/>
   * This method is called by the caching implementation after a Cache.Entry is
   * accessed to determine the {@link Duration} before the said entry expires in
   * the future.  If a {@link Duration#ZERO} is returned the Cache.Entry will be
   * considered expired for future access.  Returning <code>null</code> will
   * result in no change to the previously understood expiry {@link Duration}.
   * <p/>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @param entry the cache entry that was accessed
   * @return the new duration until the entry expires
   */
  Duration getExpiryForAccessedEntry(Entry<? extends K, ? extends V> entry);

  /**
   * Gets the duration before the modified Cache.Entry is considered expired.
   * <p/>
   * This method is called by the caching implementation after a Cache.Entry is
   * modified to determine the {@link Duration} before the updated entry expires.
   * If a {@link Duration#ZERO} is returned the Cache.Entry is considered already
   * expired.  Returning <code>null</code> will result in no change to the
   * previously understood expiry {@link Duration}.
   * <p/>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @param entry the cache entry that was modified
   * @return the new duration until the entry expires
   */
  Duration getExpiryForModifiedEntry(Entry<? extends K, ? extends V> entry);

}
