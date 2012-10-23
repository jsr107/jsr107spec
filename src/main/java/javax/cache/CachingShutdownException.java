/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import java.util.Collections;
import java.util.Map;

/**
 * Thrown to indicate an exception has occurred in an aggregate Caching shutdown
 * procedure.
 *
 * @since 1.0
 */
public class CachingShutdownException extends CacheException {

    private final Map<CacheManager, Exception> failures;

    /**
     * Constructs a new CachingShutdownException with the specified list of shutdown
     * failures.
     *
     * @param failures a map containing the thrown exceptions
     */
    public CachingShutdownException(Map<CacheManager, Exception> failures) {
        this.failures = Collections.unmodifiableMap(failures);
    }

    /**
     * Return the set of CacheManagers that failed during shutdown and the associated
     * thrown exceptions.
     *
     * @return the set of thrown exceptions
     */
    public Map<CacheManager, Exception> getFailures() {
        return failures;
    }

    /**
     * Returns a short description of this throwable.
     * The result is the concatenation of:
     * <ul>
     * <li> the {@linkplain Class#getName() name} of the class of this object
     * <li> ": " (a colon and a space)
     * <li> the result of invoking this object's {@link #getLocalizedMessage}
     * method
     * </ul>
     * If <tt>getLocalizedMessage</tt> returns <tt>null</tt>, then just
     * the class name is returned.
     *
     * @return a string representation of this throwable.
     */
    @Override
    public String toString() {
        StringBuilder failuresMessage = new StringBuilder("The following CacheManagers through these exceptions on close()");
        for (CacheManager cacheManager : failures.keySet()) {
            failuresMessage.append("\n").append("CacheManager ").append(cacheManager.getName()).append(": ").append(failures.get(cacheManager));
        }
        return failuresMessage.toString();
    }
}
