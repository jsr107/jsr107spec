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
 * The listeners are fired:
 * <ul>
 *     <li>in order in which they were registered</li>
 *     <li>after the entry is mutated in the cache</li>
 *     <li>the calling thread blocks until the listener returns if the listener was registered as synchronous</li>
 * </ul>
 * A synchronous listener is not permitted to mutate any caches.
 *
 * @see CacheEntryCreatedListener
 * @see CacheEntryUpdatedListener
 * @see CacheEntryReadListener
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
