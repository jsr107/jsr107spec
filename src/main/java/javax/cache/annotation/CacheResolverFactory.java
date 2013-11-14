/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
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
   *                           used by the intercepter.
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
   * @return The {@link CacheResolver} instance to be used by the intercepter.
   */
  CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult>
                                              cacheMethodDetails);
}
