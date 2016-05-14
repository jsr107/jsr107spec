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

package javax.cache.configuration;

import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import java.io.Serializable;

/**
 * Defines the configuration requirements for a
 * {@link CacheEntryListener} and a {@link Factory} for its
 * creation.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 * @author Brian Oliver
 * @author Greg Luck
 * @since 1.0
 */
public interface CacheEntryListenerConfiguration<K, V> extends Serializable {
  /**
   * Obtains the {@link Factory} for the
   * {@link CacheEntryListener}.
   *
   * @return the {@link Factory} for the
   *         {@link CacheEntryListener}
   */
  Factory<CacheEntryListener<? super K, ? super V>> getCacheEntryListenerFactory();

  /**
   * Determines if the old value should be provided to the
   * {@link CacheEntryListener}.
   *
   * @return <code>true</code> if the old value is required by the
   *         {@link CacheEntryListener}
   */
  boolean isOldValueRequired();

  /**
   * Obtains the {@link Factory} for the {@link CacheEntryEventFilter} that should be
   * applied prior to notifying the {@link CacheEntryListener}.
   * <p>
   * When <code>null</code> no filtering is applied and all appropriate events
   * are notified.
   *
   * @return the {@link Factory} for the
   *         {@link CacheEntryEventFilter} or <code>null</code>
   *         if no filtering is required
   */
  Factory<CacheEntryEventFilter<? super K, ? super V>>
  getCacheEntryEventFilterFactory();

  /**
   * Determines if the thread that caused an event to be created should be
   * blocked (not return from the operation causing the event) until the
   * {@link CacheEntryListener} has been notified.
   *
   * @return <code>true</code> if the thread that created the event should block
   */
  boolean isSynchronous();
}
