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
 * The motivation for this design is to allow efficient implementation of network based listenrs.
 * <p/>
 * Listeners should be implemented with care. In particular it is important to consider the impact on perforamnce
 * and latency.
 *
 * @see CacheEntryCreatedListener
 * @see CacheEntryUpdatedListener
 * @see CacheEntryReadListener
 * @see CacheEntryRemovedListener
 * @see CacheEntryExpiredListener
 *
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.7
 */
public interface CacheEntryListener extends EventListener {

    /**
     * @return the notification scope for this listener
     */
    NotificationScope getNotificationScope();


}
