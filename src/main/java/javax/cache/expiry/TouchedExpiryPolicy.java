package javax.cache.expiry;

import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;

/**
 * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
 * of a Cache Entry based on when it was last touched. A touch includes
 * creation, update or access.
 *
 * @param <K> the type of cache keys
 */
public final class TouchedExpiryPolicy<K> implements ExpiryPolicy<K>, Serializable {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305291023L;

  /**
   * The {@link Duration} a Cache Entry should be available before it expires.
   */
  private Duration expiryDuration;

  /**
   * Constructs an {@link TouchedExpiryPolicy} {@link ExpiryPolicy}.
   *
   * @param expiryDuration the {@link Duration} a Cache Entry should exist be
   *                       before it expires after being modified
   */
  public TouchedExpiryPolicy(Duration expiryDuration) {
    this.expiryDuration = expiryDuration;
  }

  /**
   * Obtains a {@link Factory} for a Touched {@link ExpiryPolicy}.
   *
   * @return a {@link Factory} for a Touched {@link ExpiryPolicy}.
   */
  public static <K, V> Factory<ExpiryPolicy<K>> factoryOf(Duration duration) {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy<K>>(new TouchedExpiryPolicy<K>(duration));
  }

  /**
   * {@inheritDoc}
   * @param key
   */
  @Override
  public Duration getExpiryForCreatedEntry(K key) {
    //for newly created entries we use the specified expiry duration.
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   * @param key
   */
  @Override
  public Duration getExpiryForAccessedEntry(K key) {
    //accessing a cache entry resets the duration.
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   * @param key
   */
  @Override
  public Duration getExpiryForModifiedEntry(K key) {
    //accessing a cache entry resets the duration.
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((expiryDuration == null) ? 0 : expiryDuration.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof TouchedExpiryPolicy)) {
      return false;
    }
    TouchedExpiryPolicy<?> other = (TouchedExpiryPolicy<?>) obj;
    if (expiryDuration == null) {
      if (other.expiryDuration != null) {
        return false;
      }
    } else if (!expiryDuration.equals(other.expiryDuration)) {
      return false;
    }
    return true;
  }
}
