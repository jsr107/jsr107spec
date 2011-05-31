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
 */
public interface CacheStatisticsMBean {

    /**
     *
     * @return
     */
    long getEntryCount();

    /**
     *
     * @return
     */
    long getCacheHits();

    /**
     *
     * @return
     */
    long getCacheMisses();

    /**
     *
     */
    void clearStatistics();
}
