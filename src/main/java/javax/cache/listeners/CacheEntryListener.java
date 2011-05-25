package javax.cache.listeners;

import java.util.EventListener;

/**
 * Tagging interface for Listeners
 *
 * TODO: for now, just implemented CRUD listeners
 * CacheListener<K,V> had the following:
 * Triggered when a cache mapping is created due to the cache loader being consulted
 *  public void onLoad(K key);
 *
 * Triggered when a cache mapping is created due to calling Cache.put()
 *  public void onPut(K key);
 *
 * Triggered when a cache mapping is removed due to eviction
 *  public void onEvict(K key);
 *
 * Triggered when a cache mapping is removed due to calling Cache.remove()
 *  public void onRemove(K key);
 *
 * Triggered when a cache mapping is removed due to calling Cache.removeAll()
 *  public void onRemoveAll();
 *
 * @author ycosmado
 * @since 1.0
 */
public interface CacheEntryListener extends EventListener {
}
