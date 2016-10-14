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
package javax.cache.expiry;

/**
 * Defines functions to determine when cache entries will expire based on
 * creation, access and modification operations.
 * <p>
 * Each of the functions return a new {@link Duration} that specifies the
 * amount of time that must pass before a cache entry is considered expired.
 * {@link Duration} has constants defined for useful durations.
 *
 * @author Brian Oliver
 * @author Greg Luck
 * @since 1.0
 * @see Duration
 */
public interface ExpiryPolicy {

  /**
   * Gets the {@link Duration} before a newly created Cache.Entry is considered
   * expired.
   * <p>
   * This method is called by a caching implementation after a Cache.Entry is
   * created, but before a Cache.Entry is added to a cache, to determine the
   * {@link Duration} before an entry expires.  If a {@link Duration#ZERO}
   * is returned the new Cache.Entry is considered to be already expired and
   * will not be added to the Cache.
   * <p>
   * Should an exception occur while determining the Duration, an implementation
   * specific default {@link Duration} will be used.
   *
   * @return the new {@link Duration} before a created entry expires
   */
  Duration getExpiryForCreation();

  /**
   * Gets the {@link Duration} before an accessed Cache.Entry is
   * considered expired.
   * <p>
   * This method is called by a caching implementation after a Cache.Entry is
   * accessed to determine the {@link Duration} before an entry expires.  If a
   * {@link Duration#ZERO} is returned a Cache.Entry will be
   * considered immediately expired.  Returning <code>null</code> will result
   * in no change to the previously understood expiry {@link Duration}.
   * <p>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @return the new {@link Duration} before an accessed entry expires
   */
  Duration  getExpiryForAccess();

  /**
   * Gets the {@link Duration} before an updated Cache.Entry is considered
   * expired.
   * <p>
   * This method is called by the caching implementation after a Cache.Entry is
   * updated to determine the {@link Duration} before the updated entry expires.
   * If a {@link Duration#ZERO} is returned a Cache.Entry is considered
   * immediately expired.  Returning <code>null</code> will result in no change
   * to the previously understood expiry {@link Duration}.
   * <p>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @return the new {@link Duration} before an updated entry expires
   */
  Duration getExpiryForUpdate();
}
