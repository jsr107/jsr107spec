package javax.cache;

/**
 * This interface supports setter injection and also the fluent builder pattern.
 *
 * @author Greg Luck
 */
public interface CacheConfiguration {

    /**
     * Whether the cache is a read-through cache. A CacheLoader should be configured for read through caches
     * which is called on a cache miss.
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
     *
     * @return true if the cache is write-through
     */
    boolean isWriteThrough();

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     *
     * @param writeThrough set to true for a write-through cache
     *
     */
    void setWriteThrough(boolean writeThrough);

    /**
     * Whether storeByValue (true) or storeByReference (false)
     *
     * @return true if the cache is store by value
     */
    boolean isStoreByValue();

    /**
     * Sets whether the cache is store-by-value cache.
     *
     * @param storeByValue the value for storeByValue
     * @throws IllegalStateException if the configuration can no longer be changed
     * @see #isStoreByValue()
     */
    void setStoreByValue(boolean storeByValue);




}
