/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.event.CacheEntryListenerRegistration;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.util.concurrent.TimeUnit;

/**
 * A read-only representation of a {@link Cache} configuration.
 * <p/>
 * The properties provided by instances of this interface are used by 
 * {@link CacheManager}s to configure {@link Cache}s.
 * <p/>
 * Implementations of this interface must override {@link Object#hashCode()} and
 * {@link Object#equals(Object)} as {@link Configuration}s are often compared at runtime.
 * 
 * @param <K> the type of keys maintained the cache
 * @param <V> the type of cached values
 * 
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @author Brian Oliver
 * 
 * @since 1.0
 */
public interface Configuration<K, V> {

    /**
     * Determines if a {@link Cache} should operate in "read-through" mode.
     * <p/>
     * When in "read-through" mode, cache misses that occur due to cache entries
     * not existing as a result of performing a "get" call via one of {@link Cache#get(Object)}, 
     * {@link Cache#getAll(java.util.Set)}, {@link Cache#getAndRemove(Object)} and/or
     * {@link Cache#getAndReplace(Object, Object)} will appropriately cause 
     * the configured {@link CacheLoader} to be invoked.
     * <p/>
     * The default value is <code>false</code>.
     * 
     * @return <code>true</code> when a {@link Cache} is in "read-through" mode. 
     * 
     * @see #getCacheLoader()
     */
    boolean isReadThrough();
    
    /**
     * Determines if a {@link Cache} should operate in "write-through" mode.
     * <p/>
     * When in "write-through" mode, cache updates that occur as a result of performing 
     * "put" operations called via one of {@link Cache#put(Object, Object)}, {@link Cache#getAndRemove(Object)},
     * {@link javax.cache.Cache#removeAll()}, {@link Cache#getAndPut(Object, Object)}
     * {@link Cache#getAndRemove(Object)}, {@link Cache#getAndReplace(Object, Object)}, 
     * {@link Cache#invokeEntryProcessor(Object, javax.cache.Cache.EntryProcessor)}
     * will appropriately cause the configured {@link CacheWriter} to be invoked.
     * <p/>
     * The default value is <code>false</code>.
     * 
     * @return <code>true</code> when a {@link Cache} is in "write-through" mode.
     * 
     * @see #getCacheWriter()
     */
    boolean isWriteThrough();
    
    /**
     * Whether storeByValue (true) or storeByReference (false).
     * When true, both keys and values are stored by value.
     * <p/>
     * When false, both keys and values are stored by reference.
     * Caches stored by reference are capable of mutation by any threads holding
     * the reference. The effects are:
     * <ul>
     * <li>if the key is mutated, then the key may not be retrievable or removable</li>
     * <li>if the value is mutated, then all threads in the JVM can potentially observe those mutations,
     * subject to the normal Java Memory Model rules.</li>
     * </ul>
     * Storage by reference only applies to the local heap. If an entry is moved off heap it will
     * need to be transformed into a representation. Any mutations that occur after transformation
     * may not be reflected in the cache.
     * <p/>
     * When a cache is storeByValue, any mutation to the key or value does not affect the key of value
     * stored in the cache.
     * <p/>
     * The default value is <code>true</code>.
     * 
     * @return true if the cache is store by value
     */
    boolean isStoreByValue();

    /**
     * Checks whether statistics collection is enabled in this cache.
     * <p/>
     * The default value is <code>false</code>.
     *
     * @return true if statistics collection is enabled
     */
    boolean isStatisticsEnabled();

    /**
     * Checks whether management is enabled on this cache.
     * <p/>
     * The default value is <code>false</code>.
     *
     * @return true if management is enabled
     */
    boolean isManagementEnabled();

    /**
     * Checks whether transactions are enabled for this cache.
     * <p/>
     * Note that in a transactional cache, entries being mutated within a 
     * transaction cannot be expired by the cache.
     * <p/>
     * The default value is <code>false</code>.
     * 
     * @return true if transaction are enabled
     */
    boolean isTransactionsEnabled();

    /**
     * Gets the transaction isolation level.
     * <p/>
     * The default value is {@link IsolationLevel#NONE}.
     * 
     * @return the isolation level.
     */
    IsolationLevel getTransactionIsolationLevel();

    /**
     * Gets the transaction mode.
     * <p/>
     * The default value is {@link Mode#NONE}.
     * 
     * @return the the mode of the cache.
     */
    Mode getTransactionMode();
    
    /**
     * Obtains the {@link CacheEntryListenerRegistration}s for CacheEntryListeners 
     * to be configured on a {@link Cache}.
     * 
     * @return an {@link Iterable} over the {@link CacheEntryListenerRegistration}s
     */
    Iterable<CacheEntryListenerRegistration<? super K, ? super V>> getCacheEntryListenerRegistrations();
    
    /**
     * Gets the registered {@link CacheLoader}, if any.
     * <p/>
     * A CacheLoader should be configured for "Read Through" caches
     * to load values when a cache miss occurs using either the
     * {@link Cache#get(Object)} and/or {@link Cache#getAll(java.util.Set)} methods.
     * <p/>
     * The default value is <code>null</code>.
     * 
     * @return the {@link CacheLoader} or null if none has been set.
     */
    CacheLoader<K, V> getCacheLoader();

    /**
     * Gets the registered {@link CacheWriter}, if any.
     * <p/>
     * The default value is <code>null</code>.
     * 
     * @return the {@link CacheWriter} or null if none has been set.
     */
    CacheWriter<? super K, ? super V> getCacheWriter();

    /**
     * Gets the {@link ExpiryPolicy} to be used for caches.
     * <p/>
     * The default value is {@link javax.cache.ExpiryPolicy.Default}.
     * 
     * @return the {@link ExpiryPolicy} (must not be <code>null</code>)
     */
    ExpiryPolicy<? super K, ? super V> getExpiryPolicy();

    /**
     * A time duration.
     */
    public static class Duration {
        /**
         * ETERNAL (forever).
         */
        public static final Duration ETERNAL = new Duration();

        /**
         * ZERO (no time).
         */
        public static final Duration ZERO = new Duration(TimeUnit.SECONDS, 0);
        
        /**
         * The unit of time to specify time in. The minimum time unit is milliseconds.
         */
        private final TimeUnit timeUnit;

       /*
        * How long, in the specified units, the cache entries should live.
        * The lifetime is measured from the cache entry was last put (i.e. creation or modification for an update) or
        * the time accessed depending on the {@link ExpiryType}
        * 0 means eternal.
        *
        */
        private final long durationAmount;

        /**
         * A private constructor to an eternal {@link Duration}.
         */
        private Duration() {
            this.timeUnit = null;
            this.durationAmount = 0;
        }
        
        /**
         * Constructs a duration.
         *
         * @param timeUnit   the unit of time to specify time in. The minimum time unit is milliseconds.
         * @param durationAmount how long, in the specified units, the cache entries should live. 0 means eternal.
         * @throws NullPointerException          if timeUnit is null
         * @throws IllegalArgumentException      if durationAmount is less than 0 or a TimeUnit less than milliseconds is specified
         */
        public Duration(TimeUnit timeUnit, long durationAmount) {
            if (timeUnit == null) {
                throw new NullPointerException();
            }
            switch (timeUnit) {
                case NANOSECONDS:
                case MICROSECONDS:
                    throw new IllegalArgumentException("Must specify a TimeUnit of milliseconds or higher.");
                default:
                    this.timeUnit = timeUnit;
                    break;
            }
            if (durationAmount < 0) {
                throw new IllegalArgumentException("Cannot specify a negative durationAmount.");
            }
            this.durationAmount = durationAmount;
        }

        /**
         * Constructs a {@link Duration} based on the duration between two
         * specified points in time (since the Epoc), messured in milliseconds.
         * 
         * @param startTime the start time (since the Epoc)
         * @param endTime   the end time (since the Epoc)
         */
        public Duration(long startTime, long endTime) {
            if (startTime == Long.MAX_VALUE || endTime == Long.MAX_VALUE) {
                //we're dealing with arithmetic involving an ETERNAL value
                //so the result must be ETERNAL
                timeUnit = null;
                durationAmount = 0;
            } else {
                timeUnit = TimeUnit.MILLISECONDS;
                durationAmount = endTime - startTime;
            }
        }
        
        /**
         * @return the TimeUnit used to specify this duration
         */
        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        /**
         * @return the number of TimeUnits which quantify this duration
         */
        public long getDurationAmount() {
            return durationAmount;
        }

        /**
         * Determines if a {@link Duration} is eternal (forever).
         * 
         * @return true if the {@link Duration} is eternal
         */
        public boolean isEternal() {
            return timeUnit == null && durationAmount == 0;
        }
        
        /**
         * Calculates the adjusted time (from the Epoc) given a specified time 
         * (to be adjusted) by the duration.
         * 
         * @param time the time from which to adjust given the duration
         * @return the adjusted time
         */
        public long getAdjustedTime(long time) {
            if (isEternal()) {
                return Long.MAX_VALUE;
            } else {
                return time + timeUnit.toMillis(durationAmount);
            }
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Duration duration = (Duration) o;

            long time1 =  timeUnit.toMillis(durationAmount);
            long time2 = duration.timeUnit.toMillis(duration.durationAmount);
            return time1 == time2;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return ((Long)timeUnit.toMillis(durationAmount)).hashCode();
        }
    }
}
