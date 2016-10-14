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
package javax.cache.configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.io.Serializable;

/**
 * A basic read-only representation of a {@link Cache} configuration.
 * <p>
 * The properties provided by instances of this interface are used by
 * {@link CacheManager}s to configure {@link Cache}s.
 * <p>
 * Implementations of this interface must override {@link Object#hashCode()} and
 * {@link Object#equals(Object)} as {@link Configuration}s are often compared at
 * runtime.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Brian Oliver
 * @since 1.0
 */
public interface Configuration<K, V> extends Serializable {

  /**
   * Determines the required type of keys for {@link Cache}s configured
   * with this {@link Configuration}.
   *
   * @return the key type or <code>Object.class</code> if the type is undefined
   */
  Class<K> getKeyType();

  /**
   * Determines the required type of values for {@link Cache}s configured
   * with this {@link Configuration}.
   *
   * @return the value type or <code>Object.class</code> if the type is undefined
   */
  Class<V> getValueType();

  /**
   * Whether storeByValue (true) or storeByReference (false).
   * When true, both keys and values are stored by value.
   * <p>
   * When false, both keys and values are stored by reference.
   * Caches stored by reference are capable of mutation by any threads holding
   * the reference. The effects are:
   * <ul>
   * <li>if the key is mutated, then the key may not be retrievable or
   * removable</li>
   * <li>if the value is mutated, then all threads in the JVM can potentially
   * observe those mutations, subject to the normal Java Memory Model rules.</li>
   * </ul>
   * Storage by reference only applies to the local heap. If an entry is moved off
   * heap it will need to be transformed into a representation. Any mutations that
   * occur after transformation may not be reflected in the cache.
   * <p>
   * When a cache is storeByValue, any mutation to the key or value does not
   * affect the key of value stored in the cache.
   * <p>
   * The default value is <code>true</code>.
   *
   * @return true if the cache is store by value
   */
  boolean isStoreByValue();
}
