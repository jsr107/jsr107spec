/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.configuration;

import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.io.Serializable;
import java.util.List;

/**
 * A read-only representation of a {@link javax.cache.Cache} configuration.
 * <p/>
 * The properties provided by instances of this interface are used by
 * {@link javax.cache.CacheManager}s to configure {@link javax.cache.Cache}s.
 * <p/>
 * Implementations of this interface must override {@link Object#hashCode()} and
 * {@link Object#equals(Object)} as {@link Configuration}s are often compared at runtime.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @since 1.0
 */
public interface Configuration<K, V> extends Serializable {

  /**
   * Determines the required type of keys for {@link javax.cache.Cache}s configured with
   * this {@link Configuration}.
   *
   * @return the key type or <code>null</code> if the type is undefined
   */
  Class<K> getKeyType();

  /**
   * Determines the required type of values for {@link javax.cache.Cache}s configured with
   * this {@link Configuration}.
   *
   * @return the value type or <code>null</code> if the type is undefined
   */
  Class<V> getValueType();

  /**
   * Determines if a {@link javax.cache.Cache} should operate in read-through mode.
   * <p/>
   * When in "read-through" mode, cache misses that occur due to cache entries
   * not existing as a result of performing a "get" will appropriately
   * cause
   * the configured {@link javax.cache.integration.CacheLoader} to be invoked.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link javax.cache.Cache} is in "read-through" mode.
   * @see #getCacheLoaderFactory()
   */
  boolean isReadThrough();

  /**
   * Determines if a {@link javax.cache.Cache} should operate in write-through mode.
   * <p/>
   * When in "write-through" mode, cache updates that occur as a result of performing
   * "put" operations called via one of {@link javax.cache.Cache#put(Object, Object)}, {@link javax.cache.Cache#getAndRemove(Object)},
   * {@link javax.cache.Cache#removeAll()}, {@link javax.cache.Cache#getAndPut(Object, Object)}
   * {@link javax.cache.Cache#getAndRemove(Object)}, {@link javax.cache.Cache#getAndReplace(Object, Object)},
   * {@link javax.cache.Cache#invoke(Object, javax.cache.processor.EntryProcessor, Object...)},
   * {@link javax.cache.Cache#invokeAll(java.util.Set, javax.cache.processor.EntryProcessor, Object...)}
   * will appropriately cause the configured {@link javax.cache.integration.CacheWriter} to be invoked.
   * <p/>
   *
   *
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link javax.cache.Cache} is in "write-through" mode.
   * @see #getCacheWriterFactory()
   */
  boolean isWriteThrough();

  /**
   * Whether storeByValue (true) or storeByReference (false).
   * When true, both keys and values are stored by value.
   * <p/>
   * When false, both keys and values are stored by reference.
   * Caches stored by reference are capable of mutation by any threads holding
   * the reference. The effects are:
   * <ul>
   * <li>if the key is mutated, then the key may not be retrievable or removable</li>
   * <li>if the value is mutated, then all threads in the JVM can potentially observe those mutations,
   * subject to the normal Java Memory Model rules.</li>
   * </ul>
   * Storage by reference only applies to the local heap. If an entry is moved off heap it will
   * need to be transformed into a representation. Any mutations that occur after transformation
   * may not be reflected in the cache.
   * <p/>
   * Additionally Store-By-Reference is only supported for non-transactional caches.  Attempts
   * to configure a cache using both transactions and Store-By-Reference will result in an
   * in a CacheException.
   * <p/>
   * When a cache is storeByValue, any mutation to the key or value does not affect the key of value
   * stored in the cache.
   * <p/>
   * The default value is <code>true</code>.
   *
   * @return true if the cache is store by value
   */
  boolean isStoreByValue();

  /**
   * Checks whether statistics collection is enabled in this cache.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return true if statistics collection is enabled
   */
  boolean isStatisticsEnabled();

  /**
   * Checks whether management is enabled on this cache.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return true if management is enabled
   */
  boolean isManagementEnabled();

  /**
   * Checks whether transactions are enabled for this cache.
   * <p/>
   * Note that in a transactional cache, entries being mutated within a
   * transaction cannot be expired by the cache.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return true if transaction are enabled
   */
  boolean isTransactionsEnabled();

  /**
   * Gets the transaction isolation level.
   * <p/>
   * The default value is {@link IsolationLevel#NONE}.
   *
   * @return the isolation level.
   */
  IsolationLevel getTransactionIsolationLevel();

  /**
   * Gets the transaction mode.
   * <p/>
   * The default value is {@link Mode#NONE}.
   *
   * @return the mode of the cache.
   */
  Mode getTransactionMode();

  /**
   * Obtains the {@link CacheEntryListenerConfiguration}s for
   * {@link javax.cache.event.CacheEntryListener}s to be configured on a
   * {@link javax.cache.Cache}.
   *
   * @return an {@link Iterable} over the {@link CacheEntryListenerConfiguration}s
   */
  List<CacheEntryListenerConfiguration<K,
          V>> getCacheEntryListenerConfigurations();

  /**
   * Gets the {@link Factory} for the {@link javax.cache.integration.CacheLoader}, if any.
   * <p/>
   * A CacheLoader should be configured for "Read Through" caches
   * to load values when a cache miss occurs using either the
   * {@link javax.cache.Cache#get(Object)} and/or {@link javax.cache.Cache#getAll(java.util.Set)} methods.
   * <p/>
   * The default value is <code>null</code>.
   *
   * @return the {@link Factory} for the {@link javax.cache.integration.CacheLoader} or null if none has been set.
   */
  Factory<CacheLoader<K, V>> getCacheLoaderFactory();

  /**
   * Gets the {@link Factory} for the {@link javax.cache.integration.CacheWriter}, if any.
   * <p/>
   * The default value is <code>null</code>.
   *
   * @return the {@link Factory} for the {@link javax.cache.integration.CacheWriter} or null if none has been set.
   */
  Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory();

  /**
   * Gets the {@link Factory} for the {@link javax.cache.expiry.ExpiryPolicy} to be used for caches.
   * <p/>
   * The default value is a {@link Factory} that will produce a
   * {@link javax.cache.expiry.EternalExpiryPolicy} instance.
   *
   * @return the {@link Factory} for {@link javax.cache.expiry.ExpiryPolicy} (must not be <code>null</code>)
   */
  Factory<ExpiryPolicy<? super K, ? super V>> getExpiryPolicyFactory();

}
