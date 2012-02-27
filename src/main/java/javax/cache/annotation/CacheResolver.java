/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.annotation;

import javax.cache.Cache;
import java.lang.annotation.Annotation;

/**
 * Determines the {@link Cache} to use for an intercepted method invocation.
 * <p/>
 * Implementations MUST be thread-safe.
 *
 * @author Eric Dalquist
 * @since 1.0
 * @see CacheResolverFactory
 */
public interface CacheResolver {

    /**
     * Resolve the {@link Cache} to use for the {@link CacheInvocationContext}.
     * 
     * @param cacheInvocationContext The context data for the intercepted method invocation
     * @return The {@link Cache} instance to be used by the intercepter
     */
    <K, V> Cache<K, V> resolveCache(CacheInvocationContext<? extends Annotation> cacheInvocationContext);
    
}
