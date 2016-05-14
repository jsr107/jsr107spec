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

package javax.cache.event;

import javax.cache.Cache;
import java.util.EventObject;

/**
 * A Cache entry event base class.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 * @author Greg Luck
 * @since 1.0
 */
public abstract class CacheEntryEvent<K, V> extends EventObject
    implements Cache.Entry<K, V> {

  private EventType eventType;

  /**
   * Constructs a cache entry event from a given cache as source
   *
   * @param source the cache that originated the event
   * @param eventType the event type for this event
   */
  public CacheEntryEvent(Cache source, EventType eventType) {
    super(source);
    this.eventType = eventType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Cache getSource() {
    return (Cache) super.getSource();
  }

  /**
   * Returns the previous value, that existed prior to the
   * modification of the Entry value.
   *
   * @return the previous value or <code>null</code> if there was no previous value
   */
  public abstract V getOldValue();

  /**
   * Whether the old value is available.
   *
   * @return true if the old value is populated
   */
  public abstract boolean isOldValueAvailable();

  /**
   * Gets the event type of this event
   *
   * @return the event type.
   */
  public final EventType getEventType() {
    return eventType;
  }
}
