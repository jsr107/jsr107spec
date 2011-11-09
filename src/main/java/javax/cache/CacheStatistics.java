/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.management.MXBean;
import java.util.Date;

/**
 * A management bean for cache statistics.
 * <p/>
 * Statistics are accumulated from the time a cache is created. They can be reset to zero using {@link #clearStatistics()}.
 * <p/>
 * There are no defined consistency semantics for statistics. Refer to the implementation for precise semantics.
 * <p/>
 * Each cache's statistics object must be registered with an ObjectName that is unique and has the following:
 * <p/>
 * Type:
 * <code>javax.cache:type=CacheStatistics</code>
 * <p/>
 * Required Attributes:
 * <ul>
 * <li>CacheManager Name
 * <li>Cache Name
 * </ul>
 * @author Greg Luck
 * @since 1.0
 *
 */
@MXBean
public interface CacheStatistics {

    /**
     * @return the name of the Cache these statistics are for
     */
    String getName();

    /**

     * @return the {@link javax.management.ObjectName} of the Cache these statistics are for
     */
    //ObjectName getObjectName();

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
     * The date from which statistics have been accumulated. Because statistics can be cleared, this is not necessarily
     * since the cache was started.
     *
     * @return the date statistics started being accumulated
     */
    Date statsAccumulatingFrom();

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
     * A miss is a get request which is not satisfied.
     * <p/>
     * In a simple cache a miss occurs when the cache does not satisfy the request.
     * <p/>
     * In a caches with multiple tiered storage, a miss may be implemented as a miss
     * to the cache or to the first tier.
     * <p/>
     * In a read-through cache a miss is an absence of the key in teh cache which will trigger a call to a CacheLoader. So it is
     * still a miss even though the cache will load and return the value.
     * <p/>
     * Refer to the implementation for precise semantics.
     *
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
     * A "get" is an operation that returns the current or previous value. It does not include checking for the existence
     * of a key.
     *
     * @return the number of gets
     */
    long getCacheGets();

    /**
     * The total number of puts to the cache.
     * <p/>
     * A put is counted even if it is immediately evicted. A replace includes a put and remove.
     *
     * @return the number of hits
     */
    long getCachePuts();

    /**
     * The total number of removals from the cache. This does not include evictions, where the cache itself
     * initiates the removal to make space.
     * <p/>
     * A replace is a put that overwrites a mapping and is not considered a remove.
     *
     * @return the number of hits
     */
    long getCacheRemovals();

    /**
     * The total number of evictions from the cache. An eviction is a removal initiated by the cache itself to free
     * up space. An eviction is not treated as a removal and does not appear in the removal counts.
     *
     * @return the number of evictions from the cache
     */
    long getCacheEvictions();

    /**
     * The mean time to execute gets.
     * <p/>
     * In a read-through cache the time taken to load an entry on miss is not included in get time.
     *
     * @return the time in milliseconds
     */
    float getAverageGetMillis();

    /**
     * The mean time to execute puts.
     *
     * @return the time in milliseconds
     */
    float getAveragePutMillis();

    /**
     * The mean time to execute removes.
     *
     * @return the time in milliseconds
     */
    float getAverageRemoveMillis();

}
