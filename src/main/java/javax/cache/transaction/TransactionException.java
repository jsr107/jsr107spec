package javax.cache.transaction;

import javax.cache.CacheException;

/**
 * A transaction exception.
 * @author Greg Luck
 */
public class TransactionException extends CacheException {

private static final long serialVersionUID = 1L;

    /**
     * Constructs a new TransactionException.
     */
    public TransactionException() {
        super();
    }

    /**
     * Constructs a new TransactionException with a message string.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
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
     * @param throwable
     */
    public TransactionException(String message, Throwable throwable) {
        super(message, throwable);
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
