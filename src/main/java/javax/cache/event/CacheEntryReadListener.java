/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

/**
 * Invoked if a cache entry is read,
 * for example through a {@link javax.cache.Cache#get(Object)} call.
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryReadListener<K, V> extends CacheEntryListener<K, V> {

    /**
     * Called after the entry has been read. If no entry existed for the key the event is not called.
     * This method is not called if a batch operation was performed.
     *
     * @param event The event just read.
     * @see #entriesRead(Iterable)
     */
    void entryRead(CacheEntryEvent<? extends K, ? extends V> event);

    /**
     * Called after the entries have been read. Only entries which existed in the cache are passed in.
     *
     * @param events The events just read.
     */
    void entriesRead(Iterable<CacheEntryEvent<? extends K, ? extends V>> events);
}
