package javax.cache.listeners;

import javax.cache.Cache;

/**
 * Invoked if an existing cache entry is updated,
 * for example through a {@link Cache#put(Object, Object)} operation.
 * It is not invoked by a {@link Cache#remove(Object)} operation.
 *
 * @author ycosmado
 * @since 1.0
 */
public interface CacheEntryUpdatedListener<K,V> extends CacheEntryListener {
    // TODO: maybe method should be be handleEvent and or arg EventObject
    void onUpdate(Cache.Entry<K,V> entry);
}
