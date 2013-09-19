/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.expiry;

/**
 * Defines functions to determine when cache entries will expire based on
 * creation, access and modification operations.
 * <p/>
 * Each of the functions return a new {@link Duration}  which specifies the
 * amount of time that must pass before a cache entry is considered expired.
 * {@link Duration} has constants defined for useful durations.
 *
 * @author Brian Oliver
 * @author Greg Luck
 * @see Duration
 */
public interface ExpiryPolicy {

  /**
   * Gets the {@link Duration} before a newly created Cache.Entry is considered
   * expired.
   * <p/>
   * This method is called by a caching implementation after a Cache.Entry is
   * created, but before a Cache.Entry is added to a cache, to determine the
   * {@link Duration} before a entry expires.  If a {@link Duration#ZERO}
   * is returned the new Cache.Entry is considered to be already expired and
   * will not be added to the Cache.
   * <p/>
   * Should an exception occur while determining the Duration, an implementation
   * specific default {@link Duration} will be used.
   *
   * @return the new {@link Duration} before a created entry expires
   */
  Duration getExpiryForCreation();

  /**
   * Gets the {@link Duration} before an accessed Cache.Entry is
   * considered expired.
   * <p/>
   * This method is called by a caching implementation after a Cache.Entry is
   * accessed to determine the {@link Duration} before an entry expires.  If a
   * {@link Duration#ZERO} is returned a Cache.Entry will be
   * considered immediately expired.  Returning <code>null</code> will result
   * in no change to the previously understood expiry {@link Duration}.
   * <p/>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @return the new {@link Duration} before an accessed entry expires
   */
  Duration  getExpiryForAccess();

  /**
   * Gets the {@link Duration} before an updated Cache.Entry is considered
   * expired.
   * <p/>
   * This method is called by the caching implementation after a Cache.Entry is
   * updated to determine the {@link Duration} before the updated entry expires.
   * If a {@link Duration#ZERO} is returned a Cache.Entry is considered
   * immediately expired.  Returning <code>null</code> will result in no change
   * to the previously understood expiry {@link Duration}.
   * <p/>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @return the new {@link Duration} before an updated entry expires
   */
  Duration getExpiryForUpdate();
}
