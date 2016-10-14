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
 * Determines the {@link CacheResolver} to use for an annotated method. The
 * {@link CacheResolver} will be retrieved once per annotated method.
 * <p>
 * Implementations MUST be thread-safe.
 *
 * @author Eric Dalquist
 * @since 1.0
 */
public interface CacheResolverFactory {

  /**
   * Get the {@link CacheResolver} used at runtime for resolution of the
   * {@link Cache} for the {@link CacheResult}, {@link CachePut},
   * {@link CacheRemove}, or {@link CacheRemoveAll} annotation.
   *
   * @param cacheMethodDetails The details of the annotated method to get the
   *                           {@link CacheResolver} for. @return The {@link
   *                           CacheResolver} instance to be
   *                           used by the interceptor.
   */
  CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation>
                                     cacheMethodDetails);

  /**
   * Get the {@link CacheResolver} used at runtime for resolution of the {@link
   * Cache} for the {@link CacheResult} annotation to cache exceptions.
   * <p>
   * Will only be called if {@link CacheResult#exceptionCacheName()} is not empty.
   *
   * @param cacheMethodDetails The details of the annotated method to get the
   *                           {@link CacheResolver} for.
   * @return The {@link CacheResolver} instance to be used by the interceptor.
   */
  CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult>
                                              cacheMethodDetails);
}
