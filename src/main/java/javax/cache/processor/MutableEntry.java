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
package javax.cache.processor;

import javax.cache.Cache;
import javax.cache.integration.CacheLoader;

/**
 * A mutable representation of a {@link javax.cache.Cache.Entry}.
 * <p/>
 * Mutable entries are used by {@link EntryProcessor}s to mutate
 * {@link Cache.Entry}s in place, atomically.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 *
 * @author Greg Luck
 * @since 1.0
 * @see EntryProcessor
 */
public interface MutableEntry<K, V> extends Cache.Entry<K, V> {

  /**
   * Checks for the existence of the entry in the cache
   *
   * @return true if the entry exists
   */
  boolean exists();

  /**
   * Removes the entry from the Cache.
   * <p>
   * This has the same semantics as calling {@link Cache#remove}.
   */
  void remove();

  /**
   * Returns the value stored in the cache.
   * <p/>
   * If the cache is configured to use read-through, and this method
   * would return null because the entry is missing from the cache,
   * the Cache's {@link CacheLoader} is called in an attempt to load
   * the entry.
   *
   * @return the value corresponding to this entry
   */
  V getValue();

  /**
   * Sets or replaces the value associated with the key.
   * <p>
   * If {@link #exists} is false and setValue is called
   * then a mapping is added to the cache visible once the EntryProcessor
   * completes. Moreover a second invocation of {@link #exists()}
   * will return true.
   *
   * @param value the value to update the entry with
   * @throws ClassCastException if the implementation supports and is
   *                            configured to perform runtime-type-checking,
   *                            and value type is incompatible with that
   *                            which has been configured for the
   *                            {@link Cache}
   */
  void setValue(V value);
}
