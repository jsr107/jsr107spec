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
     * @param cache
     * @throws IllegalStateException if the cache is not {@link Status#UNITIALISED} before this method is called.
     * @throws CacheException        if there was an error adding the cache to the CacheManager
     */
    void addCache(Cache<?, ?> cache) throws IllegalStateException, CacheException;

    /**
     * Checks whether a cache of type ehcache exists.
     * <p/>
     *
     * @param cacheName the cache name to check for
     * @return true if it exists
     * @throws IllegalStateException if the cache is not {@link Status#ALIVE}
     */
    boolean cacheExists(String cacheName) throws IllegalStateException;


    /**
     * Gets a named cache.
     *
     * @param cacheName
     * @return the Cahce or null if it does exist
     */
    <K, V> Cache<K, V> getCache(String cacheName);


    /**
     * Remove a cache from the CacheManager. The cache is disposed of.
     *
     * @param cacheName the cache name
     * @throws IllegalStateException if the cache is not {@link Status#ALIVE}
     */
    public void removeCache(String cacheName) throws IllegalStateException;

    /**
     * Shuts down the CacheManager.
     * <p/>
     * If the shutdown occurs on the singleton, then the singleton is removed, so that if a singleton access method is called, a new
     * singleton will be created.
     */
    public void shutdown();


}
