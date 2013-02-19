/**
 *  Copyright 2011 Terracotta, Inc.
 *  Copyright 2011 Oracle America Incorporated
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

package javax.cache.event;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A CompletionListenerFuture is a CompletionListener implementation that
 * supports being used as a Future.
 * <p/>
 * A CompletionListenerFuture may only be used once.  Attempts to use an instance
 * multiple times, as part of multiple asynchronous calls will result in an
 * java.lang.IllegalStateException being raised.
 *
 * @author Brian Oliver
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

    @Override
    public void onCompletion() {
        synchronized (this) {
            if (isCompleted) {
                throw new IllegalStateException("Attempted to use a CompletionListenerFuture for more than one purpose (ie: two or more times)");
            } else {
                isCompleted = true;
                notify();
            }
        }
    }

    @Override
    public void onException(Exception e) {
        synchronized (this) {
            if (isCompleted) {
                throw new IllegalStateException("Attempted to use a CompletionListenerFuture for more than one purpose (ie: two or more times)");
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

    @Override
    public Void get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (!isCompleted) {
                wait();
            }

            if (exception == null) {
                return null;
            } else {
                throw new ExecutionException(exception);
            }
        }
    }

    @Override
    public Void get(long l, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
        synchronized (this) {
            if (!isCompleted) {
                timeUnit.timedWait(this, l);
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
