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
 */package javax.cache.expiry;

import javax.cache.configuration.Factory;
import javax.cache.configuration.FactoryBuilder;
import java.io.Serializable;

/**
 * An {@link ExpiryPolicy} that defines the expiry {@link Duration}
 * of a Cache Entry based on when it was created. An update does not reset
 * the expiry time.
 *
 * @author Greg Luck
 * @author Brian Oliver
 * @since 1.0
 * @see ExpiryPolicy
 */
public final class CreatedExpiryPolicy implements ExpiryPolicy, Serializable {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201305291023L;

  /**
   * The {@link Duration} a Cache Entry should be available before it expires.
   */
  private Duration expiryDuration;

  /**
   * Constructs an {@link CreatedExpiryPolicy} {@link ExpiryPolicy}.
   *
   * @param expiryDuration the {@link Duration} a Cache Entry should exist be
   *                       before it expires after being modified
   */
  public CreatedExpiryPolicy(Duration expiryDuration) {
    this.expiryDuration = expiryDuration;
  }

  /**
   * Obtains a {@link Factory} for a Created {@link ExpiryPolicy}.
   * @param duration The expiry duration
   * @return a {@link Factory} for a Created {@link ExpiryPolicy}.
   */
  public static Factory<ExpiryPolicy> factoryOf(Duration duration) {
    return new FactoryBuilder.SingletonFactory<ExpiryPolicy>(new CreatedExpiryPolicy(duration));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForCreation() {
    //for newly created entries we use the specified expiry duration
    return expiryDuration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForAccess() {
    //accessing a cache entry has no affect on the current expiry duration
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Duration getExpiryForUpdate() {
    //updating a cache entry has no affect on the current expiry duration
    return null;
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
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(object instanceof CreatedExpiryPolicy)) {
      return false;
    }
    CreatedExpiryPolicy other = (CreatedExpiryPolicy) object;
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
