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
 * This interface defines a lifecycle for these resources and associates a {@link CacheStatus}
 * with each.
 * <p/>
 * The {@link CacheStatus} of a newly created resource is {@link CacheStatus#UNINITIALISED}.
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheLifecycle {

    /**
     * Notifies providers to start themselves.
     * <p/>
     * This method is called during the resource's start method after it has changed it's
     * status to alive. Cache operations are legal in this method.
     *
     * At the completion of this method invocation {@link #getStatus()} must return {@link CacheStatus#STARTED}.
     *
     * @throws CacheException
     */
    void start() throws CacheException;

    /**
     * Providers may be doing all sorts of exotic things and need to be able to clean up on
     * stop.
     * <p/>
     * Cache operations are illegal after this method is called.
     * A {@link IllegalStateException} will be thrown if an operation is performed on CacheManager or any contained Cache while
     * they are stopping or are a stopped.
     * <p/>
     * Resources will change status to {@link CacheStatus#STOPPING} when this method is called. Once they are stopped they will change
     * status to {@link CacheStatus#STOPPED}.
     *
     * @throws CacheException
     */
    void stop() throws CacheException;

    /**
     * Returns the cache status.
     *
     * @return one of {@link CacheStatus}
     */
    CacheStatus getStatus();
}
