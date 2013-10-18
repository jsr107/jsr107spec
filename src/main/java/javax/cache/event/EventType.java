/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
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
