/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

import javax.cache.Cache;

/**
 * Invoked if a cache entry is created,
 * for example through a {@link Cache#put(Object, Object)} operation.
 * If an entry for the key existed prior to the operation it is not invoked, as this ia an update.
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @see CacheEntryUpdatedListener
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryCreatedListener<K, V> extends CacheEntryListener {

    /**
     * Called after the entry has been created (put into the cache where no previous mapping existed).
     * This method is not called if a batch operation was performed.
     *
     * @param entry The entry just added.
     * @see #onCreateAll(Iterable)
     */
    void onCreate(Cache.Entry<K, V> entry);

    /**
     * Called after the entries have been created (put into the cache where no previous mapping existed).
     *
     * @param entries The entries just added.
     */
    void onCreateAll(Iterable<Cache.Entry<K, V>> entries);


}
