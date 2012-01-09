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
 *     <li>after the entry is added to the cache</li>
 *     <li>If the event is registered with synchronous notification, it is fired synchronously in the same thread if in the same JVM</li>
 * </ul>
 *
 * Because a listener occurs...
 *
 * todo simplify back to one
 * todo Java SE wants one method per listener. Yannis to find email
 * todo remove Notification Scope
 * todo say something about exceptions
 * todo say the key cannot be mutated?
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
