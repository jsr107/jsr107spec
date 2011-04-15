package javax.cache;

/**
 * Indicate the status in it's lifecycle of a {@link CacheManager} or {@link Cache}.
 * @author Greg Luck
 */
public enum Status {

     /**
     * The cache is uninitialised. It cannot be used.
     */
    UNITIALISED,

    /**
     * The cache is alive. It can be used.
     */
    ALIVE,

    /**
     * The cache is shudown. It cannot be used.
     */
    SHUTDOWN


}
