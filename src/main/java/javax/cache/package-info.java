/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * This package contains the API for JCache. JCache describes the technique
 * whereby Java developers use a {@link CachingProvider} to temporarily cache Java
 * objects. It provides a common way for Java programs to create, access, update
 * and remove entries from caches.
 * <h1>Core Concepts</h1>
 * The Java Caching API defines five core interfaces: {@link CachingProvider},
 * {@link CacheManager}, {@link Cache}, {@link Entry} and {@link ExpiryPolicy}.
 * <p>
 * A {@link CachingProvider} defines the mechanism to establish, configure,
 * acquire, manage and control zero or more {@link CacheManager}s.  An application
 * may access and use zero or more {@link CachingProvider}s at runtime.
 * <p>
 * A {@link CacheManager} defines the mechanism to establish, configure, acquire,
 * manage and control zero or more uniquely named {@link Cache}s all within the
 * context of the {@link CacheManager}. A {@link CacheManager} is owned by
 * a single {@link CachingProvider}.
 * <p>
 * A {@link Cache} is a Map-like data-structure that permits the temporary storage
 * of Key-based Values, some what like {@link Map} data-structure.  A
 * {@link Cache} is owned by a single {@link CacheManager}.
 * <p>
 * An {@link Entry} is a single key-value pair stored by a {@link Cache}.
 * <p>
 * Each entry stored by a {@link Cache} has a defined duration, called the Expiry
 * Duration, during which they may be accessed, updated and removed.  Once this
 * duration has passed, the entry is said to be expired. Once expired, entries are
 * no longer available to be accessed, updated or removed, just as if they never
 * existed in a {@link Cache}. Expiry is set using an {@link ExpiryPolicy}.
 * <h1>Store-By-Value and Store-By-Reference</h1>
 * Entries are stored by individual {@link Cache}s using one of two mechanisms:
 * store-by-value and store-by-reference.
 * <h2>Store-By-Value</h2>
 * The default mechanism, called store-by-value, instructs an implementation to
 * make a copy of application provided keys and values prior to storing them in a
 * {@link Cache} and later to return a new copy of the entries when accessed from a
 * {@link Cache}.
 * <p>
 * The purpose of copying entries as they are stored in a {@link Cache} and again when
 * they are returned from a {@link Cache} is to allow applications to continue mutating
 * the state of the keys and values without causing side-effects to entries held
 * by a {@link Cache}.
 * <h2>Store-By-Reference</h2>
 * The alternative and optional mechanism, called store-by-reference, instructs a
 * {@link Cache} implementation to simply store and return references to the application
 * provided keys and values, instead of making a copies as required by the
 * store-by-value approach.  Should an application later mutate the keys or values
 * provided to a {@link Cache} using store-by-reference semantics, the side-effects of the
 * mutations will be visible to those accessing the entries from the {@link Cache},
 * without an application having to update the {@link Cache}.
 * <h1>{@link Cache}s and {@link Map}s</h1>
 * While {@link Cache}s and {@link Map}s share somewhat similar APIs,
 * {@link Cache}s are not Maps and {@link Map}s are not {@link Cache}s.
 * The following section outlines the main similarities and differences.
 * <p>
 * Like {@link Map}-based data-structures:
 * <ul>
 * <li>{@link Cache} values are stored and accessed through an associated key.</li>
 * <li>Each key may only be associated with a single value in a {@link Cache}.
 * <li>Great care must be exercised if mutable objects are used as keys. The
 * behavior of a {@link Cache} is undefined if a key is mutated in a manner that
 * affects equals comparisons when a key is used with a {@link Cache}.</li>
 * <li>{@link Cache}s depend on the concept of equality to determine when keys
 * and values are the same. Consequently custom key and value classes should
 * define a suitable implementation of the {@link Object#equals} method.</li>
 * <li> Custom key classes should additionally provide a suitable implementation
 * of the {@link Object#hashCode()} method.
 * <p>
 * Although recommended, implementations are not required to call either the
 * Object.hashCode or Object.equals methods defined by custom key classes.
 * Implementations are free to use optimizations whereby the invocation of these
 * methods is avoided.
 * <p>
 * As this specification does not define the concept of object equivalence it
 * should be noted applications that make use of custom key classes and rely on
 * implementation specific optimizations to determine equivalence may not be
 * portable.</li>
 * </ul>
 * Unlike {@link Map}-based data-structures:
 * <ul>
 * <li>{@link Cache} keys and values must not be null.</li>
 * <li>Any attempt to use null for keys or values will result in a
 * {@link NullPointerException} being thrown, regardless of the use.</li>
 * <li>Entries may expire.</li>
 * <li>Entries may be evicted.</li>
 * <li>To support the compare-and-swap (CAS) operations, those that atomically
 * compare and exchange values, custom value classes should provide a suitable
 * implementation of {@link Object#equals}.
 * <p>
 * Although recommended, implementations are not required to call the
 * {@link Object#equals} method defined by custom value classes. Implementations
 * are free to implement optimizations whereby the invocation of this method is avoided.
 * </li>
 * <li>Implementations may require Keys and Values to be serializable in some
 * manner.</li>
 * <li>{@link Cache}s may be configured to control how entries are stored,
 * either using
 * store-by-value or optionally using store-by-reference semantics.</li>
 * <li> Implementations may optionally enforce security restrictions. In case of a
 * violation, a {@link SecurityException} must be thrown.</li>
 * </ul>
 * <h1>Consistency</h1>
 * Consistency refers to the behavior of caches and the guarantees that exist when
 * concurrent cache mutation occur together with the visibility of the mutations
 * when multiple threads are accessing a cache.
 * <p>
 * All implementations must support the Default Consistency model as outlined
 * below.
 * <h2>Default Consistency</h2>
 * When using the default consistency mode, most {@link Cache} operations are performed as
 * if a locking mechanism exists for each key in a {@link Cache}. When a cache
 * operation acquires an exclusive read and write lock on a key all subsequent
 * operations on that key will block until that lock is released. The consequences
 * are that operations performed by a thread happen-before read or mutation
 * operations performed by another thread, including threads in different
 * Java Virtual Machines.
 * <p>
 * For some {@link Cache} operations the value returned by a {@link Cache} is considered the last
 * value. The last value might be an old value or a new value, especially in the
 * case where an entry is concurrently being updated. It is implementation
 * dependent which is returned.
 * <p>
 * Other operations follow a different convention in that mutations may only occur
 * when the current state of an entry matches a desired state. In such operations
 * multiple threads are free to compete to apply these changes i.e. as if they
 * share a lock.
 * <p>
 * As these methods must interact with other {@link Cache} operations acting as if they
 * had an exclusive lock, the CAS methods cannot write new values without acting
 * as if they also had an exclusive lock.
 * <p>
 * See the JCache Specification for further details.
 * <h2>Further Consistency Models</h2>
 * An implementation may provide support for different consistency models in
 * addition to the required <em>Default Consistency</em> mode
 * <h1>A Simple Example</h1>
 * This simple example creates a default {@link CacheManager}, configures a
 * {@link Cache} on it called “simpleCache” with a key type of String and a value
 * type of Integer and an expiry of one hour and then performs a some cache
 * operations.
 * <p>
 * <pre><code>
 * //resolve a cache manager
 * CachingProvider cachingProvider = Caching.getCachingProvider();
 * CacheManager cacheManager = cachingProvider.getCacheManager();
 *
 * //configure the cache
 * MutableConfiguration<String, Integer> config =
 *    new MutableConfiguration<>()
 *    .setTypes(String.class, Integer.class)
 *    .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(ONE_HOUR))
 *    .setStatisticsEnabled(true);
 *
 * //create the cache
 * Cache<String, Integer> cache = cacheManager.createCache("simpleCache", config);
 *
 * //cache operations
 * String key = "key";
 * Integer value1 = 1;
 * cache.put("key", value1);
 * Integer value2 = cache.get(key);
 * cache.remove(key);
 * </code></pre>
 *
 * @author Greg Luck
 * @since 1.0
 */
package javax.cache;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.spi.CachingProvider;

import java.util.Map;

import static javax.cache.Cache.Entry;
