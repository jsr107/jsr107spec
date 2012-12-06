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

package javax.cache;

import java.util.ArrayList;

import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import javax.cache.event.CacheEntryListenerRegistration;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;

/**
 * A simple mutable implementation of a {@link CacheConfiguration}.
 * 
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * 
 * @author Brian Oliver
 * @since 1.0
 */
public class SimpleCacheConfiguration<K, V> implements CacheConfiguration<K, V> {

    /**
     * The {@link CacheEntryListenerRegistration}s for the {@link CacheConfiguration}.
     */
    protected ArrayList<CacheEntryListenerRegistration<? super K, ? super V>> cacheEntryListenerRegistrations;

    /**
     * The {@link CacheLoader} for the built {@link CacheConfiguration}.
     */
    protected CacheLoader<K, ? extends V> cacheLoader;
    
    /**
     * The {@link CacheWriter} for the built {@link CacheConfiguration}.
     */
    protected CacheWriter<? super K, ? super V> cacheWriter;
    
    /**
     * The {@link CacheEntryExpiryPolicy} for the {@link CacheConfiguration}.
     */
    protected CacheEntryExpiryPolicy<? super K, ? super V> cacheEntryExpiryPolicy;
    
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
     * Constructs an {@link SimpleCacheConfiguration} with the standard
     * default values.
     */
    public SimpleCacheConfiguration() {
        this.cacheEntryListenerRegistrations = new ArrayList<CacheEntryListenerRegistration<? super K, ? super V>>();
        this.cacheLoader = null;
        this.cacheWriter = null;
        this.cacheEntryExpiryPolicy = new CacheEntryExpiryPolicy.Default<K, V>();
        this.isReadThrough = false;
        this.isWriteThrough = false;
        this.isStatisticsEnabled = false;
        this.isStoreByValue = true;
        this.isTransactionsEnabled = false;
        this.txnIsolationLevel = IsolationLevel.NONE;
        this.txnMode = Mode.NONE;
    }
    
    /**
     * Constructs a {@link SimpleCacheConfiguration} based on a set of parameters.
     * 
     * @param cacheEntryListenerRegistrations the {@link CacheEntryListenerRegistration}s
     * @param cacheLoader                     the {@link CacheLoader}
     * @param cacheWriter                     the {@link CacheWriter}
     * @param cacheEntryExpiryPolicy          the {@link CacheEntryExpiryPolicy}
     * @param isReadThrough                   is read-through caching supported
     * @param isWriteThrough                  is write-through caching supported
     * @param isStatisticsEnabled             are statistics enabled
     * @param isStoreByValue                  <code>true</code> if the "store-by-value" more
     *                                        or <code>false</code> for "store-by-reference"
     * @param isTransactionsEnabled           <code>true</code> if transactions are enabled                                       
     * @param txnIsolationLevel               the {@link IsolationLevel}
     * @param txnMode                         the {@link Mode}
     */
    public SimpleCacheConfiguration(
            Iterable<CacheEntryListenerRegistration<? super K, ? super V>> cacheEntryListenerRegistrations,
            CacheLoader<K, ? extends V> cacheLoader,
            CacheWriter<? super K, ? super V> cacheWriter,
            CacheEntryExpiryPolicy<? super K, ? super V> cacheEntryExpiryPolicy, 
            boolean isReadThrough, 
            boolean isWriteThrough,
            boolean isStatisticsEnabled, 
            boolean isStoreByValue,
            boolean isTransactionsEnabled,
            IsolationLevel txnIsolationLevel, 
            Mode txnMode) {
        
        this.cacheEntryListenerRegistrations = new ArrayList<CacheEntryListenerRegistration<? super K, ? super V>>();
        
        for (CacheEntryListenerRegistration<? super K, ? super V> r : cacheEntryListenerRegistrations) {
            SimpleCacheEntryListenerRegistration<K, V> registration = 
                new SimpleCacheEntryListenerRegistration<K, V>(r.getCacheEntryListener(), 
                                                               r.getCacheEntryFilter(), 
                                                               r.isOldValueRequired(), 
                                                               r.isSynchronous());
            
            this.cacheEntryListenerRegistrations.add(registration);
        }
        
        this.cacheLoader = cacheLoader;
        this.cacheWriter = cacheWriter;
        
        this.cacheEntryExpiryPolicy = cacheEntryExpiryPolicy;
        
        this.isReadThrough = isReadThrough;
        this.isWriteThrough = isWriteThrough;
        
        this.isStatisticsEnabled = isStatisticsEnabled;
        
        this.isStoreByValue = isStoreByValue;
        
        this.isTransactionsEnabled = isTransactionsEnabled;
        this.txnIsolationLevel = txnIsolationLevel;
        this.txnMode = txnMode;
    }
    
    /**
     * A copy-constructor for a {@link SimpleCacheConfiguration}.
     * 
     * @param configuration  the {@link CacheConfiguration} from which to copy
     */
    public SimpleCacheConfiguration(CacheConfiguration<K, V> configuration) {
        this(configuration.getCacheEntryListenerRegistrations(), 
             configuration.getCacheLoader(), 
             configuration.getCacheWriter(), 
             configuration.getCacheEntryExpiryPolicy(),
             configuration.isReadThrough(), 
             configuration.isWriteThrough(),
             configuration.isStatisticsEnabled(), 
             configuration.isStoreByValue(),
             configuration.isTransactionsEnabled(),
             configuration.getTransactionIsolationLevel(), 
             configuration.getTransactionMode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<CacheEntryListenerRegistration<? super K, ? super V>> getCacheEntryListenerRegistrations() {
        return cacheEntryListenerRegistrations;
    }

    /**
     * Add a {@link CacheEntryListener}.  If the specified 
     * {@link CacheEntryListenerRegistration} is already known, the request to
     * add it is ignored.
     *
     * @param cacheEntryListener    the {@link CacheEntryListener}
     * @param requireOldValue       whether the old value is supplied to {@link javax.cache.event.CacheEntryEvent}.
     * @param cacheEntryEventFilter the {@link CacheEntryEventFilter}
     * @param synchronous           whether the caller is blocked until the listener invocation completes.
     */
    public SimpleCacheConfiguration<K, V> registerCacheEntryListener(
        CacheEntryListener<? super K, ? super V> cacheEntryListener,
        boolean requireOldValue,
        CacheEntryEventFilter<? super K, ? super V> cacheEntryEventFilter,
        boolean synchronous) {
        
        if (cacheEntryListener == null) {
            throw new NullPointerException("CacheEntryListener can't be null");
        }
        
        SimpleCacheEntryListenerRegistration<K, V> registration = 
                new SimpleCacheEntryListenerRegistration<K, V>(cacheEntryListener, 
                                                               cacheEntryEventFilter, 
                                                               requireOldValue, 
                                                               synchronous);
        
        boolean alreadyExists = false;
        for (CacheEntryListenerRegistration<? super K, ? super V> r : cacheEntryListenerRegistrations) {
            if (r.equals(registration)) {
                alreadyExists = true;
            }
        }
        
        if (!alreadyExists) {
            this.cacheEntryListenerRegistrations.add(registration);
        }
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CacheLoader<K, ? extends V> getCacheLoader() {
        return this.cacheLoader;
    }
    
    /**
     * Set the {@link CacheLoader}.
     * 
     * @param loader the {@link CacheLoader}
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setCacheLoader(CacheLoader<K, ? extends V> loader) {
        this.cacheLoader = loader;
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public CacheWriter<? super K, ? super V> getCacheWriter() {
        return this.cacheWriter;
    }
    
    /**
     * Set the {@link CacheWriter}.
     * 
     * @param writer the {@link CacheWriter}
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setCacheWriter(CacheWriter<? super K, ? super V> writer) {
        this.cacheWriter = writer;
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public CacheEntryExpiryPolicy<? super K, ? super V> getCacheEntryExpiryPolicy() {
        return this.cacheEntryExpiryPolicy;
    }
    
    /**
     * Set the {@link CacheEntryExpiryPolicy}.  If <code>null</code> is specified
     * the {@link CacheEntryExpiryPolicy} Default is assumed.
     * 
     * @param policy the {@link CacheEntryExpiryPolicy}
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setCacheEntryExpiryPolicy(CacheEntryExpiryPolicy<? super K, ? super V> policy) {
        if (policy == null) {
            this.cacheEntryExpiryPolicy = new CacheEntryExpiryPolicy.Default<K, V>();
        } else {
            this.cacheEntryExpiryPolicy = policy;
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
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setTransactions(IsolationLevel level, Mode mode) {
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
     * Set the Transaction {@link Mode}.
     * 
     * @param mode the {@link Mode}
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setTransactionMode(Mode mode) {
        this.txnMode = mode;
        return this;
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
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setReadThrough(boolean isReadThrough) {
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
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setWriteThrough(boolean isWriteThrough) {
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
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setStoreByValue(boolean isStoreByValue) {
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
     * {@inheritDoc}
     */
    @Override
    public void setStatisticsEnabled(boolean isStatisticsEnabled) {
        this.isStatisticsEnabled = isStatisticsEnabled;
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
     * @return the {@link SimpleCacheConfiguration} to permit fluent-style method calls
     */
    public SimpleCacheConfiguration<K, V> setTransactionsEnabled(boolean isTransactionsEnabled) {
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
        result = prime
                * result
                + ((cacheEntryListenerRegistrations == null) ? 0 : cacheEntryListenerRegistrations
                        .hashCode());
        result = prime * result
                + ((cacheLoader == null) ? 0 : cacheLoader.hashCode());
        result = prime * result
                + ((cacheWriter == null) ? 0 : cacheWriter.hashCode());
        result = prime * result
                + ((cacheEntryExpiryPolicy == null) ? 0 : cacheEntryExpiryPolicy.hashCode());
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
        if (!(object instanceof SimpleCacheConfiguration)) {
            return false;
        }
        SimpleCacheConfiguration<?, ?> other = (SimpleCacheConfiguration<?, ?>) object;
        if (cacheEntryListenerRegistrations == null) {
            if (other.cacheEntryListenerRegistrations != null) {
                return false;
            }
        } else if (!cacheEntryListenerRegistrations.equals(other.cacheEntryListenerRegistrations)) {
            return false;
        }
        if (cacheLoader == null) {
            if (other.cacheLoader != null) {
                return false;
            }
        } else if (!cacheLoader.equals(other.cacheLoader)) {
            return false;
        }
        if (cacheWriter == null) {
            if (other.cacheWriter != null) {
                return false;
            }
        } else if (!cacheWriter.equals(other.cacheWriter)) {
            return false;
        }
        if (cacheEntryExpiryPolicy == null) {
            if (other.cacheEntryExpiryPolicy != null) {
                return false;
            }
        } else if (!cacheEntryExpiryPolicy.equals(other.cacheEntryExpiryPolicy)) {
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
     * An implementation of a {@link CacheEntryListenerRegistration}.
     * 
     * @param <K> the type of the keys
     * @param <V> the type of the values
     */
    static class SimpleCacheEntryListenerRegistration<K, V> implements CacheEntryListenerRegistration<K, V> {

        private CacheEntryListener<? super K, ? super V> listener;
        private CacheEntryEventFilter<? super K, ? super V> filter;
        private boolean isOldValueRequired;
        private boolean isSynchronous;
        
        /**
         * Constructs an {@link SimpleCacheEntryListenerRegistration}.
         * 
         * @param listener            the {@link CacheEntryListener}
         * @param filter              the optional {@link CacheEntryEventFilter}
         * @param isOldValueRequired  if the old value is required for events with this listener
         * @param isSynchronous       if the listener should block the thread causing the event
         */
        public SimpleCacheEntryListenerRegistration(CacheEntryListener<? super K, ? super V> listener, 
                                                    CacheEntryEventFilter<? super K, ? super V> filter, 
                                                    boolean isOldValueRequired, 
                                                    boolean isSynchronous) {
            this.listener = listener;
            this.filter = filter;
            this.isOldValueRequired = isOldValueRequired;
            this.isSynchronous = isSynchronous;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public CacheEntryEventFilter<? super K, ? super V> getCacheEntryFilter() {
            return filter;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public CacheEntryListener<? super K, ? super V> getCacheEntryListener() {
            return listener;
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
            result = prime * result + ((filter == null) ? 0 : filter.hashCode());
            result = prime * result + (isOldValueRequired ? 1231 : 1237);
            result = prime * result + (isSynchronous ? 1231 : 1237);
            result = prime * result
                    + ((listener == null) ? 0 : listener.hashCode());
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
            if (!(object instanceof SimpleCacheEntryListenerRegistration)) {
                return false;
            }
            SimpleCacheEntryListenerRegistration<?, ?> other = (SimpleCacheEntryListenerRegistration<?, ?>) object;
            if (filter == null) {
                if (other.filter != null) {
                    return false;
                }
            } else if (!filter.equals(other.filter)) {
                return false;
            }
            if (isOldValueRequired != other.isOldValueRequired) {
                return false;
            }
            if (isSynchronous != other.isSynchronous) {
                return false;
            }
            if (listener == null) {
                if (other.listener != null) {
                    return false;
                }
            } else if (!listener.equals(other.listener)) {
                return false;
            }
            return true;
        }
    }

}
