/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.transaction;

/**
 * An enum for the isolation levels.
 * @author Greg Luck
 * @since  1.0
 */
public enum IsolationLevel {

    /**
     * Isolation level
     */
    NONE(java.sql.Connection.TRANSACTION_NONE),

    /**
     * Isolation level
     */
    READ_UNCOMMITTED(java.sql.Connection.TRANSACTION_READ_UNCOMMITTED),

    /**
     * Isolation level
     */
    READ_COMMITTED(java.sql.Connection.TRANSACTION_READ_COMMITTED),

    /**
     * Isolation level
     */
    REPEATABLE_READ(java.sql.Connection.TRANSACTION_REPEATABLE_READ),

    /**
     * Isolation level
     */
    SERIALIZABLE(java.sql.Connection.TRANSACTION_SERIALIZABLE);

    private final int value;

    private IsolationLevel(int value) {
        this.value = value;
    }

    /**
     * Return the constant for this isolation level defined in {@link java.sql.Connection}
     * @return the java.sql.Connection value
     */
    public int getJavaSqlConstant() {
        return value;
    }
}
