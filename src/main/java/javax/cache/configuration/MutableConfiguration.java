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

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.event.CacheEntryListener;
import javax.cache.expiry.EternalExpiryPolicy;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheWriter;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple mutable implementation of a {@link Configuration}.
 *
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * @author Brian Oliver
 * @author Greg Luck
 * @since 1.0
 */
public class MutableConfiguration<K, V> extends Configuration<K, V> {

  /**
   * The serialVersionUID required for {@link java.io.Serializable}.
   */
  public static final long serialVersionUID = 201306200821L;

  /**
   * The type of keys for {@link Cache}s configured with this
   * {@link Configuration}.
   */
  protected Class<K> keyType;

  /**
   * The type of values for {@link Cache}s configured with this
   * {@link Configuration}.
   */
  protected Class<V> valueType;

  /**
   * The {@link CacheEntryListenerConfiguration}s for the {@link Configuration}.
   */
  protected ArrayList<CacheEntryListenerConfiguration<K,
      V>> listenerConfigurations;

  /**
   * The {@link Factory} for the {@link CacheLoader}.
   */
  protected Factory<CacheLoader<K, V>> cacheLoaderFactory;

  /**
   * The {@link Factory} for the {@link CacheWriter}.
   */
  protected Factory<CacheWriter<? super K, ? super V>> cacheWriterFactory;

  /**
   * The {@link Factory} for the {@link ExpiryPolicy}.
   */
  protected Factory<ExpiryPolicy<K>> expiryPolicyFactory;

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
    this.keyType = (Class<K>)Object.class;
    this.valueType = (Class<V>)Object.class;
    this.listenerConfigurations = new
        ArrayList<CacheEntryListenerConfiguration<K, V>>();
    this.cacheLoaderFactory = null;
    this.cacheWriterFactory = null;
    this.expiryPolicyFactory = EternalExpiryPolicy.factoryOf();
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
   * Constructs a {@link MutableConfiguration} based on another
   * {@link Configuration}.
   *
   * @param configuration the {@link Configuration}
   */
  public MutableConfiguration(Configuration<K, V> configuration) {

    this.keyType = configuration.getKeyType();
    this.valueType = configuration.getValueType();

    listenerConfigurations = new
        ArrayList<CacheEntryListenerConfiguration<K, V>>();
    for (CacheEntryListenerConfiguration<K, V> definition : configuration
        .getCacheEntryListenerConfigurations()) {
      addCacheEntryListenerConfiguration(definition);
    }

    this.cacheLoaderFactory = configuration.getCacheLoaderFactory();
    this.cacheWriterFactory = configuration.getCacheWriterFactory();

    if (configuration.getExpiryPolicyFactory() == null) {
      this.expiryPolicyFactory = EternalExpiryPolicy.<K>factoryOf();
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
   * Sets the expected type of keys and values for a {@link Cache}
   * configured with this {@link Configuration}. Setting both to
   * <code>Object.class</code> means type-safety checks are not required.
   *
   * @param keyType   the expected key type
   * @param valueType the expected value type
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   * @throws NullPointerException should the key or value type be null
   */
  public MutableConfiguration<K, V> setTypes(Class<K> keyType, Class<V> valueType) {
    if (keyType == null || valueType == null) {
      throw new NullPointerException("keyType and/or valueType can't be null");
    } else {
      this.keyType = keyType;
      this.valueType = valueType;
      return this;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CacheEntryListenerConfiguration<K,
        V>> getCacheEntryListenerConfigurations() {
    return listenerConfigurations;
  }

  /**
   * Add a configuration for a {@link CacheEntryListener}.
   *
   * @param cacheEntryListenerConfiguration the
   *  {@link CacheEntryListenerConfiguration}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   * @throws IllegalArgumentException is the same CacheEntryListenerConfiguration
   * is used more than once
   */
  public MutableConfiguration<K, V> addCacheEntryListenerConfiguration(
      CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    if (cacheEntryListenerConfiguration == null) {
      throw new NullPointerException("CacheEntryListenerConfiguration can't be null");
    }

    boolean alreadyExists = false;
    for (CacheEntryListenerConfiguration<? super K, ? super V> c : listenerConfigurations) {
      if (c.equals(cacheEntryListenerConfiguration)) {
        alreadyExists = true;
      }
    }

    if (!alreadyExists) {
      this.listenerConfigurations.add(cacheEntryListenerConfiguration);
    } else {
      throw new IllegalArgumentException("A CacheEntryListenerConfiguration can " +
          "be registered only once");
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
   * Set the {@link CacheLoader} factory.
   *
   * @param factory the {@link CacheLoader} {@link Factory}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setCacheLoaderFactory(Factory<? extends
      CacheLoader<K, V>> factory) {
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
   * Set the {@link CacheWriter} factory.
   *
   * @param factory the {@link CacheWriter} {@link Factory}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setCacheWriterFactory(Factory<? extends
      CacheWriter<? super K, ? super V>> factory) {
    this.cacheWriterFactory = (Factory<CacheWriter<? super K, ? super V>>) factory;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  public Factory<ExpiryPolicy<K>> getExpiryPolicyFactory() {
    return this.expiryPolicyFactory;
  }

  /**
   * Set the {@link Factory} for the {@link ExpiryPolicy}.  If <code>null</code>
   * is specified the default {@link ExpiryPolicy} is used.
   *
   * @param factory the {@link ExpiryPolicy} {@link Factory}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setExpiryPolicyFactory(Factory<? extends
      ExpiryPolicy<? super K>> factory) {
    if (factory == null) {
      this.expiryPolicyFactory = EternalExpiryPolicy.<K>factoryOf();
    } else {
      this.expiryPolicyFactory = (Factory<ExpiryPolicy<K>>) factory;
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
   * Set the Transaction {@link IsolationLevel} and {@link Mode},
   * which also sets {@link #isTransactionsEnabled()} to true.
   *
   * @param level the {@link IsolationLevel}
   * @param mode  the {@link Mode}
   * @return the {@link MutableConfiguration} to permit fluent-style method calls
   */
  public MutableConfiguration<K, V> setTransactions(IsolationLevel level,
                                                    Mode mode) {
    this.txnIsolationLevel = level;
    this.txnMode = mode;
    this.isTransactionsEnabled = true;
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
   * <p/>
   * It is an invalid configuration to set this to true without specifying a
   * {@link CacheLoader} {@link Factory}.
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
   * <p/>
   * It is an invalid configuration to set this to true without specifying a
   * {@link CacheWriter} {@link Factory}.
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
   * Set if a configured cache should use store-by-value or store-by-reference
   * semantics.
   *
   * @param isStoreByValue <code>true</code> if store-by-value is required,
   *                       <code>false</code> for store-by-reference
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
   * Statistics may be enabled or disabled at runtime via
   * {@link CacheManager#enableStatistics(String, boolean)}.
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
   * Management may be enabled or disabled at runtime via
   * {@link CacheManager#enableManagement(String, boolean)}.
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
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + keyType.hashCode();
    result = prime * result + valueType.hashCode();
    result = prime
        * result
        + ((listenerConfigurations == null) ? 0 : listenerConfigurations
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

    if (!keyType.equals(other.keyType)) {
      return false;
    }
    if (!valueType.equals(other.valueType)) {
      return false;
    }
    if (listenerConfigurations == null) {
      if (other.listenerConfigurations != null) {
        return false;
      }
    } else if (!listenerConfigurations.equals(other
        .listenerConfigurations)) {
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
}
