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
