package javax.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CacheOptions {

  public interface CacheV1<K,V> {
    void put(K key, V value);
    V get(Object k); // this is what Map does
    Map<K,V> getAll(Collection keys);
  }

  public interface CacheV2<K,V> {
    void put(K key, V value);
    V get(K k);
    Map<K,V> getAll(Collection<K> keys);
  }

  public interface CacheV3<K,V> {
    void put(K key, V value);
    V get(K k);
    Map<K,V> getAll(Collection<? extends K> keys);
  }

  public static class CacheV1Impl<K,V> implements CacheV1<K,V> {
    private final HashMap<K,V> map = new HashMap<K,V>();

    public void put(K key, V value) {
      map.put(key, value);
    }

    public V get(Object k) {
      return map.get(k);
    }

    public Map<K, V> getAll(Collection keys) {
      HashMap<K,V> ret = new HashMap<K,V>();
      for (Object key: keys) {
        V value = map.get(key);
        ret.put((K) key, value);
      }
      return ret;
    }
  }
}
