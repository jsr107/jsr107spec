package net.sf.jsr107cache;

import java.util.Map;

public interface EvictionStrategy {
    public CacheEntry createEntry(Object key, Object value, long ttl);
    public void discardEntry(CacheEntry e);
    public void touchEntry(CacheEntry entry);
    public void clear();
    public Map evict(Cache c);
}
