/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.processor;

import javax.cache.CacheException;

/**
 * An exception to indicate a problem occurred attempting to execute an
 * {@link EntryProcessor} against an entry.
 * <p>
 * An implementation must wrap any {@link Exception} thrown by an
 * {@link EntryProcessor} in this exception.
 *
 * @author Greg Luck
 * @since 1.0
 */
public class EntryProcessorException extends CacheException {

  private static final long serialVersionUID = 20130822110920L;


  /**
   * Constructs a new CacheLoaderException.
   */
  public EntryProcessorException() {
    super();
  }

  /**
   * Constructs a new CacheLoaderException with a message string.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public EntryProcessorException(String message) {
    super(message);
  }

  /**
   * Constructs a CacheLoaderException with a message string, and
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
  public EntryProcessorException(String message, Throwable cause) {
    super(message, cause);
  }


  /**
   * Constructs a new CacheLoaderException with the specified cause and a
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
  public EntryProcessorException(Throwable cause) {
    super(cause);
  }

}
