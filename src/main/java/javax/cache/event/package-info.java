/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * This package contains event listener interfaces.
 * <p>
 * These may be registered for callback notification of the cache events.
 * The specific interface should be implemented for each event type a callback
 * is desired on.
 * <p>
 * Event notifications occur synchronously in the line of execution of the calling thread.
 * The calling thread blocks until the listener has completed execution or thrown a {@link javax.cache.event.CacheEntryCreatedListener}.
 * <p>
 * Listeners are invoked <strong>after</strong> the cache is updated. If the listener throws
 * an {@link javax.cache.event.CacheEntryCreatedListener} this will propagate back to the caller but it does not affect the cache update
 * as it already completed before the listener was called.
 * 
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
package javax.cache.event;
