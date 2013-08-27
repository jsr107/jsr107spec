package javax.cache.expiry;

import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;

import static javax.cache.expiry.Duration.ETERNAL;

/**
 * The eternal {@link ExpiryPolicy} specifies that Cache Entries
 * won't expire.  This however doesn't mean they won't be expired if an
 * underlying implementation needs to free-up resources where by it may
 * choose to expire entries that are not due to expire.
 *
 * @param <K> the type of cache keys
 */
public final class EternalExpiryPolicy<K> implements ExpiryPolicy<K>, Serializable {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305101603L;

  /**
   * Obtains a {@link Factory} for an Eternal {@link ExpiryPolicy}.
   *
   * @return a {@link Factory} for an Eternal {@link ExpiryPolicy}.
   */
  public static <K> Factory<ExpiryPolicy<K>> factoryOf() {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy<K>>(
        new EternalExpiryPolicy<K>());
  }

  /**
   * {@inheritDoc}
   * @param key
   */
  @Override
  public Duration getExpiryForCreatedEntry(K key) {
    return ETERNAL;
  }

  /**
   * {@inheritDoc}
   * @param key
   */
  @Override
  public Duration getExpiryForAccessedEntry(K key) {
    return null;
  }

  /**
   * {@inheritDoc}
   * @param key
   */
  @Override
  public Duration getExpiryForModifiedEntry(K key) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return EternalExpiryPolicy.class.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {
    return other instanceof EternalExpiryPolicy;
  }
}
