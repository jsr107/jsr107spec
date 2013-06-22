package javax.cache.expiry;

import javax.cache.Cache;
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
 * @param <V> the type of cache values
 */
public final class EternalExpiryPolicy<K, V> implements ExpiryPolicy<K, V>, Serializable {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305101603L;

  /**
   * Obtains a {@link javax.cache.configuration.Factory} for an Eternal {@link ExpiryPolicy}.
   *
   * @return a {@link javax.cache.configuration.Factory} for an Eternal {@link ExpiryPolicy}.
   */
  public static <K, V> Factory<ExpiryPolicy<? super K, ? super V>> factoryOf() {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy<? super K, ? super V>>(new EternalExpiryPolicy<K, V>());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForCreatedEntry(Cache.Entry<? extends K, ? extends V> entry) {
    return ETERNAL;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForAccessedEntry(Cache.Entry<? extends K, ? extends V> entry) {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForModifiedEntry(Cache.Entry<? extends K, ? extends V> entry) {
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
