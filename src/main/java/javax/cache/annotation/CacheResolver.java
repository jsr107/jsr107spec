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

import javax.cache.Cache;
import java.lang.annotation.Annotation;

/**
 * Determines the {@link Cache} to use for an intercepted method invocation.
 * <p>
 * Implementations MUST be thread-safe.
 *
 * @author Eric Dalquist
 * @see CacheResolverFactory
 * @since 1.0
 */
public interface CacheResolver {

  /**
   * Resolve the {@link Cache} to use for the {@link CacheInvocationContext}.
   *
   * @param <K> the type of key
   * @param <V> the type of value
   * @param cacheInvocationContext The context data for the intercepted method
   *                               invocation
   * @return The {@link Cache} instance to be used by the interceptor
   */
  <K, V> Cache<K, V> resolveCache(CacheInvocationContext<? extends Annotation>
                                      cacheInvocationContext);

}
