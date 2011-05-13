package javax.cache;

/**
 * This interface supports setter injection and also the fluent builder pattern.
 */
public interface CacheConfiguration {

    /**
     * Configuration accessors
     */

    /**
     * Whether the cache is a read-through cache. A CacheEntryFactory should be configured for read through caches
     * which is called on a cache miss.
     * @return
     */
    boolean isReadThrough();

    /**
     * Sets whether the cache is a read-through cache.
     * @param readThrough the value for readThrough
     * @throws IllegalStateException if the configuration can no longer be changed
     */
    void setReadThrough(boolean readThrough);



    /**
     * Blurbage about blurbage
     *
     *
     * Whether storeByValue (true) or storeByRefernce (false)
     * @return
     */
    boolean isStoreByValue();

    /**
     *
     *
     *
     * Sets whether the cache is store-by-value cache.
     * @param storeByValue the value for storeByValue
     * @throws IllegalStateException if the configuration can no longer be changed
     * @see #isStoreByValue()
     */
    void setStoreByValue(boolean storeByValue);



}
