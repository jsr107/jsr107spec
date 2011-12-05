/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.experimental;

import javax.cache.Cache;
import javax.cache.CacheConfiguration;
import javax.cache.CacheManager;
import javax.cache.Caching;

/**
 * @author ycosmado
 * @since 1.0
 */
public class Example {
    /**
     *
     * @param <K>
     * @param <V>
     */
    public <K, V> void testNew() {
        CacheManager cacheManager = Caching.getCacheManager("myManager");
        CacheBuilder<K, V> cacheBuilder =
                cacheManager
                        .<K, V>createConfigurationBuilderEXPERIMENTAL()
                        .setExpiry(CacheConfiguration.ExpiryType.ACCESSED, CacheConfiguration.Duration.ETERNAL)
                        .setStatisticsEnabled(true)
                        .build()
                        .createBuilderEXPERIMENTAL()
                        .register("myBuilder");
        assert cacheBuilder == cacheManager.getCacheBuilderEXPERIMENTAL();

        Cache<K, V> myCache1 = cacheBuilder.build("myCache1");
        Cache<K, V> myCache2 = cacheBuilder.build("myCache2");
    }
}
