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
