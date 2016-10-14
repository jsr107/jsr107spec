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
 * Generates a {@link GeneratedCacheKey} based on
 * a {@link CacheKeyInvocationContext}.
 * <p>
 * Implementations must be thread-safe.
 *
 * @author Eric Dalquist
 * @since 1.0
 */
public interface CacheKeyGenerator {

  /**
   * Called for each intercepted method invocation to generate a suitable
   * cache key (as a {@link GeneratedCacheKey}) from the
   * {@link CacheKeyInvocationContext} data.
   *
   * @param cacheKeyInvocationContext Information about the intercepted method invocation
   * @return A non-null cache key for the invocation.
   */
  GeneratedCacheKey generateCacheKey(CacheKeyInvocationContext<? extends Annotation> cacheKeyInvocationContext);
}
