/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.mbeans;

import javax.cache.CacheStatistics;
import javax.management.MXBean;

/**
 * A management bean for cache.
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
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
@MXBean
public interface CacheStatisticsMXBean extends CacheStatistics {
}
