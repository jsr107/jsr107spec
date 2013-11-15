/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * This package contains the API for Entry Processors.
 * <p>
 * Implementations may optionally enforce security restrictions. In case of a
 * violation, a {@link java.lang.SecurityException} must be thrown.
 *
 * @author Greg Luck
 * @since 1.0
 */
package javax.cache.processor;
