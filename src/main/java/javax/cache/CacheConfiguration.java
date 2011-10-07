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
 * Information on how a cache is configured.
 * <p/>
 * A Cache may be constructed by {@link CacheManager} using a configuration instance.
 * <p/>
 * At runtime it is used by javax.cache to decide how to behave. For example the behaviour of put
 * will vary depending on whether the cache is write-through.
 * <p/>
 * Finally, a cache makes it's configuration visible via this interface.
 *
 * Only those configurations which can be changed at runtime (if supported by the underlying implementation)
 * have setters in this interface. Those that can only be set prior to cache construction have setters in
 * {@link CacheBuilder}.
 *
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface CacheConfiguration {

    /**
     * Whether the cache is a read-through cache. A CacheLoader should be configured for read through caches
     * which is called on a cache miss.
     * <p/>
     * Default value is false.
     *
     * @return true if the cache is read-through
     */
    boolean isReadThrough();

    /**
     * Sets whether the cache is a read-through cache.
     *
     * @param readThrough the value for readThrough
     * @throws IllegalStateException if the configuration can no longer be changed
     */
    void setReadThrough(boolean readThrough);

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     * <p/>
     * Default value is false.
     *
     * @return true if the cache is write-through
     */
    boolean isWriteThrough();

    /**
     * Whether the cache is a write-through cache. A CacheWriter should be configured.
     *
     * @param writeThrough set to true for a write-through cache
     */
    void setWriteThrough(boolean writeThrough);

    /**
     * Whether storeByValue (true) or storeByReference (false).
     * When true, both keys and values are stored by value.
     * <p/>
     * When false, both keys and values are stored by reference.
     * Caches stored by reference are capable of mutation by any threads holding
     * the reference. The effects are:
     * <ul>
     * <li>if the key is mutated, then the key may not be retrievable or removable</li>
     * <li>if the value is mutated, then any threads holding references will see the changes</li>
     * </ul>
     * Storage by reference only applies to local heap. If an entry is moved outside local heap it will
     * need to be transformed into a representation. Any mutations that occur after transformation will
     * not be reflected in the cache.
     * <p/>
     * Default value is true.
     *
     * @return true if the cache is store by value
     */
    boolean isStoreByValue();

    /**
     * Checks whether statistics collection is enabled in this cache.
     * <p/>
     * Default value is false.
     *
     * @return true if statistics collection is enabled
     */
    boolean isStatisticsEnabled();

    /**
     * Sets whether statistics gathering is enabled  on this cache.
     *
     * @param enableStatistics true to enable statistics, false to disable
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
     * @return the isolation level. null if this cache is not transactional
     */
    IsolationLevel getTransactionIsolationLevel();

    /**
     * Gets the transaction mode.
     * @return the the mode of the cache. null if this cache is not transactional
     */
    Mode getTransactionMode();

    /**
     * Sets how long cache entries should live. If expiry is not set entries are eternal.
     *
     *
     *
     * @param type the type of the expiry
     * @param duration how long, in the specified duration, the cache entries should live.
     * @throws NullPointerException is type or duration is null
     */
    void setExpiry(ExpiryType type, Duration duration);

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
     * Sets how large the cache can be.
     * <p/>
     * When the cache size maximum is reached the cache must evict entries to return the cache below the limit.
     * Distributed or multi-tiered cache implementations may choose to interpret size per node/tier or in total.
     *
     *
     * @param size the size of the cache.
     * @throws NullPointerException is size is null
     * @throws InvalidConfigurationException if the cache size is specified in bytes and the implementation does not support this.
     */
    void setSize(Size size);

    /**
     * The capacity of the cache.
     * <p/>
     * Default value is {@link Size#UNLIMITED}.
     *
     * @return the capacity of this cache.
     */
    Size getSize();

    /**
     * The size of a Cache.
     */
    public static class Size {
        /**
         * UNLIMITED
         */
        public static final Size UNLIMITED = new Size(Unit.PETABYTES, 0);

        private Unit sizeUnit;
        private long size;

        /**
         * @param sizeUnit the unit of time to specify time in. The minimum time unit is milliseconds.
         * @param size     how large, in the specified units, the cache should be. 0 means unlimited.
         * @throws NullPointerException     if sizeUnit is null
         * @throws IllegalArgumentException if size is less than 0
         *                                  TODO: revisit above exceptions
         */
        public Size(Unit sizeUnit, long size) {
            if (sizeUnit == null) {
                throw new NullPointerException();
            }
            this.sizeUnit = sizeUnit;
            if (size < 0) {
                throw new IllegalArgumentException();
            }
            this.size = size;
        }

        /**
         * Gets the Unit used to specify the size
         *
         * @return the Unit used to specify the size
         */
        public Unit getSizeUnit() {
            return sizeUnit;
        }

        /**
         * Gets the Unit used to specify the size
         *
         * @return the Unit used to specify the size. 0 means unlimited
         */
        public long getSize() {
            return size;
        }

        /**
         * A <tt>Unit</tt> represents different units for representing size.
         * A <tt>Unit</tt> does not maintain size information, but only
         * helps organize and use size representations that may be maintained
         * separately across various contexts.
         * <p/>
         * There are two approaches:
         * <ol>
         * <li>size as a count of entries in the cache</li>
         * <li>size in bytes in the cache</li>
         * </ol>
         *
         * @author Greg Luck
         */
        public static enum Unit {


            /**
             * A count of the number of entries in the Cache
             */
            COUNT,

            /**
             * The space occupied by the object graphs or the Seralized representations, in bytes.
             */
            BYTES,

            /**
             * The space occupied by the object graphs or the Seralized representations, in kilobytes.
             */
            KILOBYTES,

            /**
             * The space occupied by the object graphs or the Seralized representations, in megabytes.
             */
            MEGABYTES,

            /**
             * The space occupied by the object graphs or the Seralized representations, in gigabytes.
             */
            GIGABYTES,

            /**
             * The space occupied by the object graphs or the Seralized representations, in terabytes.
             */
            TERABYTES,

            /**
             * The space occupied by the object graphs or the Seralized representations, in petabytes.
             */
            PETABYTES

        }
    }

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
        private final long timeToLive;

        /**
         * Constructs a duration.
         *
         * @param timeUnit   the unit of time to specify time in. The minimum time unit is milliseconds.
         * @param timeToLive how long, in the specified units, the cache entries should live. 0 means eternal.
         * @throws InvalidConfigurationException if a TimeUnit less than milliseconds is specified.
         * @throws NullPointerException          if timeUnit is null
         * @throws IllegalArgumentException      if timeToLive is less than 0
         *                                       TODO: revisit above exceptions
         * todo: Change TimeUnit to our own TimeUnit which does not define nano and micro
         */
        public Duration(TimeUnit timeUnit, long timeToLive) {
            if (timeUnit == null) {
                throw new NullPointerException();
            }
            switch (timeUnit) {
                case NANOSECONDS:
                case MICROSECONDS:
                    throw new InvalidConfigurationException();
                default:
                    this.timeUnit = timeUnit;
                    break;
            }
            if (timeToLive < 0) {
                throw new IllegalArgumentException();
            }
            this.timeToLive = timeToLive;
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
        public long getTimeToLive() {
            return timeToLive;
        }

    }

    /**
     * Type of Duration
     */
    public enum ExpiryType {
        /**
         * Time since last modified. Creation is a modification event
         */
        MODIFIED,
        /**
         * Time since last accessed
         */
        ACCESSED
    }
}
