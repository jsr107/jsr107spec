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
 * The {@link Status} of a newly created resource is {@link Status#UNINITIALISED}.
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheLifecycle {

    /**
     * Notifies providers to start themselves.
     * <p/>
     * This method is called during the resource's start method after it has changed its
     * status to alive. Cache operations are legal in this method.
     *
     * At the completion of this method invocation {@link #getStatus()} must return {@link Status#STARTED}.
     *
     * @throws CacheException if ????? TODO describe when
     */
    void start();

    /**
     * Providers may be doing all sorts of exotic things and need to be able to clean up on
     * stop.
     * <p/>
     * Cache operations are illegal after this method is called.
     * A {@link IllegalStateException} will be
     * <p/>
     * Resources will change status to {@link Status#STOPPED} when this method completes.
     * <p/>
     * Stop must free any JVM resources used.
     *
     * @throws CacheException if ????? TODO describe when
     * @throws IllegalStateException thrown if an operation is performed on a cache unless it is started.
     */
    void stop();

    /**
     * Returns the cache status.
     * <p/>
     * This method blocks while the state is changing
     *
     * @return one of {@link Status}
     */
    Status getStatus();
}
