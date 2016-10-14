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
package javax.cache.configuration;

import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;

/**
 * A convenience class providing a mutable, serializable implementation of a
 * {@link CacheEntryListenerConfiguration}.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Brian Oliver
 * @since 1.0
 */
public class MutableCacheEntryListenerConfiguration<K, V>
    implements CacheEntryListenerConfiguration<K, V> {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201306200822L;

  /**
   * The {@link Factory} to be used to create the {@link CacheEntryListener}.
   */
  private Factory<CacheEntryListener<? super K, ? super V>> listenerFactory;

  /**
   * The {@link Factory} to be used to create the {@link CacheEntryEventFilter}.
   * (may be null if no filtering is required)
   */
  private Factory<CacheEntryEventFilter<? super K, ? super V>> filterFactory;

  /**
   * Is the old value required to be provide to the {@link CacheEntryListener}?
   */
  private boolean isOldValueRequired;

  /**
   * Should the {@link CacheEntryListener} be notified as part of an operation
   * or is asynchronous delivery acceptable?
   */
  private boolean isSynchronous;

  /**
   * Constructs a {@link MutableCacheEntryListenerConfiguration} based on
   * another {@link CacheEntryListenerConfiguration}.
   *
   * @param configuration the {@link CacheEntryListenerConfiguration}
   */
  public MutableCacheEntryListenerConfiguration(CacheEntryListenerConfiguration<K, V> configuration) {
    this.listenerFactory = configuration.getCacheEntryListenerFactory();
    this.filterFactory = configuration.getCacheEntryEventFilterFactory();
    this.isOldValueRequired = configuration.isOldValueRequired();
    this.isSynchronous = configuration.isSynchronous();
  }

  /**
   * Constructs a {@link MutableCacheEntryListenerConfiguration}.
   *
   * @param listenerFactory    the {@link CacheEntryListener} {@link Factory}
   * @param filterFactory      the optional {@link CacheEntryEventFilter} {@link Factory}
   * @param isOldValueRequired if the old value is required for events with this listenerFactory
   * @param isSynchronous      if the listenerFactory should block the thread causing the event
   */
  public MutableCacheEntryListenerConfiguration(Factory<? extends CacheEntryListener<? super K, ? super V>> listenerFactory,
                                                Factory<? extends
                                                    CacheEntryEventFilter<? super K, ? super V>> filterFactory,
                                                boolean isOldValueRequired,
                                                boolean isSynchronous) {
    this.listenerFactory = (Factory<CacheEntryListener<? super K, ? super V>>) listenerFactory;
    this.filterFactory = (Factory<CacheEntryEventFilter<? super K, ? super V>>) filterFactory;
    this.isOldValueRequired = isOldValueRequired;
    this.isSynchronous = isSynchronous;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory() {
    return listenerFactory;
  }

  /**
   * Sets the {@link Factory} to be used to create a {@link CacheEntryListener}.
   *
   * @param listenerFactory the {@link Factory}
   * @return the {@link MutableCacheEntryListenerConfiguration} to permit
   *         fluent-style method calls
   */
  public MutableCacheEntryListenerConfiguration<K, V> setCacheEntryListenerFactory(
      Factory<? extends CacheEntryListener<? super K, ? super V>> listenerFactory) {
    this.listenerFactory = (Factory<CacheEntryListener<? super K, ? super V>>) listenerFactory;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Factory<CacheEntryEventFilter<? super K, ? super V>> getCacheEntryEventFilterFactory() {
    return filterFactory;
  }

  /**
   * Sets the {@link Factory} to be used to create a {@link CacheEntryEventFilter}.
   *
   * @param filterFactory the {@link Factory}, or <code>null</code> if event
   *                      filtering is not requried
   * @return the {@link MutableCacheEntryListenerConfiguration} to permit
   *         fluent-style method calls
   */
  public MutableCacheEntryListenerConfiguration<K, V> setCacheEntryEventFilterFactory(
      Factory<? extends CacheEntryEventFilter<? super K, ? super V>> filterFactory) {
    this.filterFactory = (Factory<CacheEntryEventFilter<? super K, ? super V>>) filterFactory;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isOldValueRequired() {
    return isOldValueRequired;
  }

  /**
   * Sets if the old value should be provided to the {@link CacheEntryListener}.
   *
   * @param isOldValueRequired <code>true</code> if the old value is required
   * @return the {@link MutableCacheEntryListenerConfiguration} to permit
   *         fluent-style method calls
   */
  public MutableCacheEntryListenerConfiguration<K, V> setOldValueRequired(
      boolean isOldValueRequired) {
    this.isOldValueRequired = isOldValueRequired;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSynchronous() {
    return isSynchronous;
  }

  /**
   * Sets if the thread that causes an event should be blocked
   * (not return from the operation causing the event) until the
   * {@link CacheEntryListener} has been notified.
   *
   * @param isSynchronous <code>true</code> means block until notified
   * @return the {@link MutableCacheEntryListenerConfiguration} to permit
   *         fluent-style method calls
   */
  public MutableCacheEntryListenerConfiguration<K, V> setSynchronous(
      boolean isSynchronous) {
    this.isSynchronous = isSynchronous;
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((filterFactory == null) ? 0 : filterFactory.hashCode());
    result = prime * result + (isOldValueRequired ? 1231 : 1237);
    result = prime * result + (isSynchronous ? 1231 : 1237);
    result = prime * result
        + ((listenerFactory == null) ? 0 : listenerFactory.hashCode());
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
    if (!(object instanceof MutableCacheEntryListenerConfiguration)) {
      return false;
    }
    MutableCacheEntryListenerConfiguration<?, ?> other = (MutableCacheEntryListenerConfiguration<?, ?>) object;
    if (filterFactory == null) {
      if (other.filterFactory != null) {
        return false;
      }
    } else if (!filterFactory.equals(other.filterFactory)) {
      return false;
    }
    if (isOldValueRequired != other.isOldValueRequired) {
      return false;
    }
    if (isSynchronous != other.isSynchronous) {
      return false;
    }
    if (listenerFactory == null) {
      if (other.listenerFactory != null) {
        return false;
      }
    } else if (!listenerFactory.equals(other.listenerFactory)) {
      return false;
    }
    return true;
  }
}
