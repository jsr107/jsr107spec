/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.CacheManager;

/**
 * Interface that should be implemented by Cache implementers.
 *
 * @see javax.cache.CacheManagerFactory
 *
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public interface ServiceFactory {

    /**
     * Create a CacheManager.
     *
     * @param name the name of this cache manager
     * @return a new cache manager.
     * @throws NullPointerException if name is null
     */
    CacheManager createCacheManager(String name);
}
