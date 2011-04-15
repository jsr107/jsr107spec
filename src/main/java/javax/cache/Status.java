package javax.cache;

/**
 * Indicate the status in it's lifecycle of a {@link CacheManager} or {@link Cache}.
 * @author Greg Luck
 */
public enum Status {

     /**
     * The CacheManager or Cache is uninitialised. It cannot be used.
     */
    UNITIALISED,

    /**
     * The CacheManager or Cache is alive. It can be used.
     */
    ALIVE,

    /**
     * The CacheManager or Cache is shudown. It cannot be used.
     */
    SHUTDOWN
}
