/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * Information on how a cache is configured.
 *
 * A Cache may be constructed by {@link CacheManager} using a configuration instance.
 *
 * At runtime it is used by javax.cache to decide how to behave. For example the behaviour of put
 * will vary depending on whether the cache is write-through.
 *
 * Finally, a cache makes it's configuration visible via this interface.
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheConfiguration {

    /**
     * Whether the cache is a read-through cache. A CacheLoader should be configured for read through caches
     * which is called on a cache miss.
     * <p/>
     * Default value is false.
     *
     * @return true if the cache is read-through
     */
    boolean isReadThrough();

    /**
     * Sets whether the cache is a read-through cache.
     *
     * @param readThrough the value for readThrough
     * @throws IllegalStateException if the configuration can no longer be changed
     */
    void setReadThrough(boolean readThrough);

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     * <p/>
     * Default value is false.
     *
     * @return true if the cache is write-through
     */
    boolean isWriteThrough();

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     *
     * @param writeThrough set to true for a write-through cache
     */
    void setWriteThrough(boolean writeThrough);

    /**
     * Whether storeByValue (true) or storeByReference (false).
     * When true, both keys and values are stored by value.
     * <p/>
     * When false, both keys and values are stored by reference.
     * Caches stored by reference are capable of mutation by any threads holding
     * the reference. The effects are:
     * <ul>
     *     <li>if the key is mutated, then the key may not be retrievable or removable</li>
     *     <li>if the value is mutated, then any threads holding references will see the changes</li>
     * </ul>
     * Storage by reference only applies to local heap. If an entry is moved outside local heap it will
     * need to be transformed into a representation. Any mutations that occur after transformation will
     * not be reflected in the cache.
     * <p/>
     * Default value is true.
     *
     * @return true if the cache is store by value
     */
    boolean isStoreByValue();

    /**
     * Checks whether statistics collection is enabled in this cache.
     * <p/>
     * Default value is false.
     *
     * @return true if statistics collection is enabled
     */
    boolean isStatisticsEnabled();

    /**
     * Sets whether statistics gathering is enabled  on this cache.
     *
     * @param enableStatistics true to enable statistics, false to disable
     */
    void setStatisticsEnabled(boolean enableStatistics);

    /**
     * Checks whether transactions are enabled for this cache.
     * <p/>
     * Default value is false.
     *
     * @return true if statistics collection is enabled
     */
    boolean isTransactionEnabled();
}
