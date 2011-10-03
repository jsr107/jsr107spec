/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.transaction;

import static java.sql.Connection.TRANSACTION_NONE;
import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
import static java.sql.Connection.TRANSACTION_READ_COMMITTED;
import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;
import static java.sql.Connection.TRANSACTION_SERIALIZABLE;


/**
 * A enum for the isolation levels. The enum values used are the constants from the java.sql.Connection package.
 * @author Greg Luck
 */
public enum IsolationLevel {

    TX_NONE(TRANSACTION_NONE),

    TX_READ_UNCOMMITTED(TRANSACTION_READ_UNCOMMITTED),

    TX_READ_COMMITTED(TRANSACTION_READ_COMMITTED),

    TX_READ_REPEATABLE(TRANSACTION_REPEATABLE_READ),

    TX_READ_SERIALIZABLE(TRANSACTION_SERIALIZABLE);

    private final int value;

    private IsolationLevel(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

}
