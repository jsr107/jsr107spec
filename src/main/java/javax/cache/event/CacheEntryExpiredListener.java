/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;


/**
 * Invoked if a cache entry or entries are evicted due to expiration.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryExpiredListener<K, V> extends CacheEntryListener<K, V> {

  /**
   * Called after one or more entries have been expired by the cache. This is not
   * necessarily when an entry is expired, but when the cache detects the expiry.
   *
   * @param events The entries just removed.
   * @throws CacheEntryListenerException if there is problem executing the listener
   */
  void onExpired(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
      throws CacheEntryListenerException;

}
