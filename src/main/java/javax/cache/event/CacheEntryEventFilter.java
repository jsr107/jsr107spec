/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * A function that may be used to check {@link CacheEntryEvent}s prior to being
 * dispatched to {@link CacheEntryListener}s.
 * <p>
 * A filter must not create side effects.
 *
 * @param <K> the type of key
 * @param <V> the type of value
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
  boolean evaluate(CacheEntryEvent<? extends K, ? extends V> event)
      throws CacheEntryListenerException;
}
