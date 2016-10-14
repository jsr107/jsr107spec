/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 * A Cache provider will use an implementation specific thread to call methods
 * on this interface.
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
