/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * todo: finish this
 * Starter for a statistics MBean.
 * @author Greg Luck
 * @since 1.7
 */
public interface CacheStatisticsMBean {

    /**
     *
     * @return the number of entries
     */
    long getEntryCount();

    /**
     *
     * @return the number if hits
     */
    long getCacheHits();

    /**
     *
     * @return the number of misses
     */
    long getCacheMisses();

    /**
     *
     */
    void clearStatistics();
}
