/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
