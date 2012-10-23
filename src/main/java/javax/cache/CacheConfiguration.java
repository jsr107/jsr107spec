/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.util.concurrent.TimeUnit;

/**
 * A value object for cache configuration.
 * <p/>
 * A Cache may be constructed by {@link CacheManager} using a configuration instance.
 * <p/>
 * At runtime it is used by javax.cache to decide how to behave. For example the behaviour of put
 * will vary depending on whether the cache is write-through.
 * <p/>
 * Finally, a cache makes its configuration visible via this interface.
 *
 * Only those configurations which can be changed at runtime (if supported by the underlying implementation)
 * have setters in this interface. Those that can only be set prior to cache construction have setters in
 * {@link CacheBuilder}.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheConfiguration<K, V> {

    /**
     * Whether the cache is a read-through cache. A CacheLoader should be configured for read through caches
     * which is called by the cache for what without the loader would have been misses on
     * {@link Cache#get(Object)} and {@link Cache#getAll(java.util.Set}.
     * <p/>
     * Default value is false.
     *
     * @return true if the cache is read-through
     */
    boolean isReadThrough();

    /**
     * Whether the cache is a write-through cache. If so a CacheWriter should be configured.
     * <p/>
     * Default value is false.
     *
     * @return true if the cache is write-through
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
     * The default value is true.
     * <p/>
     * When a cache is storeByValue, any mutation to the key or value does not affect the key of value
     * stored in the cache.
     *
     * @return true if the cache is store by value
     */
    boolean isStoreByValue();

    /**
     * Checks whether statistics collection is enabled in this cache.
     * <p/>
     * The default value is false.
     *
     * @return true if statistics collection is enabled
     */
    boolean isStatisticsEnabled();

    /**
     * Sets whether statistics gathering is enabled  on this cache. This may be changed at runtime.
     *
     * @param enableStatistics true to enable statistics, false to disable.
     */
    void setStatisticsEnabled(boolean enableStatistics);

    /**
     * Checks whether transaction are enabled for this cache.
     * <p/>
     * Default value is false.
     *
     * @return true if transaction are enabled
     */
    boolean isTransactionEnabled();

    /**
     * Gets the transaction isolation level.
     * @return the isolation level. {@link javax.cache.transaction.IsolationLevel#NONE} if this cache is not transactional.
     */
    IsolationLevel getTransactionIsolationLevel();

    /**
     * Gets the transaction mode.
     * @return the the mode of the cache. {@link javax.cache.transaction.Mode#NONE} if this cache is not transactional.
     */
    Mode getTransactionMode();

    /**
     * Gets the registered {@link CacheLoader}, if any.
     * @return the {@link CacheLoader} or null if none has been set.
     */
    CacheLoader<K, ? extends V> getCacheLoader();

    /**
     * Gets the registered {@link CacheWriter}, if any.
     * @return the {@link CacheWriter} or null if none has been set.
     */
    CacheWriter<? super K, ? super V> getCacheWriter();

    /**
     * Gets the cache's time to live setting,Sets how long cache entries should live. If expiry is not set entries are eternal.
     * <p/>
     * Default value is {@link Duration#ETERNAL}.
     *
     * @param type the type of the expiration
     * @return how long, in milliseconds, the specified units, the entry should live. 0 means eternal.
     */
    Duration getExpiry(ExpiryType type);

    /**
     * A time duration.
     */
    public static class Duration {
        /**
         * ETERNAL
         */
        public static final Duration ETERNAL = new Duration(TimeUnit.SECONDS, 0);

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Duration duration = (Duration) o;

            long time1 =  timeUnit.toMillis(durationAmount);
            long time2 = duration.timeUnit.toMillis(duration.durationAmount);
            return time1 == time2;
        }

        @Override
        public int hashCode() {
            return ((Long)timeUnit.toMillis(durationAmount)).hashCode();
        }
    }

    /**
     * Type of Expiry
     */
    public enum ExpiryType {

        /**
         * The time since a {@link Cache.Entry} was <em>created</em> or <em>last modified</em>. An example of a cache operation
         * which does this is {@link Cache#put}.
         */
        MODIFIED,

        /**
         * The Time since a {@link Cache.Entry} was last <em>accessed</em> by a cache operation. An access explicitly includes  <em>modified</em>
         * operations. An example is {@link Cache#get(Object)}
         */
        ACCESSED
    }
}
