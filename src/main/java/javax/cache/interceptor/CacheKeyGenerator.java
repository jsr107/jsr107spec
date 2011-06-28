/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import javax.interceptor.InvocationContext;

/**
 * Generates cache keys for intercepted method invocations. Implementations MUST be thread-safe
 *
 * @author Eric Dalquist
 * @since 1.7
 */
public interface CacheKeyGenerator {

    /**
     * Called for each intercepted method invocation. Generate a {@link CacheKey} from the {@link InvocationContext} data.
     *
     * @param invocationContext The intercepted method invocation
     * @return A non-null cache key for the invocation.
     */
    CacheKey generateCacheKey(InvocationContext invocationContext);
}
