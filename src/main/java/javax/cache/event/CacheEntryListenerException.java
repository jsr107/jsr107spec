/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.event;

import javax.cache.CacheException;

/**
 * An exception to indicate a problem has occurred with a listener.
 * As listeners are only called after the cache has been mutated, the mutation
 * to the cache is not affected.
 *
 * @author Greg Luck
 * @since 1.0
 */
public class CacheEntryListenerException extends CacheException {

  private static final long serialVersionUID = 20130621110150L;


  /**
   * Constructs a new CacheEntryListenerException.
   */
  public CacheEntryListenerException() {
    super();
  }

  /**
   * Constructs a new CacheEntryListenerException with a message string.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public CacheEntryListenerException(String message) {
    super(message);
  }

  /**
   * Constructs a CacheEntryListenerException with a message string, and
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
  public CacheEntryListenerException(String message, Throwable cause) {
    super(message, cause);
  }


  /**
   * Constructs a new CacheEntryListenerException with the specified cause and a
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
  public CacheEntryListenerException(Throwable cause) {
    super(cause);
  }

}
