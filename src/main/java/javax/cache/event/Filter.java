/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.event;

/**
 * Evaluates a {@link CacheEntryEvent} as to whether a listener should be invoked.
 * This allows for pluggable filtering behavior.
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public interface Filter {

    /**
     * Apply the test to the object
     * @param cacheEntryEvent the event to evaluate for inclusion
     * @return true if the listener should be invoked, false otherwise.
     */
    boolean evaluate(CacheEntryEvent cacheEntryEvent);
}
