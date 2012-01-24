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
     *
     * @param event The event just read.
     * @throws CacheEntryListenerException if there is problem executing the listener
     */
    void entryRead(CacheEntryEvent<? extends K, ? extends V> event) throws CacheEntryListenerException;
}
