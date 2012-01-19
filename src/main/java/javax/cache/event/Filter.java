/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.event;

/**
 * Run a test for a condition. Allows for pluggable filtering behavior.
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface Filter {
    /**
     * Apply the test to the object
     * @param o an object
     * @return true if the test passes, false otherwise.
     */
    boolean evaluate(Object o);
}
