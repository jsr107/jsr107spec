package javax.cache.event;

import javax.cache.Cache;

/**
 * A filter which may be used to check {@link Cache.Entry}s and only passed to listeners on successful evaluation.
 * @param <K> the type of keys maintained by the associated cache
 * @param <V> the type of values maintained by the associated cache
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryFilter<K, V> {

    /**
     * Evaluates a cache entry
     *
     * @param entry The entry just mutated.
     * @return true if the test passes, otherwise false. The effect of returning true is that listener will be invoked
     * @throws CacheEntryListenerException if there is problem executing the listener
     */
    boolean evaluate(Cache.Entry<? extends K, ? extends V> entry) throws CacheEntryListenerException;
}
