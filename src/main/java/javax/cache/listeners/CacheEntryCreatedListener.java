package javax.cache.listeners;

import javax.cache.Cache;

/**
 * Invoked if a cache entry is created,
 * for example through a {@link Cache#put(Object, Object)} operation.
 * If an entry for the key existed prior to the operation it is not invoked.
 *
 * @author ycosmado
 * @since 1.0
 */
public interface CacheEntryCreatedListener<K,V> extends CacheEntryListener {
    // TODO: maybe method should be be handleEvent and or arg EventObject
    void onCreate(Cache.Entry<K,V> entry);
}
