package javax.cache;

import java.util.Map;

/**
 * CacheEntry
 *
 * @author Brian Goetz
 */
public interface CacheEntry<K,V> extends Map.Entry<K,V> {

    int getHits();

    long getLastAccessTime();
    long getLastUpdateTime();
    long getCreationTime();
    long getExpirationTime();

    /**
     * Returns a version counter.
     * An implementation may use timestamps for this or an incrementing
     * number. Timestamps usually have issues with granularity and are harder
     * to use across clusteres or threads, so an incrementing counter is often safer.
     */
    long getVersion();

    boolean isValid();
    long getCost();
}
