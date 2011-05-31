/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * Indicates the status in it's lifecycle of a resource.
 *
 * @author Greg Luck
 */
public enum Status {

    /**
     * The resource has been created but not yet initialised. It cannot be used.
     */
    UNITIALISED,

    /**
     * The resource is in the process of starting
     */
    STARTING,

    /**
     * The resoure has been started and is ready for service.
     */
    STARTED,

    /**
     * The resource is in the process of stopping.
     */
    STOPPING,

    /**
     * The resource has been stopped. It can no longer service requests.
     */
    STOPPED
}
