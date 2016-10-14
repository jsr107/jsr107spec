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
package javax.cache.event;

/**
 * The type of event received by the listener.
 *
 * @author Greg Luck
 * @since 1.0
 */
public enum EventType {

  /**
   * An event type indicating that the cache entry was created.
   */
  CREATED,

  /**
   * An event type indicating that the cache entry was updated. i.e. a previous
   * mapping existed
   */
  UPDATED,


  /**
   * An event type indicating that the cache entry was removed.
   */
  REMOVED,


  /**
   * An event type indicating that the cache entry has expired.
   */
  EXPIRED

}
