/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *  Copyright (c) 2011-2016 Terracotta, Inc.
 *  Copyright (c) 2011-2016 Oracle and/or its affiliates.
 *
 *  All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package javax.cache.expiry;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * A {@link java.io.Serializable} duration of time.
 *
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @author Brian Oliver
 * @since 1.0
 * @see ExpiryPolicy
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
   * One day.
   */
  public static final Duration ONE_DAY = new Duration(DAYS, 1);

  /**
   * One hour.
   */
  public static final Duration ONE_HOUR = new Duration(HOURS, 1);

  /**
   * Thirty minutes.
   */
  public static final Duration THIRTY_MINUTES = new Duration(MINUTES, 30);

  /**
   * Twenty minutes.
   */
  public static final Duration TWENTY_MINUTES = new Duration(MINUTES, 20);

  /**
   * Ten minutes.
   */
  public static final Duration TEN_MINUTES = new Duration(MINUTES, 10);

  /**
   * Five minutes.
   */
  public static final Duration FIVE_MINUTES = new Duration(MINUTES, 5);

  /**
   * One minute.
   */
  public static final Duration ONE_MINUTE = new Duration(MINUTES, 1);

  /**
   * Zero (no time).
   */
  public static final Duration ZERO = new Duration(SECONDS, 0);

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
   * @param timeUnit       the unit of time to specify time in. The minimum time unit is milliseconds.
   * @param durationAmount how long, in the specified units, the cache entries should live. 0 means eternal.
   * @throws NullPointerException     if timeUnit is null
   * @throws IllegalArgumentException if durationAmount is less than 0 or a TimeUnit less than milliseconds is specified
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
   * specified points in time (since the Epoc), measured in milliseconds.
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
    } else if (startTime < 0) {
        throw new IllegalArgumentException("Cannot specify a negative startTime.");
    } else if (endTime < 0) {
      throw new IllegalArgumentException("Cannot specify a negative endTime.");
    } else {
      timeUnit = TimeUnit.MILLISECONDS;
      durationAmount = Math.max(startTime, endTime) - Math.min(startTime, endTime);
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
  public boolean equals(Object other) {
    if (this == other) {
      return true;

    } else if (other == null || getClass() != other.getClass()) {
      return false;

    } else {
      Duration duration = (Duration) other;

      if (this.timeUnit == null && duration.timeUnit == null &&
          this.durationAmount == duration.durationAmount) {
        return true;
      } else if (this.timeUnit != null && duration.timeUnit != null) {
        long time1 = timeUnit.toMillis(durationAmount);
        long time2 = duration.timeUnit.toMillis(duration.durationAmount);
        return time1 == time2;
      } else {
        return false;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return timeUnit == null ? -1 : (int)timeUnit.toMillis(durationAmount);
  }
}
