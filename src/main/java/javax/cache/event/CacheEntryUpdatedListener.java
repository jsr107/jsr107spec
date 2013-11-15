/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * Invoked if an existing cache entry is updated, or if a batch call is made,
 * after the entries are updated.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @see CacheEntryCreatedListener
 * @since 1.0
 */
public interface CacheEntryUpdatedListener<K, V> extends CacheEntryListener<K, V> {

  /**
   * Called after one or more entries have been updated.
   *
   * @param events The entries just updated.
   * @throws CacheEntryListenerException if there is problem executing the listener
   */
  void onUpdated(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
      throws CacheEntryListenerException;
}
