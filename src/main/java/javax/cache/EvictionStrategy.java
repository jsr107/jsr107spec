package javax.cache;

import java.util.Map;

public interface EvictionStrategy<K,V> {
    public CacheEntry<K,V> createEntry(K key, V value, long ttl);
    public void discardEntry(CacheEntry<K,V> e);
    public void touchEntry(CacheEntry<K,V> entry);
    public void clear();
    public Map<K,V> evict(Cache<K,V> c);
}
