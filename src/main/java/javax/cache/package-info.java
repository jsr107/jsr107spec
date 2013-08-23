/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * <p/>
 * This package contains the API for JCache.
 * <p/>
 * The entry point is the {@link Caching} class. {@link CacheManager} holds and controls
 * a collection of {@link Cache}s.
 * <p/>
 * A cache is an association of key to value.
 * <p/>
 * Implementations may optionally enforce security restrictions. In case of a
 * violation, a {@link SecurityException} must be thrown.
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
package javax.cache;
