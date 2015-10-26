package javax.cache.annotation;

import javax.cache.CacheException;

/**
 * Thrown if an invalid cache annotation configuration is detected.
 *
 * @author Eric Dalquist
 * @since 1.0.1
 */
public class CacheAnnotationConfigurationException extends CacheException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new CacheAnnotationConfigurationException.
     *
     * @since 1.0.1
     */
    public CacheAnnotationConfigurationException() {
        super();
    }

    /**
     * Constructs a new CacheAnnotationConfigurationException with a message string.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @since 1.0.1
     */
    public CacheAnnotationConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a CacheAnnotationConfigurationException with a message string, and
     * a base exception
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.0.1
     */
    public CacheAnnotationConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new CacheAnnotationConfigurationException with the specified cause and a
     * detail message of <tt>(cause==null ? null : cause.toString())</tt>
     * (which typically contains the class and detail message of
     * <tt>cause</tt>).  This constructor is useful for runtime exceptions
     * that are little more than wrappers for other throwables.
     *
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.0.1
     */
    public CacheAnnotationConfigurationException(Throwable cause) {
        super(cause);
    }
}
