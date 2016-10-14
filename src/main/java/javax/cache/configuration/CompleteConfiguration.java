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

import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import java.io.Serializable;

/**
 * A read-only representation of the complete JCache {@link javax.cache.Cache}
 * configuration.
 * <p>
 * The properties provided by instances of this interface are used by
 * {@link javax.cache.CacheManager}s to configure {@link javax.cache.Cache}s.
 * <p>
 * Implementations of this interface must override {@link Object#hashCode()} and
 * {@link Object#equals(Object)} as
 * {@link javax.cache.configuration.CompleteConfiguration}s are often compared at
 * runtime.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @since 1.0
 */
public interface CompleteConfiguration<K, V> extends Configuration<K, V>,
    Serializable {

  /**
   * Determines if a {@link javax.cache.Cache} should operate in read-through mode.
   * <p>
   * When in "read-through" mode, cache misses that occur due to cache entries
   * not existing as a result of performing a "get" will appropriately
   * cause the configured {@link javax.cache.integration.CacheLoader} to be
   * invoked.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link javax.cache.Cache} is in
   * "read-through" mode.
   * @see #getCacheLoaderFactory()
   */
  boolean isReadThrough();

  /**
   * Determines if a {@link javax.cache.Cache} should operate in write-through
   * mode.
   * <p>
   * When in "write-through" mode, cache updates that occur as a result of
   * performing "put" operations called via one of
   * {@link javax.cache.Cache#put(Object, Object)},
   * {@link javax.cache.Cache#getAndRemove(Object)},
   * {@link javax.cache.Cache#removeAll()},
   * {@link javax.cache.Cache#getAndPut(Object, Object)}
   * {@link javax.cache.Cache#getAndRemove(Object)},
   * {@link javax.cache.Cache#getAndReplace(Object,
   * Object)}, {@link javax.cache.Cache#invoke(Object,
   * javax.cache.processor.EntryProcessor,
   * Object...)}, {@link javax.cache.Cache#invokeAll(java.util.Set,
   * javax.cache.processor.EntryProcessor, Object...)} will appropriately cause
   * the configured {@link javax.cache.integration.CacheWriter} to be invoked.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link javax.cache.Cache} is in
   *        "write-through" mode.
   * @see #getCacheWriterFactory()
   */
  boolean isWriteThrough();

  /**
   * Checks whether statistics collection is enabled in this cache.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return true if statistics collection is enabled
   */
  boolean isStatisticsEnabled();

  /**
   * Checks whether management is enabled on this cache.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return true if management is enabled
   */
  boolean isManagementEnabled();

  /**
   * Obtains the {@link javax.cache.configuration.CacheEntryListenerConfiguration}s
   * for {@link javax.cache.event.CacheEntryListener}s to be configured on a
   * {@link javax.cache.Cache}.
   *
   * @return an {@link Iterable} over the
   * {@link javax.cache.configuration.CacheEntryListenerConfiguration}s
   */
  Iterable<CacheEntryListenerConfiguration<K,
      V>> getCacheEntryListenerConfigurations();

  /**
   * Gets the {@link javax.cache.configuration.Factory} for the
   * {@link javax.cache.integration.CacheLoader}, if any.
   * <p>
   * A CacheLoader should be configured for "Read Through" caches to load values
   * when a cache miss occurs using either the
   * {@link javax.cache.Cache#get(Object)} and/or
   * {@link javax.cache.Cache#getAll(java.util.Set)} methods.
   * <p>
   * The default value is <code>null</code>.
   *
   * @return the {@link javax.cache.configuration.Factory} for the
   * {@link javax.cache.integration.CacheLoader} or null if none has been set.
   */
  Factory<CacheLoader<K, V>> getCacheLoaderFactory();

  /**
   * Gets the {@link javax.cache.configuration.Factory} for the
   * {@link javax.cache.integration.CacheWriter}, if any.
   * <p>
   * The default value is <code>null</code>.
   *
   * @return the {@link javax.cache.configuration.Factory} for the
   * {@link javax.cache.integration.CacheWriter} or null if none has been set.
   */
  Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory();

  /**
   * Gets the {@link javax.cache.configuration.Factory} for the
   * {@link javax.cache.expiry.ExpiryPolicy} to be used for caches.
   * <p>
   * The default value is a {@link javax.cache.configuration.Factory} that will
   * produce a {@link javax.cache.expiry.EternalExpiryPolicy} instance.
   *
   * @return the {@link javax.cache.configuration.Factory} for
   * {@link javax.cache.expiry.ExpiryPolicy} (must not be <code>null</code>)
   */
  Factory<ExpiryPolicy> getExpiryPolicyFactory();

}
