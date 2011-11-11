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
}
