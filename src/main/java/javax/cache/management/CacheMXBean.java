/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.management;

import javax.cache.Cache;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.management.MXBean;

/**
 * A management bean for cache. It provides configuration information. It does not
 * allow mutation of configuration or mutation of the cache.
 * <p>
 * Each cache's management object must be registered with an ObjectName that is
 * unique and has the following type and attributes:
 * <p>
 * Type:
 * <code>javax.cache:type=CacheConfiguration</code>
 * <p>
 * Required Attributes:
 * <ul>
 * <li>CacheManager the URI of the CacheManager
 * <li>Cache the name of the Cache
 * </ul>
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
@MXBean
public interface CacheMXBean {

  /**
   * Determines the required type of keys for this {@link Cache}, if any.
   *
   * @return the fully qualified class name of the key type,
   * or "java.lang.Object" if the type is undefined.
   */
  String getKeyType();

  /**
   * Determines the required type of values for this {@link Cache}, if any.
   * @return the fully qualified class name of the value type,
   * or "java.lang.Object" if the type is undefined.
   */
  String getValueType();

  /**
   * Determines if a {@link Cache} should operate in read-through mode.
   * <p>
   * When in read-through mode, cache misses that occur due to cache entries
   * not existing as a result of performing a "get" call via one of
   * {@link Cache#get},
   * {@link Cache#getAll},
   * {@link Cache#getAndRemove} and/or
   * {@link Cache#getAndReplace} will appropriately
   * cause the configured {@link CacheLoader} to be
   * invoked.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link Cache} is in
   *         "read-through" mode.
   * @see CacheLoader
   */
  boolean isReadThrough();

  /**
   * Determines if a {@link Cache} should operate in "write-through"
   * mode.
   * <p>
   * When in "write-through" mode, cache updates that occur as a result of
   * performing "put" operations called via one of
   * {@link Cache#put},
   * {@link Cache#getAndRemove},
   * {@link Cache#removeAll},
   * {@link Cache#getAndPut}
   * {@link Cache#getAndRemove},
   * {@link Cache#getAndReplace},
   * {@link Cache#invoke}
   * {@link Cache#invokeAll}
   * <p>
   * will appropriately cause the configured {@link CacheWriter} to be invoked.
   * <p>
   * The default value is <code>false</code>.
   *
   * @return <code>true</code> when a {@link Cache} is in "write-through" mode.
   * @see CacheWriter
   */
  boolean isWriteThrough();

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
   * When a cache is storeByValue, any mutation to the key or value does not affect
   * the key of value stored in the cache.
   * <p>
   * The default value is <code>true</code>.
   *
   * @return true if the cache is store by value
   */
  boolean isStoreByValue();

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

}
