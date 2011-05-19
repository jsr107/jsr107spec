package javax.cache;

/**
 * Interface describing various events that can happen as elements are added to
 * or removed from a cache.
 *
 *
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Greg Luck
 */
public interface CacheListener<K, V> {

    /**
     * Triggered when a cache mapping is created due to the cache loader being consulted
     */
    public void onLoad(K key);

    /**
     * Triggered when a cache mapping is created due to calling Cache.put()
     */
    public void onPut(K key);

    /**
     * Triggered when a cache mapping is removed due to eviction
     */
    public void onEvict(K key);

    /**
     * Triggered when a cache mapping is removed due to calling Cache.remove()
     */
    public void onRemove(K key);

    /**
     * Triggered when a cache mapping is removed due to calling Cache.removeAll()
     */
    public void onRemoveAll();
}
