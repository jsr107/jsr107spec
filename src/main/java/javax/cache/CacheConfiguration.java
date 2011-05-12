package javax.cache;

/**
 * Created by IntelliJ IDEA.
 * User: gluck
 * Date: 12/05/11
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
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
     * Whether storeByValue (true) or storeByRefernce (false)
     * @return
     */
    boolean isStoreByValue();


}
