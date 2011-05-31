/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * Cache resources may have non-trivial initialisation and disposal procedures.
 * As such it is unrealistic to expect them to be avaiable for service after
 * object creation.
 * <p/>
 * This interface defines a lifecycle for these resources and associates a {@link Status}
 * with each.
 * <p/>
 * The {@link Status} of a newly created resource is {@link Status#UNITIALISED}.
 * @author Greg Luck
 */
public interface Lifecycle {

    /**
     * Notifies providers to initialise themselves.
     * <p/>
     * This method is called during the resource's initialise method after it has changed it's
     * status to alive. Cache operations are legal in this method.
     *
     * At the completion of this method invocation {@link #getStatus()} must return {@link Status#STARTED}.
     *
     * @throws CacheException
     */
    void initialise() throws CacheException;

    /**
     * Providers may be doing all sorts of exotic things and need to be able to clean up on
     * stopAndDispose.
     * <p/>
     * Cache operations are illegal when this method is called. The cache itself is partly
     * disposed when this method is called.
     *
     * @throws CacheException
     */
    void stopAndDispose() throws CacheException;

    /**
     * Returns the cache status.
     *
     * @return one of {@link Status}
     */
    Status getStatus();

}
