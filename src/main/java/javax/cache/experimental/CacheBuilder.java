/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.experimental;

import javax.cache.Cache;

/**
 * @author ycosmado
 * @since 1.0
 * @param <K> the key type
 * @param <V> the value type
 */
public interface CacheBuilder<K, V> {
    /**
     *
     * @param name
     * @return
     */
    Cache<K, V> build(String name);

    /**
     *
     * @param name
     * @return
     */
    CacheBuilder<K, V> register(String name);
}
