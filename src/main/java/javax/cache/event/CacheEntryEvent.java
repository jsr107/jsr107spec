/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

import javax.cache.Cache;
import java.util.EventObject;

/**
 * A Cache event base class
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * @since 1.0
 */
public abstract class CacheEntryEvent<K, V> extends EventObject implements Cache.Entry<K, V> {

  private EventType eventType;

  /**
   * Constructs a cache entry event from a given cache as source
   *
   * @param source the cache that originated the event
   */
  public CacheEntryEvent(Cache source, EventType eventType) {
    super(source);
    this.eventType = eventType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Cache getSource() {
    return (Cache) super.getSource();
  }

  /**
   * Returns the previous value, that of which existed prior to the
   * modification of the Entry value.
   *
   * @return the previous value or <code>null</code> if there was no
   *         previous value
   */
  public abstract V getOldValue();

  /**
   * Whether the old value is available.
   *
   * @return true if the old value is populated
   */
  public abstract boolean isOldValueAvailable();

  /**
   * Gets the event type of this event
   *
   * @return the event type.
   */
  public final EventType getEventType() {
    return eventType;
  }
}
