/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
