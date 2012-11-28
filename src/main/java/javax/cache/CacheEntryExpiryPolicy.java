/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.Cache.Entry;
import javax.cache.CacheConfiguration.Duration;

/**
 * Defines functions to determine when Cache entries will expire.
 * 
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * 
 * @author Brian Oliver
 */
public interface CacheEntryExpiryPolicy<K, V> {
    
    /**
     * The default {@link CacheEntryExpiryPolicy} ensures Cache entries
     * live forever when they are created and modified.  No change to Cache entry
     * time to live occurs when said entries are accessed.
     */
    CacheEntryExpiryPolicy DEFAULT = new CacheEntryExpiryPolicy() {
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration onCreation(Entry entry) {
            return Duration.ETERNAL;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration onAccess(Entry entry, Duration duration) {
            return duration;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public Duration onModified(Entry entry, Duration duration) {
            return Duration.ETERNAL;
        }
    };

    /**
     * This method is called when a Cache.Entry is created.  It returns 
     * the time to expire (live) for the new entry.
     * 
     * @param entry the cache entry that was created
     * 
     * @return the time to live for the new cache entry
     */
    Duration onCreation(Cache.Entry<K, V> entry);

    /**
     * This method is called when a Cache.Entry is accessed.  It returns 
     * the new time to expire (live) for the said entry after the access.
     * 
     * @param entry the cache entry that was accessed
     * @param duration the current time to live before the entry expires
     * 
     * @return the new time to live before the entry expires
     */
    Duration onAccess(Cache.Entry<K, V> entry, Duration duration);
        
    /**
     * This method is called when a Cache.Entry is modified.  It returns 
     * the new time to expire (live) for the said entry after the modification.
     * 
     * @param entry the cache entry that was modified
     * @param duration the current time to live before the entry expires
     * 
     * @return the new time to live before the entry expires
     */
    Duration onModified(Cache.Entry<K, V> entry, Duration duration);
}
