/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * A management bean for caches.
 * <p/>
 * Statistics are accumulated from the time a cache is created. They can be reset to zero using {@link #clearStatistics()}.
 *
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheStatisticsMBean {

    /**
     * @return the name of the Cache these statistics are for
     */
    String getName();

    /**
     * Gets the {@link Status} attribute of the Cache expressed as a String.
     *
     * @return The status value from the Status enum class
     */
    String getStatus();

    /**
     * Clears the statistics counters to 0 for the associated Cache.
     */
    void clearStatistics();


    /**
     * @return the number of entries
     */
    long getEntryCount();

    /**
     * The number of get requests that were satisfied by the cache.
     *
     * @return the number of hits
     */
    long getCacheHits();

    /**
     * {@link #getCacheHits} divided by the total number of gets.
     * This is a measure of cache efficiency.
     *
     * @return the percentage of successful hits, as a decimal
     */
    float getCacheHitPercentage();

    /**
     * @return the number of misses
     */
    long getCacheMisses();

    /**
     * Returns the percentage of cache accesses that did not find a requested entry in the cache.
     *
     * @return the percentage of accesses that failed to find anything
     */
    float getCacheMissPercentage();

    /**
     * The total number of requests to the cache. This will be equal to the sum of the hits and misses.
     * <p/>
     * A "get" is an operation that returns the current or previous value.
     *
     * @return the number of hits
     */
    long getCacheGets();

    /**
     * @return the number of evictions from the cache
     */
    long getCacheEvictions();


}
