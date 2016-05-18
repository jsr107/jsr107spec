/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * Invoked if a cache entry is removed, or if a batch call is made, after the
 * entries are removed.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryRemovedListener<K, V> extends CacheEntryListener<K, V> {

  /**
   * Called after one or more entries have been removed. If no entry existed for
   * a key an event is not raised for it.
   *
   * @param events The entries just removed.
   * @throws CacheEntryListenerException if there is problem executing the listener
   */
  void onRemoved(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
      throws CacheEntryListenerException;


}
