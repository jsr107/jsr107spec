/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * Invoked after a cache entry is created, or if a batch call is made, after the
 * entries are created.
 * <p>
 * If an entry for the key existed prior to the operation it is not invoked,
 * instead {@link CacheEntryUpdatedListener} is invoked.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @see CacheEntryUpdatedListener
 * @since 1.0
 */
public interface CacheEntryCreatedListener<K, V> extends CacheEntryListener<K, V> {

  /**
   * Called after one or more entries have been created.
   *
   * @param events The entries just created.
   * @throws CacheEntryListenerException if there is problem executing the listener
   */
  void onCreated(Iterable<CacheEntryEvent<? extends K, ? extends V>> events)
      throws CacheEntryListenerException;

}
