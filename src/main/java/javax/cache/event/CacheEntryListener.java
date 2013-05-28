/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

import java.util.EventListener;

/**
 * Tagging interface for cache entry listeners.
 * <p/>
 * Sub-interfaces exist for the various cache events allowing a listener to be created which implements only those listeners
 * it is interested in.
 * <p/>
 * The motivation for this design is to allow efficient implementation of network based listeners.
 * <p/>
 * Listeners should be implemented with care. In particular it is important to consider the impact on performance
 * and latency.
 * <p/>
 * A listener is a user supplied object instance and therefore can only be registered programmatically.
 * <p/>
 * Listeners:
 * <ul>
 *     <li>are called in the order in which they were registered</li>
 *     <li>are fired after the entry is mutated in the cache</li>
 *     <li>if synchronous are fired, for a given key, in the order in which events occur</li>
 *     <li>block the calling thread until the listener returns if the listener was registered as synchronous</li>
 *     <li>which are asynchronous iterate through multiple events with an undefined ordering, except that events on the same key
 *     are in the the order in which the events occur.</li>
 * </ul>
 * A listener which mutates a cache on the CacheManager may cause a deadlock. Detection and response to deadlocks
 * is implementation specific.
 * <p/>
 * A listener on a transactional cache is executed orthogonally to the transaction. If synchronous it is executed after the mutation
 * and not after the transaction commits, and if asynchronous the timing is undefined. A listener which throws an exception will not affect
 * the transaction. A transaction which is rolled back will not unfire a listener.
 * <p/>
 * A listener will be notified of events for the time it is registered. Listeners are not required to be durable.
 *
 * @see CacheEntryCreatedListener
 * @see CacheEntryUpdatedListener
 * @see CacheEntryRemovedListener
 * @see CacheEntryExpiredListener
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryListener<K, V> extends EventListener {

}
