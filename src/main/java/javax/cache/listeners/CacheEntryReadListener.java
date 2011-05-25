package javax.cache.listeners;

import javax.cache.Cache;

/**
 * Invoked if a cache entry is read,
 * for example through a {@link Cache#get(Object)} call.
 *
 * @author ycosmado
 * @since 1.0
 */
public interface CacheEntryReadListener<K,V> extends CacheEntryListener {
    // TODO: maybe method should be be handleEvent and or arg EventObject
    void onRead(Cache.Entry<K,V> entry);
}
