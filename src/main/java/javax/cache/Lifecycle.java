package javax.cache;

/**
 * Cache resources may have non-trivial initialisation and disposal procedures.
 * As such it is unrealistic to expect them to be avaiable for service after
 * object creation.
 * <p/>
 * This interface defines a lifecycle for these resources and associates a {@link Status}
 * with each.
 * <p/>
 * The {@link Status} of a newly created resource is {@link Status#UNITIALISED}.
 *
 * TODO up to here
 *
 * @author Greg Luck
 */
public interface Lifecycle {

    /**
     * Notifies providers to initialise themselves.
     * <p/>
     * This method is called during the resource's initialise method after it has changed it's
     * status to alive. Cache operations are legal in this method.
     *
     * @throws CacheException
     */
    void init() throws CacheException;

    /**
     * Providers may be doing all sorts of exotic things and need to be able to clean up on
     * dispose.
     * <p/>
     * Cache operations are illegal when this method is called. The cache itself is partly
     * disposed when this method is called.
     *
     * @throws CacheException
     */
    void dispose() throws CacheException;

    /**
     * Returns the cache status.
     * @return one of {@link Status}
     */
    Status getStatus();

}
