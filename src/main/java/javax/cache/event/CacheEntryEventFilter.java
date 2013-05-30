/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * A filter which may be used to check {@link CacheEntryEvent}s prior to being
 * dispatched to {@link CacheEntryListener}s.
 *
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Greg Luck
 * @author Brian Oliver
 * @since 1.0
 */
public interface CacheEntryEventFilter<K, V> {

  /**
   * Evaluates specified {@link CacheEntryEvent}.
   *
   * @param event the event that occurred
   * @return true if the evaluation passes, otherwise false.
   *         The effect of returning true is that listener will be invoked
   * @throws CacheEntryListenerException if there is problem executing the listener
   */
  boolean evaluate(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException;
}
