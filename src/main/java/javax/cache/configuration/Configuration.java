/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.configuration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.event.CacheEntryListener;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.io.Serializable;
import java.util.List;

/**
 * A read-only representation of a {@link Cache} configuration.
 * <p/>
 * The properties provided by instances of this interface are used by
 * {@link CacheManager}s to configure {@link Cache}s.
 * <p/>
 * Implementations of this interface must override {@link Object#hashCode()} and
 * {@link Object#equals(Object)} as {@link Configuration}s are often compared at
 * runtime.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * @since 1.0
 */
public abstract class Configuration<K, V> implements Serializable {

  /**
   * Determines the required type of keys for {@link Cache}s configured
   * with this {@link Configuration}.
   *
   * @return the key type or <code>Object.class</code> if the type is undefined
   */
  public abstract Class<K> getKeyType();

  /**
   * Determines the required type of values for {@link Cache}s configured
   * with this {@link Configuration}.
   *
   * @return the value type or <code>Object.class</code> if the type is undefined
   */
  public abstract Class<V> getValueType();

  /**
   * Determines if a {@link Cache} should operate in read-through mode.
   * <p/>
   * When in "read-through" mode, cache misses that occur due to cache entries
   * not existing as a result of performing a "get" will appropriately
   * cause
   * the configured {@link CacheLoader} to be invoked.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link Cache} is in "read-through"
   *         mode.
   * @see #getCacheLoaderFactory()
   */
  public abstract boolean isReadThrough();

  /**
   * Determines if a {@link Cache} should operate in write-through mode.
   * <p/>
   * When in "write-through" mode, cache updates that occur as a result of performing
   * "put" operations called via one of {@link Cache#put(Object, Object)},
   * {@link Cache#getAndRemove(Object)},
   * {@link Cache#removeAll()}, {@link Cache#getAndPut(Object,
   * Object)}
   * {@link Cache#getAndRemove(Object)}, {@link Cache#getAndReplace(Object,
   * Object)},
   * {@link Cache#invoke(Object, javax.cache.processor.EntryProcessor,
   * Object...)},
   * {@link Cache#invokeAll(java.util.Set, javax.cache.processor.EntryProcessor,
   * Object...)}
   * will appropriately cause the configured {@link CacheWriter}
   * to be invoked.
   * <p/>
   * <p/>
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link Cache} is in "write-through"
   *         mode.
   * @see #getCacheWriterFactory()
   */
  public abstract boolean isWriteThrough();

  /**
   * Whether storeByValue (true) or storeByReference (false).
   * When true, both keys and values are stored by value.
   * <p/>
   * When false, both keys and values are stored by reference.
   * Caches stored by reference are capable of mutation by any threads holding
   * the reference. The effects are:
   * <ul>
   * <li>if the key is mutated, then the key may not be retrievable or removable</li>
   * <li>if the value is mutated, then all threads in the JVM can potentially observe
   * those mutations,
   * subject to the normal Java Memory Model rules.</li>
   * </ul>
   * Storage by reference only applies to the local heap. If an entry is moved off heap
   * it will
   * need to be transformed into a representation. Any mutations that occur after
   * transformation
   * may not be reflected in the cache.
   * <p/>
   * Additionally Store-By-Reference is only supported for non-transactional caches.
   * Attempts
   * to configure a cache using both transactions and Store-By-Reference will result in
   * an
   * in a CacheException.
   * <p/>
   * When a cache is storeByValue, any mutation to the key or value does not affect the
   * key of value
   * stored in the cache.
   * <p/>
   * The default value is <code>true</code>.
   *
   * @return true if the cache is store by value
   */
  public abstract boolean isStoreByValue();

  /**
   * Checks whether statistics collection is enabled in this cache.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return true if statistics collection is enabled
   */
  public abstract boolean isStatisticsEnabled();

  /**
   * Checks whether management is enabled on this cache.
   * <p/>
   * The default value is <code>false</code>.
   *
   * @return true if management is enabled
   */
  public abstract boolean isManagementEnabled();

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
  public abstract boolean isTransactionsEnabled();

  /**
   * Gets the transaction isolation level.
   * <p/>
   * The default value is {@link IsolationLevel#NONE}.
   *
   * @return the isolation level.
   */
  public abstract IsolationLevel getTransactionIsolationLevel();

  /**
   * Gets the transaction mode.
   * <p/>
   * The default value is {@link Mode#NONE}.
   *
   * @return the mode of the cache.
   */
  public abstract Mode getTransactionMode();

  /**
   * Obtains the {@link CacheEntryListenerConfiguration}s for {@link CacheEntryListener}s
   * to be configured on a {@link Cache}.
   *
   * @return an {@link Iterable} over the {@link CacheEntryListenerConfiguration}s
   */
  public abstract List<CacheEntryListenerConfiguration<K,
      V>> getCacheEntryListenerConfigurations();

  /**
   * Gets the {@link Factory} for the {@link CacheLoader}, if
   * any.
   * <p/>
   * A CacheLoader should be configured for "Read Through" caches
   * to load values when a cache miss occurs using either the
   * {@link Cache#get(Object)} and/or {@link Cache#getAll(java.util.Set)}
   * methods.
   * <p/>
   * The default value is <code>null</code>.
   *
   * @return the {@link Factory} for the {@link CacheLoader} or
   *         null if none has been set.
   */
  public abstract Factory<CacheLoader<K, V>> getCacheLoaderFactory();

  /**
   * Gets the {@link Factory} for the {@link CacheWriter}, if
   * any.
   * <p/>
   * The default value is <code>null</code>.
   *
   * @return the {@link Factory} for the {@link CacheWriter} or
   *         null if none has been set.
   */
  public abstract Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory();

  /**
   * Gets the {@link Factory} for the {@link ExpiryPolicy} to be used
   * for caches.
   * <p/>
   * The default value is a {@link Factory} that will produce a
   * {@link javax.cache.expiry.EternalExpiryPolicy} instance.
   *
   * @return the {@link Factory} for {@link ExpiryPolicy} (must not be
   *         <code>null</code>)
   */
  public abstract Factory<ExpiryPolicy<? super K>> getExpiryPolicyFactory();

}
