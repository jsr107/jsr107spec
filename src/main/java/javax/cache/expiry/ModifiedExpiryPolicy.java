package javax.cache.expiry;

import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;

/**
 * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
 * of a Cache Entry based on the last time it was modified. Modified
 * includes created and updated.
 *
 * @param <K> the type of cache keys
 */
public final class ModifiedExpiryPolicy<K> implements ExpiryPolicy<K>, Serializable {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305101602L;

  /**
   * The {@link Duration} a Cache Entry should be available before it expires.
   */
  private Duration expiryDuration;

  /**
   * Constructs an {@link ModifiedExpiryPolicy} {@link ExpiryPolicy}.
   *
   * @param expiryDuration the {@link Duration} a Cache Entry should exist be
   *                       before it expires after being modified
   */
  public ModifiedExpiryPolicy(Duration expiryDuration) {
    this.expiryDuration = expiryDuration;
  }

  /**
   * Obtains a {@link Factory} for a Modified {@link ExpiryPolicy}.
   *
   * @return a {@link Factory} for a Modified {@link ExpiryPolicy}.
   */
  public static <K, V> Factory<ExpiryPolicy<? super K>> factoryOf(Duration duration) {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy<? super K>>(new ModifiedExpiryPolicy<K>(duration));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <L extends K> Duration getExpiryForCreatedEntry(L key) {
    //for newly created entries we use the specified expiry duration
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <L extends K> Duration getExpiryForAccessedEntry(L key) {
    //accessing a cache entry has no affect on the current expiry duration
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <L extends K> Duration getExpiryForModifiedEntry(L key) {
    //when a cache entry is modified, we return the specified expiry duration,
    //ignoring the current expiry duration
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
    if (!(obj instanceof ModifiedExpiryPolicy)) {
      return false;
    }
    ModifiedExpiryPolicy<?> other = (ModifiedExpiryPolicy<?>) obj;
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
