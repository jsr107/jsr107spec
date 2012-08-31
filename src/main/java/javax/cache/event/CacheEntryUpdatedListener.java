/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * Invoked if an existing cache entry is updated, for example through a {@link javax.cache.Cache#put(Object, Object)} or a {@link javax.cache.CacheLoader} operation .
 *
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @see CacheEntryCreatedListener
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryUpdatedListener<K, V> extends CacheEntryListener<K, V> {

    /**
     * todo change this to have two args: the old value and the new value
     * Called after the entry has been updated (put into the cache where a previous mapping existed).
     *
     * @param event The event just updated.
     * @throws CacheEntryListenerException if there is problem executing the listener
     */
    void entryUpdated(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException;
}
