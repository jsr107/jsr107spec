/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.integration;

/**
 * A CompletionListener is implemented by an application when it needs to be
 * notified of the completion of some Cache operation.
 * <p>
 * When the operation is complete, the Cache provider notifies the application
 * by calling the {@link #onCompletion()} method of the {@link
 * CompletionListener}.
 * <p>
 * If the operation fails for any reason, the Cache provider calls the
 * {@link #onException(Exception)} method of the {@link CompletionListener}.
 * <p>
 * To support a Java Future-based approach to synchronously wait for a Cache
 * operation to complete, use a {@link CompletionListenerFuture}.
 * <p>
 * A CompletionListener will use an implementation specific thread for the call.
 *
 * @author Brian Oliver
 * @since 1.0
 * @see CompletionListenerFuture
 */
public interface CompletionListener {

  /**
   * Notifies the application that the operation completed successfully.
   */
  void onCompletion();

  /**
   * Notifies the application that the operation failed.
   *
   * @param e the Exception that occurred
   */
  void onException(Exception e);
}
