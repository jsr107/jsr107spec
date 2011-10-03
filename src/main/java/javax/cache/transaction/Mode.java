/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.transaction;

import static java.sql.Connection.TRANSACTION_NONE;
import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;


/**
 * A enum for the different transaction modes.
 * @author Greg Luck
 */
public enum Mode {

    /**
     * A resource local transaction (can only be used for a transcation involving a single CacheManager and no other XA resources)
     */
    LOCAL,

    /**
     * A global transaction that can span multiple XA Resources
     */
    XA
}


