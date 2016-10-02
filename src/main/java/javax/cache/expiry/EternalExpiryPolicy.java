package javax.cache.expiry;

import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;

import static javax.cache.expiry.Duration.ETERNAL;

/**
 * An eternal {@link ExpiryPolicy} specifies that Cache Entries
 * won't expire.  This however doesn't mean they won't be evicted if an
 * underlying implementation needs to free-up resources where by it may
 * choose to evict entries that are not due to expire.
 *
 * @author Greg Luck
 * @author Brian Oliver
 * @since 1.0
 * @see ExpiryPolicy
 */
public final class EternalExpiryPolicy implements ExpiryPolicy, Serializable {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305101603L;

  /**
   * Obtains a {@link Factory} for an Eternal {@link ExpiryPolicy}.

   * @return a {@link Factory} for an Eternal {@link ExpiryPolicy}.
   */
  public static Factory<ExpiryPolicy> factoryOf() {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy>(
        new EternalExpiryPolicy());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForCreation() {
    return ETERNAL;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForAccess() {
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForUpdate() {
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
