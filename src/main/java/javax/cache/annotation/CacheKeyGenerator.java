/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import java.lang.annotation.Annotation;

/**
 * Generates a {@link GeneratedCacheKey} based on
 * a {@link CacheKeyInvocationContext}.
 * <p/>
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
