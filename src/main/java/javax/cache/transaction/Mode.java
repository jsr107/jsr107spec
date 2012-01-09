/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.transaction;

/**
 * A enum for the different cache transaction modes.
 * @author Greg Luck
 * @since 1.0
 */
public enum Mode {

    /**
     * The cache is not transactional.
     */
    NONE,

    /**
     * A resource local transaction (can only be used for a transcation involving a single CacheManager and no other XA resources)
     */
    LOCAL,

    /**
     * A global transaction that can span multiple XA Resources
     */
    XA
}


