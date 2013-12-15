/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.integration;

import javax.cache.CacheException;

/**
 * An exception to indicate a problem has occurred executing a {@link CacheWriter}.
 * <p>
 * A Caching Implementation must wrap any {@link Exception} thrown by a {@link
 * CacheWriter} in this exception.
 *
 * @author Greg Luck
 * @since 1.0
 */
public class CacheWriterException extends CacheException {

  private static final long serialVersionUID =  20130822161612L;


  /**
   * Constructs a new CacheWriterException.
   */
  public CacheWriterException() {
    super();
  }

  /**
   * Constructs a new CacheWriterException with a message string.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public CacheWriterException(String message) {
    super(message);
  }

  /**
   * Constructs a CacheWriterException with a message string, and
   * a base exception
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   * @param cause   the cause (that is saved for later retrieval by the
   *                {@link #getCause()} method).  (A <tt>null</tt> value is
   *                permitted, and indicates that the cause is nonexistent or
   *                unknown.)
   * @since 1.0
   */
  public CacheWriterException(String message, Throwable cause) {
    super(message, cause);
  }


  /**
   * Constructs a new CacheWriterException with the specified cause and a
   * detail message of <tt>(cause==null ? null : cause.toString())</tt>
   * (that typically contains the class and detail message of
   * <tt>cause</tt>).  This constructor is useful for runtime exceptions
   * that are little more than wrappers for other throwables.
   *
   * @param cause the cause (that is saved for later retrieval by the
   *              {@link #getCause()} method).  (A <tt>null</tt> value is
   *              permitted, and indicates that the cause is nonexistent or
   *              unknown.)
   * @since 1.0
   */
  public CacheWriterException(Throwable cause) {
    super(cause);
  }

}
