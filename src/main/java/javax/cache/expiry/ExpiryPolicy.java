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

/**
 * Defines functions to determine when cache entries will expire based on
 * creation, access and modification operations.
 * <p>
 * Each of the functions return a new {@link Duration} that specifies the
 * amount of time that must pass before a cache entry is considered expired.
 * {@link Duration} has constants defined for useful durations.
 *
 * @author Brian Oliver
 * @author Greg Luck
 * @since 1.0
 * @see Duration
 */
public interface ExpiryPolicy {

  /**
   * Gets the {@link Duration} before a newly created Cache.Entry is considered
   * expired.
   * <p>
   * This method is called by a caching implementation after a Cache.Entry is
   * created, but before a Cache.Entry is added to a cache, to determine the
   * {@link Duration} before an entry expires.  If a {@link Duration#ZERO}
   * is returned the new Cache.Entry is considered to be already expired and
   * will not be added to the Cache.
   * <p>
   * Should an exception occur while determining the Duration, an implementation
   * specific default {@link Duration} will be used.
   *
   * @return the new {@link Duration} before a created entry expires
   */
  Duration getExpiryForCreation();

  /**
   * Gets the {@link Duration} before an accessed Cache.Entry is
   * considered expired.
   * <p>
   * This method is called by a caching implementation after a Cache.Entry is
   * accessed to determine the {@link Duration} before an entry expires.  If a
   * {@link Duration#ZERO} is returned a Cache.Entry will be
   * considered immediately expired.  Returning <code>null</code> will result
   * in no change to the previously understood expiry {@link Duration}.
   * <p>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @return the new {@link Duration} before an accessed entry expires
   */
  Duration  getExpiryForAccess();

  /**
   * Gets the {@link Duration} before an updated Cache.Entry is considered
   * expired.
   * <p>
   * This method is called by the caching implementation after a Cache.Entry is
   * updated to determine the {@link Duration} before the updated entry expires.
   * If a {@link Duration#ZERO} is returned a Cache.Entry is considered
   * immediately expired.  Returning <code>null</code> will result in no change
   * to the previously understood expiry {@link Duration}.
   * <p>
   * Should an exception occur while determining the Duration, an implementation
   * specific default Duration will be used.
   *
   * @return the new {@link Duration} before an updated entry expires
   */
  Duration getExpiryForUpdate();
}
