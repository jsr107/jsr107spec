/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;


/**
 * Thrown to indicate an exception has occurred in the Cache.
 * <p/>
 * This is the base class for all cache exceptions.
 *
 * @author Greg Luck
 * @since 1.0
 */
public class CacheException extends RuntimeException {

    /**
     * Constructs a new CacheException.
     */
    public CacheException() {
        super();
    }

    /**
     * Constructs a new CacheException with a message string.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CacheException(String message) {
        super(message);
    }

    /**
     * Constructs a CacheException with a message string, and
     * a base exception
     *
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param throwable
     */
    public CacheException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new CacheException with the specified cause and a
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
    public CacheException(Throwable cause) {
        super(cause);
    }

}
