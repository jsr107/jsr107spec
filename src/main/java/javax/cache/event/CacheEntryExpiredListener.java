/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;


/**
 * Invoked if a cache entry is evicted because of expiration.
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryExpiredListener<K, V> extends CacheEntryListener<K, V> {

    /**
     * Called after the entry has expired and has thus been removed from the Cache.
     * This is distinguished from the {@link CacheEntryRemovedListener} where an explicit remove call was made.
     *
     * @param entry The entry that has expired.
     * @throws CacheEntryListenerException if there is problem executing the listener
     */
    void entryExpired(CacheEntryEvent<? extends K, ? extends V> entry) throws CacheEntryListenerException;
}
