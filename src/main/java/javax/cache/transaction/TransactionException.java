/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.transaction;

import javax.cache.CacheException;

/**
 * A transaction exception.
 * @author Greg Luck
 * @since  1.0
 */
public class TransactionException extends CacheException {

private static final long serialVersionUID = 1L;

    /**
     * Constructs a new TransactionException.
     * @since  1.0
     */
    public TransactionException() {
        super();
    }

    /**
     * Constructs a new TransactionException with a message string.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @since  1.0
     */
    public TransactionException(String message) {
        super(message);
    }

    /**
     * Constructs a TransactionException with a message string, and
     * a base exception
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.0
     */
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new TransactionException with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @since  1.0
     */
    public TransactionException(Throwable cause) {
        super(cause);
    }


}
