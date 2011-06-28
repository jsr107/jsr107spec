/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

import javax.cache.Cache;

/**
 * Invoked if a cache entry is removed,
 * for example through a {@link Cache#remove(Object)} call.
 *
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.7
 */
public interface CacheEntryRemovedListener<K, V> extends CacheEntryListener {

    /**
     * Called after the entry has been removed. If no entry existed for key the event is not called.
     * This method is not called if a batch operation was performed.
     *
     * @param entry The entry just removed.
     * @see #onRemoveAll(Iterable)
     */
    void onRemove(Cache.Entry<K, V> entry);

    /**
     * Called after the entries have been removed by a batch operation.
     *
     * @param entries The entry just removed.
     */
    void onRemoveAll(Iterable<Cache.Entry<K, V>> entries);


}
