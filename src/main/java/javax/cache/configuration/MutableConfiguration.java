/**
 *  Copyright 2011 Terracotta, Inc.
 *  Copyright 2011 Oracle America Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package javax.cache.configuration;

import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import javax.cache.event.CacheEntryListenerDefinition;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.util.ArrayList;

/**
 * A simple mutable implementation of a {@link Configuration}.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Brian Oliver
 * @since 1.0
 */
public class MutableConfiguration<K, V> implements Configuration<K, V> {

  /**
   * The type of keys for {@link javax.cache.Cache}s configured with this
   * {@link Configuration}.
   */
  protected Class<K> keyType;

  /**
   * The type of values for {@link javax.cache.Cache}s configured with this
   * {@link Configuration}.
   */
  protected Class<V> valueType;

  /**
   * The {@link javax.cache.event.CacheEntryListenerDefinition}s for the {@link Configuration}.
   */
  protected ArrayList<CacheEntryListenerDefinition<K, V>> cacheEntryListenerDefinitions;

  /**
   * The {@link Factory} for the {@link javax.cache.integration.CacheLoader}.
   */
  protected Factory<CacheLoader<K, V>> cacheLoaderFactory;

  /**
   * The {@link Factory} for the {@link javax.cache.integration.CacheWriter}.
   */
  protected Factory<CacheWriter<? super K, ? super V>> cacheWriterFactory;

  /**
   * The {@link Factory} for the {@link javax.cache.expiry.ExpiryPolicy}.
   */
  protected Factory<ExpiryPolicy<? super K, ? super V>> expiryPolicyFactory;

  /**
   * A flag indicating if "read-through" mode is required.
   */
  protected boolean isReadThrough;

  /**
   * A flag indicating if "write-through" mode is required.
   */
  protected boolean isWriteThrough;

  /**
   * A flag indicating if statistics gathering is enabled.
   */
  protected boolean isStatisticsEnabled;

  /**
   * A flag indicating if the cache will be store-by-value or store-by-reference.
   */
  protected boolean isStoreByValue;

  /**
   * A flag indicating if the cache will use transactions.
   */
  protected boolean isTransactionsEnabled;

  /**
   * The transaction {@link IsolationLevel}.
   */
  protected IsolationLevel txnIsolationLevel;

  /**
   * The transaction {@link Mode}.
   */
  protected Mode txnMode;

  /**
   * Whether management is enabled
   */
  protected boolean isManagementEnabled;

  /**
   * Constructs a default {@link MutableConfiguration}.
   */
  public MutableConfiguration() {
    this.keyType = null;
    this.valueType = null;
    this.cacheEntryListenerDefinitions = new ArrayList<CacheEntryListenerDefinition<K, V>>();
    this.cacheLoaderFactory = null;
    this.cacheWriterFactory = null;
    this.expiryPolicyFactory = EternalExpiryPolicy.<K, V>getFactory();
    this.isReadThrough = false;
    this.isWriteThrough = false;
    this.isStatisticsEnabled = false;
    this.isStoreByValue = true;
    this.isManagementEnabled = false;
    this.isTransactionsEnabled = false;
    this.txnIsolationLevel = IsolationLevel.NONE;
    this.txnMode = Mode.NONE;
  }

  /**
   * A copy-constructor for a {@link MutableConfiguration}.
   *
   * @param configuration the {@link Configuration} from which to copy
   */
  public MutableConfiguration(Configuration<K, V> configuration) {

    this.keyType = configuration.getKeyType();
    this.valueType = configuration.getValueType();

    this.cacheEntryListenerDefinitions = new ArrayList<CacheEntryListenerDefinition<K, V>>();

    for (CacheEntryListenerDefinition<K, V> r : configuration.getCacheEntryListenerDefinitions()) {
      SimpleCacheEntryListenerDefinition<K, V> registration =
          new SimpleCacheEntryListenerDefinition<K, V>(r.getCacheEntryListenerFactory(),
              r.getCacheEntryFilterFactory(),
              r.isOldValueRequired(),
              r.isSynchronous());

      this.cacheEntryListenerDefinitions.add(registration);
    }

    this.cacheLoaderFactory = configuration.getCacheLoaderFactory();
    this.cacheWriterFactory = configuration.getCacheWriterFactory();

    if (configuration.getExpiryPolicyFactory() == null) {
      this.expiryPolicyFactory = EternalExpiryPolicy.<K, V>getFactory();
    } else {
      this.expiryPolicyFactory = configuration.getExpiryPolicyFactory();
    }

    this.isReadThrough = configuration.isReadThrough();
    this.isWriteThrough = configuration.isWriteThrough();

    this.isStatisticsEnabled = configuration.isStatisticsEnabled();

    this.isStoreByValue = configuration.isStoreByValue();

    this.isManagementEnabled = configuration.isManagementEnabled();

    this.isTransactionsEnabled = configuration.isTransactionsEnabled();
    this.txnIsolationLevel = configuration.getTransactionIsolationLevel();
    this.txnMode = configuration.getTransactionMode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<K> getKeyType() {
    return keyType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<V> getValueType() {
    return valueType;
  }

  /**
   * Sets the expected type of keys and values for a {@link javax.cache.Cache} configured with
   * this {@link Configuration}. Setting both to <code>null</code> means type-safety
   * checks are not required.
   *
   * @param keyType   the expected key type
   * @param valueType the expected value type
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setTypes(Class<K> keyType, Class<V> valueType) {
    if ((keyType == null && valueType == null) ||
        (keyType != null && valueType != null)) {
      this.keyType = keyType;
      this.valueType = valueType;
      return this;
    } else {
      throw new IllegalArgumentException("Both keyType and valueType must be null or a type");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterable<CacheEntryListenerDefinition<K, V>> getCacheEntryListenerDefinitions() {
    return cacheEntryListenerDefinitions;
  }

  /**
   * Add a definition for a {@link CacheEntryListener}.  If a definition with
   * the same configuration is already defined, the registration request is
   * ignored.
   *
   * @param listenerFactory  the {@link CacheEntryListener} {@link Factory}
   * @param requireOldValue  whether the old value is supplied to {@link javax.cache.event.CacheEntryEvent}.
   * @param filterFactory    the optional {@link CacheEntryEventFilter} {@link Factory}
   * @param synchronous      whether the caller is blocked until the listenerFactory invocation completes.
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> registerCacheEntryListener(
      Factory<? extends CacheEntryListener<? super K, ? super V>> listenerFactory,
      boolean requireOldValue,
      Factory<? extends CacheEntryEventFilter<? super K, ? super V>> filterFactory,
      boolean synchronous) {

    if (listenerFactory == null) {
      throw new NullPointerException("CacheEntryListener Factory can't be null");
    }

    SimpleCacheEntryListenerDefinition<K, V> definition =
        new SimpleCacheEntryListenerDefinition<K, V>(
            (Factory<CacheEntryListener<? super K, ? super V>>)listenerFactory,
            (Factory<CacheEntryEventFilter<? super K, ? super V>>)filterFactory,
            requireOldValue,
            synchronous);

    boolean alreadyExists = false;
    for (CacheEntryListenerDefinition<? super K, ? super V> r : cacheEntryListenerDefinitions) {
      if (r.equals(definition)) {
        alreadyExists = true;
      }
    }

    if (!alreadyExists) {
      this.cacheEntryListenerDefinitions.add(definition);
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Factory<CacheLoader<K, V>> getCacheLoaderFactory() {
    return this.cacheLoaderFactory;
  }

  /**
   * Set the {@link CacheLoader}.
   *
   * @param factory the {@link CacheLoader} {@link Factory}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setCacheLoaderFactory(Factory<? extends CacheLoader<K, V>> factory) {
    this.cacheLoaderFactory = (Factory<CacheLoader<K, V>>) factory;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Factory<CacheWriter<? super K, ? super V>> getCacheWriterFactory() {
    return this.cacheWriterFactory;
  }

  /**
   * Set the {@link CacheWriter}.
   *
   * @param factory the {@link CacheWriter} {@link Factory}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setCacheWriterFactory(Factory<? extends CacheWriter<? super K, ? super V>> factory) {
    this.cacheWriterFactory = (Factory<CacheWriter<? super K, ? super V>>) factory;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public Factory<ExpiryPolicy<? super K, ? super V>> getExpiryPolicyFactory() {
    return this.expiryPolicyFactory;
  }

  /**
   * Set the {@link Factory} for the {@link ExpiryPolicy}.  If <code>null</code>
   * is specified the default {@link ExpiryPolicy} is used.
   *
   * @param factory the {@link ExpiryPolicy} {@link Factory}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setExpiryPolicyFactory(Factory<? extends ExpiryPolicy<? super K, ? super V>> factory) {
    if (factory == null) {
      this.expiryPolicyFactory = EternalExpiryPolicy.<K, V>getFactory();
    } else {
      this.expiryPolicyFactory = (Factory<ExpiryPolicy<? super K, ? super V>>) factory;
    }
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public IsolationLevel getTransactionIsolationLevel() {
    return this.txnIsolationLevel;
  }

  /**
   * Set the Transaction {@link IsolationLevel} and {@link Mode}.
   *
   * @param level the {@link IsolationLevel}
   * @param mode  the {@link Mode}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setTransactions(IsolationLevel level, Mode mode) {
    this.txnIsolationLevel = level;
    this.txnMode = mode;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Mode getTransactionMode() {
    return this.txnMode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isReadThrough() {
    return this.isReadThrough;
  }

  /**
   * Set if read-through caching should be used.
   *
   * @param isReadThrough <code>true</code> if read-through is required
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setReadThrough(boolean isReadThrough) {
    this.isReadThrough = isReadThrough;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isWriteThrough() {
    return this.isWriteThrough;
  }

  /**
   * Set if write-through caching should be used.
   *
   * @param isWriteThrough <code>true</code> if write-through is required
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setWriteThrough(boolean isWriteThrough) {
    this.isWriteThrough = isWriteThrough;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isStoreByValue() {
    return this.isStoreByValue;
  }

  /**
   * Set if a configured cache should use "store-by-value" or "store-by-reference"
   * semantics.
   *
   * @param isStoreByValue <code>true</code> if "store-by-value" is required,
   *                       <code>false</code> for "store-by-reference"
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setStoreByValue(boolean isStoreByValue) {
    this.isStoreByValue = isStoreByValue;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isStatisticsEnabled() {
    return this.isStatisticsEnabled;
  }

  /**
   * Sets whether statistics gathering is enabled on a cache.
   * <p/>
   * Statistics may be enabled or disabled at runtime via {@link javax.cache.CacheManager#enableStatistics(String, boolean)}.
   *
   * @param enabled true to enable statistics, false to disable.
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setStatisticsEnabled(boolean enabled) {
    this.isStatisticsEnabled = enabled;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isManagementEnabled() {
    return this.isManagementEnabled;
  }

  /**
   * Sets whether management is enabled on a cache.
   * <p/>
   * Management may be enabled or disabled at runtime via {@link javax.cache.CacheManager#enableManagement(String, boolean)}.
   *
   * @param enabled true to enable statistics, false to disable.
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setManagementEnabled(boolean enabled) {
    this.isManagementEnabled = enabled;
    return this;
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTransactionsEnabled() {
    return this.isTransactionsEnabled;
  }

  /**
   * Set if transactions should be enabled.
   *
   * @param isTransactionsEnabled <code>true</code> if transactions should be enabled
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setTransactionsEnabled(boolean isTransactionsEnabled) {
    this.isTransactionsEnabled = isTransactionsEnabled;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((keyType == null) ? 0 : keyType.hashCode());
    result = prime * result + ((valueType == null) ? 0 : valueType.hashCode());
    result = prime
        * result
        + ((cacheEntryListenerDefinitions == null) ? 0 : cacheEntryListenerDefinitions
        .hashCode());
    result = prime * result
        + ((cacheLoaderFactory == null) ? 0 : cacheLoaderFactory.hashCode());
    result = prime * result
        + ((cacheWriterFactory == null) ? 0 : cacheWriterFactory.hashCode());
    result = prime * result
        + ((expiryPolicyFactory == null) ? 0 : expiryPolicyFactory.hashCode());
    result = prime * result + (isReadThrough ? 1231 : 1237);
    result = prime * result + (isStatisticsEnabled ? 1231 : 1237);
    result = prime * result + (isStoreByValue ? 1231 : 1237);
    result = prime * result + (isWriteThrough ? 1231 : 1237);
    result = prime
        * result
        + ((txnIsolationLevel == null) ? 0 : txnIsolationLevel
        .hashCode());
    result = prime * result + ((txnMode == null) ? 0 : txnMode.hashCode());
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
    if (!(object instanceof MutableConfiguration)) {
      return false;
    }
    MutableConfiguration<?, ?> other = (MutableConfiguration<?, ?>) object;

    if ((keyType == null && other.keyType != null) ||
        (keyType != null && other.keyType == null)) {
      return false;
    } else if (keyType != null && other.keyType != null && !keyType.equals(other.keyType)) {
      return false;
    }
    if ((valueType == null && other.valueType != null) ||
        (valueType != null && other.valueType == null)) {
      return false;
    } else if (valueType != null && other.valueType != null && !valueType.equals(other.valueType)) {
      return false;
    }
    if (cacheEntryListenerDefinitions == null) {
      if (other.cacheEntryListenerDefinitions != null) {
        return false;
      }
    } else if (!cacheEntryListenerDefinitions.equals(other.cacheEntryListenerDefinitions)) {
      return false;
    }
    if (cacheLoaderFactory == null) {
      if (other.cacheLoaderFactory != null) {
        return false;
      }
    } else if (!cacheLoaderFactory.equals(other.cacheLoaderFactory)) {
      return false;
    }
    if (cacheWriterFactory == null) {
      if (other.cacheWriterFactory != null) {
        return false;
      }
    } else if (!cacheWriterFactory.equals(other.cacheWriterFactory)) {
      return false;
    }
    if (expiryPolicyFactory == null) {
      if (other.expiryPolicyFactory != null) {
        return false;
      }
    } else if (!expiryPolicyFactory.equals(other.expiryPolicyFactory)) {
      return false;
    }
    if (isReadThrough != other.isReadThrough) {
      return false;
    }
    if (isStatisticsEnabled != other.isStatisticsEnabled) {
      return false;
    }
    if (isStoreByValue != other.isStoreByValue) {
      return false;
    }
    if (isWriteThrough != other.isWriteThrough) {
      return false;
    }
    if (isTransactionsEnabled != other.isTransactionsEnabled) {
      return false;
    }
    if (txnIsolationLevel != other.txnIsolationLevel) {
      return false;
    }
    if (txnMode != other.txnMode) {
      return false;
    }
    return true;
  }

  /**
   * An implementation of a {@link javax.cache.event.CacheEntryListenerDefinition}.
   *
   * @param <K> the type of the keys
   * @param <V> the type of the values
   */
  static class SimpleCacheEntryListenerDefinition<K, V> implements CacheEntryListenerDefinition<K, V> {

    private Factory<CacheEntryListener<? super K, ? super V>> listenerFactory;
    private Factory<CacheEntryEventFilter<? super K, ? super V>> filterFactory;
    private boolean isOldValueRequired;
    private boolean isSynchronous;

    /**
     * Constructs an {@link javax.cache.configuration.MutableConfiguration.SimpleCacheEntryListenerDefinition}.
     *
     * @param listenerFactory    the {@link CacheEntryListener} {@link Factory}
     * @param filterFactory      the optional {@link CacheEntryEventFilter} {@link Factory}
     * @param isOldValueRequired if the old value is required for events with this listenerFactory
     * @param isSynchronous      if the listenerFactory should block the thread causing the event
     */
    public SimpleCacheEntryListenerDefinition(Factory<CacheEntryListener<? super K, ? super V>> listenerFactory,
                                              Factory<CacheEntryEventFilter<? super K, ? super V>> filterFactory,
                                              boolean isOldValueRequired,
                                              boolean isSynchronous) {
      this.listenerFactory = listenerFactory;
      this.filterFactory = filterFactory;
      this.isOldValueRequired = isOldValueRequired;
      this.isSynchronous = isSynchronous;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Factory<CacheEntryEventFilter<? super K, ? super V>> getCacheEntryFilterFactory() {
      return filterFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory() {
      return listenerFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOldValueRequired() {
      return isOldValueRequired;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSynchronous() {
      return isSynchronous;
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
      if (!(object instanceof SimpleCacheEntryListenerDefinition)) {
        return false;
      }
      SimpleCacheEntryListenerDefinition<?, ?> other = (SimpleCacheEntryListenerDefinition<?, ?>) object;
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

}
