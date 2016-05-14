/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *  Copyright (c) 2011-2016 Terracotta, Inc.
 *  Copyright (c) 2011-2016 Oracle and/or its affiliates.
 *
 *  All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
 * @since 1.0
 */
public class CompletionListenerFuture implements CompletionListener, Future<Void> {

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
    synchronized (this) {
      if (isCompleted) {
        throw new IllegalStateException("Attempted to use a CompletionListenerFuture instance more than once");
      } else {
        isCompleted = true;
        notify();
      }
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
    synchronized (this) {
      if (isCompleted) {
        throw new IllegalStateException("Attempted to use a CompletionListenerFuture instance more than once");
      } else {
        isCompleted = true;
        exception = e;
        notify();
      }
    }
  }

  @Override
  public boolean cancel(boolean b) {
    throw new UnsupportedOperationException("CompletionListenerFutures can't be cancelled");
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public boolean isDone() {
    synchronized (this) {
      return isCompleted;
    }
  }

  /**
   * Waits if necessary for the computation to complete, and then
   * retrieves its result.
   *
   * @return the computed result
   * @throws java.util.concurrent.CancellationException if the computation was cancelled
   * @throws ExecutionException if the computation threw an
   * exception. This wraps the exception received by {@link #onException
   * (Exception)}
   * @throws InterruptedException if the current thread was interrupted
   * while waiting
   */
  @Override
  public Void get() throws InterruptedException, ExecutionException {
    synchronized (this) {
      while (!isCompleted) {
        wait();
      }

      if (exception == null) {
        return null;
      } else {
        throw new ExecutionException(exception);
      }
    }
  }

  /**
   * Waits if necessary for at most the given time for the computation
   * to complete, and then retrieves its result, if available.
   *
   * @param timeout the maximum time to wait
   * @param unit the time unit of the timeout argument
   * @return the computed result
   * @throws java.util.concurrent.CancellationException if the computation was cancelled
   * @throws ExecutionException if the computation threw an
   * exception. This wraps the exception received by {@link #onException
   * (Exception)}
   * @throws InterruptedException if the current thread was interrupted
   * while waiting
   * @throws TimeoutException if the wait timed out
   */
  @Override
  public Void get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
    synchronized (this) {
      if (!isCompleted) {
        unit.timedWait(this, timeout);
      }

      if (isCompleted) {
        if (exception == null) {
          return null;
        } else {
          throw new ExecutionException(exception);
        }
      } else {
        throw new TimeoutException();
      }
    }
  }
}
