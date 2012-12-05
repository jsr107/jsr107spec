/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.event;

/**
 * Defines the runtime semantics of a {@link CacheEntryListener}.
 * 
 * @param <K> the type of keys
 * @param <V> the type of values
 * 
 * @author Brian Oliver
 */
public interface CacheEntryListenerRegistration<K, V> {
    /**
     * Gets the {@link CacheEntryListener}.
     * 
     * @return the {@link CacheEntryListener}
     */
    CacheEntryListener<? super K, ? super V> getCacheEntryListener();
    
    /**
     * Determines if the old value should be provided to the {@link CacheEntryListener}.
     * 
     * @return <code>true</code> if the old value is required by the {@link CacheEntryListener}
     */
    boolean isOldValueRequired();
    
    /**
     * Gets the {@link CacheEntryFilter} that should be applied prior to 
     * notifying the {@link CacheEntryListener}.  When <code>null</code> no
     * filtering is applied and all appropriate events are notified. 
     * 
     * @return the {@link CacheEntryFilter} or <code>null</code> if no filtering
     *         is required
     */
    CacheEntryFilter<? super K, ? super V> getCacheEntryFilter();
    
    /**
     * Determines if the thread that caused an event to be created should be
     * blocked (not return from the operation causing the event) until the 
     * {@link CacheEntryListener} has been notified.
     * 
     * @return <code>true</code> if the thread that created the event should block
     */
    boolean isSynchronous();
}
