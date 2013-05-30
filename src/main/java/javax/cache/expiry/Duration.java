/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.expiry;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * A {@link java.io.Serializable} duration of time.
 */
public class Duration implements Serializable {

    /**
     * The serialVersionUID required for {@link java.io.Serializable}.
     */
    public static final long serialVersionUID = 201305101442L;

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

    /**
     * How long, in the specified units, the cache entries should live.
     * The lifetime is measured from the cache entry was last accessed or
     * mutated.
     */
    private final long durationAmount;

    /**
     * Constructs a Duration (that by default is Eternal).
     */
    public Duration() {
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
            if (durationAmount == 0) {
                //allow creation of an Eternal Duration
                this.timeUnit = null;
                this.durationAmount = 0;
            } else {
                throw new NullPointerException();
            }

        } else {
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
     * Obtain the TimeUnit for the Duration
     *
     * @return the TimeUnit
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Obtain the number of TimeUnits in the Duration
     *
     * @return the number of TimeUnits
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
     * Determines if a {@link Duration} is zero.
     *
     * @return true if the {@link Duration} is zero
     */
    public boolean isZero() {
        return timeUnit != null && durationAmount == 0;
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
        return timeUnit == null ? (int)durationAmount : ((Long)timeUnit.toMillis(durationAmount)).hashCode();
    }
}
