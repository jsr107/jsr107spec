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
package javax.cache;


/**
 * Thrown to indicate an exception has occurred in the Cache.
 * <p>
 * This is the base class for all cache exceptions.
 *
 * @author Greg Luck
 * @since 1.0
 */
public class CacheException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new CacheException.
   *
   * @since 1.0
   */
  public CacheException() {
    super();
  }

  /**
   * Constructs a new CacheException with a message string.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   * @since 1.0
   */
  public CacheException(String message) {
    super(message);
  }

  /**
   * Constructs a CacheException with a message string, and
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
  public CacheException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new CacheException with the specified cause and a
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
  public CacheException(Throwable cause) {
    super(cause);
  }

}
