package net.sf.jsr107cache;

/** Interface describing various events that can happen as elements are added to
 *  or removed from a cache
 */
public interface CacheListener {
    /** Triggered when a cache mapping is created due to the cache loader being consulted */
    public void onLoad(Object key);

    /** Triggered when a cache mapping is created due to calling Cache.put() */
    public void onPut(Object key);

    /** Triggered when a cache mapping is removed due to eviction */
    public void onEvict(Object key);

    /** Triggered when a cache mapping is removed due to calling Cache.remove() */
    public void onRemove(Object key);

    public void onClear();
}
