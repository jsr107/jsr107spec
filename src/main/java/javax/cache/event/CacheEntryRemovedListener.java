/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * Invoked if a cache entry is removed,
 * for example through a {@link javax.cache.Cache#remove(Object)} call.
 *
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryRemovedListener<K, V> extends CacheEntryListener<K, V> {

    /**
     * Called after the entry has been removed. If no entry existed for key the event is not called.
     *
     * @param event The entry just removed.
     * @see #entryRemoved(CacheEntryEvent)
     * @throws CacheEntryListenerException if there is problem executing the listener
     */
    void entryRemoved(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException;

////todo change to the following for each listener.
//    /**
//     * Called after one or more entries have been removed. If no entry existed for key(s) the event is not raised.
//     *
//     * @param events The entries just removed.
//     * @throws CacheEntryListenerException if there is problem executing the listener
//     */
//    void onRemoved(Iterable<CacheEntryEvent<? extends K, ? extends V>> events,) throws CacheEntryListenerException;


}
