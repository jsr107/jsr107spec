/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * Indicates the lifecycle status of a resource.
 *
 * @author Greg Luck
 * @since 1.0
 */
public enum Status {

    /**
     * The resource has been created but not yet initialised. It cannot be used.
     */
    UNINITIALISED,

    /**
     * The resource has been started and is ready for service.
     */
    STARTED,

    /**
     * The resource has been stopped. It can no longer service requests.
     */
    STOPPED
}
