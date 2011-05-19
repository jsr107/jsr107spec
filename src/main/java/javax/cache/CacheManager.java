package javax.cache;

/**
 * A CacheManager is used for looking up Caches and controls their lifecycle.
 * @author Greg Luck
 */
public interface CacheManager {


    /**
     * This method will return a TransactionManager which can only be used for
     * local transactions, not XA Transactions.
     * @return the UserTransaction
     */
    javax.transaction.UserTransaction getUserTransaction();


    /**
     *
     * @param cacheName
     * @return
     */
    Cache getCache(String cacheName);


}
