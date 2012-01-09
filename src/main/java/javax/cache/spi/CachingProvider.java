/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.CacheManagerFactory;
import javax.cache.OptionalFeature;

/**
 * Interface that should be implemented by a CacheManager factory provider.
 *
 * It is invoked by the {@link javax.cache.Caching} class to create
 * a {@link javax.cache.CacheManager}
 * <p/>
 * An implementation of this interface must have a public no-arg constructor.
 * <p/>
 * @see javax.cache.Caching
 *
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CachingProvider {
    /**
     * Returns the singleton CacheManagerFactory.
     * @return the CacheManagerFactory
     */
    CacheManagerFactory getCacheManagerFactory();

    /**
     * Indicates whether a optional feature is supported by this implementation
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    boolean isSupported(OptionalFeature optionalFeature);
}
