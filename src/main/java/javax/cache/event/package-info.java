/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 This package contains event listener interfaces.

 These may be registered for callback notification of the cache events.
 The specific interface should be implemented for each event type a callback
 is desired on.
 <p/>
 Event notifications occur synchronously in the line of execution of the calling thread.
 The calling thread blocks until the listener has completed execution or thrown a {@link CacheEntryListenerException}.
 <p/>
 Listeners are invoked <strong>after</strong> the cache is updated. If the listener throws
 an {@link CacheEntryListenerException} this will propagate back to the caller but it does not affect the cache update
 as it already completed before the listener was called. If the cache is transactional, transactions
 must commit <strong>before</strong> listeners are called. If an exception is thrown by a listener this does not
 affect the transaction as the transaction has already completed.
 <p/>
 @author Greg Luck
 @author Yannis Cosmadopoulos
 @since 1.0
 */
package javax.cache.event;
