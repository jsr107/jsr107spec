package javax.cache.listeners;

import javax.cache.Cache;

/**
 * Invoked if a cache entry is deleted,
 * for example through a {@link Cache#remove(Object)} call.
 *
 * @author ycosmado
 * @since 1.0
 */
public interface CacheEntryDeletedListener<K,V> extends CacheEntryListener {
    // TODO: maybe method should be be handleEvent and or arg EventObject
    void onDelete(Cache.Entry<K,V> entry);
}
