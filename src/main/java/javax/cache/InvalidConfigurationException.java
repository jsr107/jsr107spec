/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * An exception to report invalid configuration settings.
 * <p/>
 * This indicates a configuration is invalid for the cache implementation.
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 *
 * todo Have a vote on when this should: if at all and compared with IllegalArgumentException
 */
public class InvalidConfigurationException extends CacheException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new InvalidConfigurationException with a message string.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public InvalidConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidConfigurationException.
     */
    public InvalidConfigurationException() {
        super();
    }
}
