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

package javax.cache.annotation;

import java.lang.annotation.Annotation;

/**
 * Runtime information about an intercepted method invocation for a method
 * annotated with {@link CacheResult}, {@link CachePut}, or
 * {@link CacheRemove}.
 * <p>
 * Used with {@link CacheKeyGenerator#generateCacheKey(CacheKeyInvocationContext)}
 * to generate a {@link GeneratedCacheKey} for the invocation.
 *
 * @param <A> The type of annotation this context information is for. One of
 *            {@link CacheResult}, {@link CachePut}, or {@link CacheRemove}.
 * @author Eric Dalquist
 * @see CacheKeyGenerator
 */
public interface CacheKeyInvocationContext<A extends Annotation>
    extends CacheInvocationContext<A> {

  /**
   * Returns a clone of the array of all method parameters to be used by the
   * {@link
   * CacheKeyGenerator} in creating a {@link GeneratedCacheKey}. The returned array
   * may be the same as or a subset of the array returned by
   * {@link #getAllParameters()}
   * <p>
   * Parameters in this array are selected by the following rules:
   * <ul>
   * <li>If no parameters are annotated with {@link CacheKey} or {@link
   * CacheValue}
   * then all parameters are included</li>
   * <li>If a {@link CacheValue} annotation exists and no {@link CacheKey} then
   * all
   * parameters except the one annotated with {@link CacheValue} are included</li>
   * <li>If one or more {@link CacheKey} annotations exist only those parameters
   * with the {@link CacheKey} annotation are included</li>
   * </ul>
   *
   * @return An array of all parameters to be used in cache key generation
   */
  CacheInvocationParameter[] getKeyParameters();

  /**
   * When a method is annotated with {@link CachePut} this is the parameter
   * annotated with {@link CacheValue}
   *
   * @return The parameter to cache, will never be null for methods annotated with
   *         {@link CachePut}, will be null for methods not annotated with {@link
   *         CachePut}
   */
  CacheInvocationParameter getValueParameter();
}
