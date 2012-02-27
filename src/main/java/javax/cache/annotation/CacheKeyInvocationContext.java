/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import java.lang.annotation.Annotation;

/**
 * Runtime information about an intercepted method invocation for a method annotated
 * with {@link CacheResult}, {@link CachePut}, or {@link CacheRemoveEntry}.
 * <p/>
 * Used with {@link CacheKeyGenerator#generateCacheKey(CacheKeyInvocationContext)} to generate a
 * {@link CacheKey} for the invocation.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 * @param <A> The type of annotation this context information is for. One of {@link javax.cache.annotation.CacheResult},
 * {@link javax.cache.annotation.CachePut}, or {@link javax.cache.annotation.CacheRemoveEntry}
 * @see CacheKeyGenerator
 */
public interface CacheKeyInvocationContext<A extends Annotation> extends CacheInvocationContext<A> {
    
    /**
     * Returns a clone of the array of all method parameters to be used by the {@link CacheKeyGenerator} in creating
     * a {@link CacheKey}. The returned array may be the same as or a subset of the array returned by
     * {@link #getAllParameters()}
     * <p/>
     * Parameters in this array are selected by the following rules:
     * <ul>
     *   <li>If no parameters are annotated with {@link CacheKeyParam} or {@link CacheValue} then all parameters are included</li>
     *   <li>If a {@link CacheValue} annotation exists and no {@link CacheKeyParam} then all parameters except the one annotated with {@link CacheValue} are included</li>
     *   <li>If one or more {@link CacheKeyParam} annotations exist only those parameters with the {@link CacheKeyParam} annotation are included</li>
     * </ul>
     * 
     * @return An array of all parameters to be used in cache key generation
     */
    CacheInvocationParameter[] getKeyParameters();
    
    /**
     * When a method is annotated with {@link CachePut} this is the parameter annotated with {@link CacheValue}
     * 
     * @return The parameter to cache, will never be null for methods annotated with {@link CachePut}, will be null for methods not annotated with {@link CachePut}
     */
    CacheInvocationParameter getValueParameter();
}
