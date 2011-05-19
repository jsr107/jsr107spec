package javax.cache;

/**
 * A CacheManager is used for looking up Caches and controls their lifecycle.
 *
 * @author Greg Luck
 */
public interface CacheManager {


    /**
     * This method will return a TransactionManager which can only be used for
     * local transactions, not XA Transactions.
     *
     * @return the UserTransaction
     */
    javax.transaction.UserTransaction getUserTransaction();


    /**
     * Adds a {@link Cache} to the CacheManager.
     * <p/>
     *
     *
     * @param cache
     * @throws IllegalStateException if the cache is not {@link Status#STATUS_UNINITIALISED} before this method is called.
     * @throws CacheException        if there was an error adding the cache to the CacheManager
     */
    void addCache(Cache<?, ?> cache) throws IllegalStateException, CacheException;


    /**
     * Gets a named cache.
     * @param cacheName
     * @return the Cahce or null if it does exist
     */
    <K, V> Cache<K, V> getCache(String cacheName);


}
