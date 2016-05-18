package javax.cache.processor;

import javax.cache.Cache;

/**
 * A mutable representation of a {@link javax.cache.Cache.Entry}.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 *
 * @author Greg Luck
 * @since 1.0
 */
public interface MutableEntry<K, V> extends Cache.Entry<K, V> {

  /**
   * Checks for the existence of the entry in the cache
   *
   * @return true if the entry exists
   */
  boolean exists();

  /**
   * Removes the entry from the Cache.
   * <p>
   * This has the same semantics as calling {@link Cache#remove}.
   */
  void remove();

  /**
   * Sets or replaces the value associated with the key.
   * <p>
   * If {@link #exists} is false and setValue is called
   * then a mapping is added to the cache visible once the EntryProcessor
   * completes. Moreover a second invocation of {@link #exists()}
   * will return true.
   *
   * @param value the value to update the entry with
   * @throws ClassCastException if the implementation supports and is
   *                            configured to perform runtime-type-checking,
   *                            and value type is incompatible with that
   *                            which has been configured for the
   *                            {@link Cache}
   */
  void setValue(V value);
}
