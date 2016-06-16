/**
 *  Copyright 2011-2013 Terracotta, Inc.
 *  Copyright 2011-2013 Oracle America Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package javax.cache.integration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A CompletionListenerFuture is a CompletionListener implementation that
 * supports being used as a Future.
 * <p>
 * For example:
 * <pre><code>
 * //create a completion future to use to wait for loadAll
 * CompletionListenerFuture future = new CompletionListenerFuture();
 * 
 * //load the values for the set of keys, replacing those that may already exist
 * //in the cache
 * cache.loadAll(keys, true, future);
 * 
 * //wait for the cache to load the keys
 * future.get();
 * </code></pre>
 * <p>
 * A CompletionListenerFuture may only be used once.  Attempts to use an instance
 * multiple times, as part of multiple asynchronous calls will result in an
 * {@link java.lang.IllegalStateException} being raised.
 *
 * @author Brian Oliver
 * @author Greg Luck
 * @author Jens Wilke
 * @since 1.0
 */
public class CompletionListenerFuture implements CompletionListener, Future<Void> {

  private final Object lock = new Object();
  private boolean isCompleted;
  private Exception exception;

  /**
   * Constructs a CompletionListenerFuture.
   */
  public CompletionListenerFuture() {
    this.isCompleted = false;
    this.exception = null;
  }

  /**
   * Notifies the application that the operation completed successfully.
   * @throws IllegalStateException if the instance is used more than once
   */
  @Override
  public void onCompletion() throws IllegalStateException {
    synchronized (lock) {
      if (isCompleted) {
        throw new IllegalStateException("Attempted to use a CompletionListenerFuture instance more than once");
      }
      markAsCompleted();
    }
  }

  /**
   * Notifies the application that the operation failed.
   *
   * @param e the Exception that occurred
   * @throws IllegalStateException if the instance is used more than once
   */
  @Override
  public void onException(Exception e) throws IllegalStateException {
    synchronized (lock) {
      if (isCompleted) {
        throw new IllegalStateException("Attempted to use a CompletionListenerFuture instance more than once");
      }
      exception = e;
      markAsCompleted();
    }
  }

  /**
   * Mark operation as completed and wakeup all listeners, called under lock.
   */
  private void markAsCompleted() {
    assert Thread.holdsLock(lock);
    isCompleted = true;
    lock.notifyAll();
  }

  /**
   * Cancelling is mot supported, always throws exception.
   *
   * @throws UnsupportedOperationException
   */
  @Override
  public boolean cancel(boolean b) {
    throw new UnsupportedOperationException("CompletionListenerFutures can't be cancelled");
  }

  /**
   * Cancelling is mot supported, always returns false
   *
   * @return always false.
   */
  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public boolean isDone() {
    synchronized (lock) {
      return isCompleted;
    }
  }

  /**
   * Waits if necessary for the operation to complete. Always returns {@code null}.
   *
   * @return always {@code null}
   * @throws ExecutionException if the computation threw an
   * exception. This wraps the exception received by {@link #onException
   * (Exception)}
   * @throws InterruptedException if the current thread was interrupted
   * while waiting
   */
  @Override
  public Void get() throws InterruptedException, ExecutionException {
    synchronized (lock) {
      while (!isCompleted) {
        lock.wait();
      }
      if (exception != null) {
        throw new ExecutionException(exception);
      }
    }
    return null;
  }

  /**
   * Waits if necessary for at most the given time for the operation
   * to complete.  Always returns {@code null}.
   *
   * @param timeout the maximum time to wait
   * @param unit the time unit of the timeout argument
   * @return always {@code null}
   * @throws ExecutionException if the computation threw an
   * exception. This wraps the exception received by {@link #onException
   * (Exception)}
   * @throws InterruptedException if the current thread was interrupted
   * while waiting
   * @throws TimeoutException if the wait timed out
   */
  @Override
  public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    long endTime = System.currentTimeMillis() + unit.toMillis(timeout);
    synchronized (lock) {
      while (!isCompleted) {
        long waitTime = endTime - System.currentTimeMillis();
        if (waitTime <= 0) {
          throw new TimeoutException();
        }
        lock.wait(waitTime);
      }
      if (exception != null) {
        throw new ExecutionException(exception);
      }
    }
    return null;
  }

}
