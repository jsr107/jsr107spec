/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.cache.event;

import javax.cache.Cache;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import java.util.EventObject;

/**
 * A Cache entry event base class.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Greg Luck
 * @since 1.0
 */
public abstract class CacheEntryEvent<K, V> extends EventObject
    implements Cache.Entry<K, V> {

  private EventType eventType;

  /**
   * Constructs a cache entry event from a given cache as source
   *
   * @param source the cache that originated the event
   * @param eventType the event type for this event
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
   * Returns the value stored in the cache when this entry was created or updated.
   * <p>
   * The value will be available
   * for {@link CacheEntryCreatedListener} and {@link CacheEntryUpdatedListener}.
   * Returns the same value as {@link #getOldValue()} for
   * {@link CacheEntryExpiredListener} and {@link CacheEntryRemovedListener}.
   * Cache clients that need to maintain compatibility with JSR107 version 1.0
   * cache implementations, need to use this method for retrieving the expired
   * or removed value. When using cache implementations compatible with JSR107
   * version 1.1, clients should prefer the method {@link #getOldValue()}.
   *
   * @return the value corresponding to this entry
   * @see #getOldValue()
   */
  @Override
  public abstract V getValue();

  /**
   * Returns the previous value that existed for entry in the cache before
   * modification or removal.
   *
   * The old value will be available
   * for {@link CacheEntryUpdatedListener}, {@link CacheEntryExpiredListener}
   * and {@link CacheEntryRemovedListener}
   * if {@link CacheEntryListenerConfiguration#isOldValueRequired()} is true.
   * The old value may be available for {@link CacheEntryUpdatedListener},
   * {@link CacheEntryExpiredListener} and {@link CacheEntryRemovedListener}
   * if {@link CacheEntryListenerConfiguration#isOldValueRequired()} is false.
   *
   * @return the previous value or <code>null</code> if there was no previous
   * value or the previous value is not available
   */
  public abstract V getOldValue();

  /**
   * Whether the old value is available. The old value will be available
   * for {@link CacheEntryUpdatedListener}, {@link CacheEntryExpiredListener}
   * and {@link CacheEntryRemovedListener}
   * if {@link CacheEntryListenerConfiguration#isOldValueRequired()} is true.
   * The old value <b>may</b> be available for {@link CacheEntryUpdatedListener},
   * {@link CacheEntryExpiredListener} and {@link CacheEntryRemovedListener}
   * if {@link CacheEntryListenerConfiguration#isOldValueRequired()} is false.
   *
   * @return true if the old value is definitely available
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
