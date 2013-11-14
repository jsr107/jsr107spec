/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.configuration;

import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import java.io.Serializable;

/**
 * Defines the configuration requirements for a
 * {@link CacheEntryListener} and a {@link Factory} for its
 * creation.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author Brian Oliver
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryListenerConfiguration<K, V> extends Serializable {
  /**
   * Obtains the {@link Factory} for the
   * {@link CacheEntryListener}.
   *
   * @return the {@link Factory} for the
   *         {@link CacheEntryListener}
   */
  Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory();

  /**
   * Determines if the old value should be provided to the
   * {@link CacheEntryListener}.
   *
   * @return <code>true</code> if the old value is required by the
   *         {@link CacheEntryListener}
   */
  boolean isOldValueRequired();

  /**
   * Obtains the {@link Factory} for the {@link CacheEntryEventFilter} that should be
   * applied prior to notifying the {@link CacheEntryListener}.
   * <p>
   * When <code>null</code> no filtering is applied and all appropriate events
   * are notified.
   *
   * @return the {@link Factory} for the
   *         {@link CacheEntryEventFilter} or <code>null</code>
   *         if no filtering is required
   */
  Factory<CacheEntryEventFilter<? super K, ? super V>>
  getCacheEntryEventFilterFactory();

  /**
   * Determines if the thread that caused an event to be created should be
   * blocked (not return from the operation causing the event) until the
   * {@link CacheEntryListener} has been notified.
   *
   * @return <code>true</code> if the thread that created the event should block
   */
  boolean isSynchronous();
}
