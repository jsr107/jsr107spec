/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.lang.annotation.Annotation;

/**
 * Runtime information about an intercepted method invocation for a method annotated
 * with {@link CacheResult}, {@link CachePut}, {@link CacheRemoveEntry}, {@link CacheRemoveAll}
 * <p/>
 * Used with {@link CacheResolver#resolveCache(CacheInvocationContext)} to determine the {@link javax.cache.Cache} to use
 * at runtime for the method invocation.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 * @param <A> The caching annotation this context is for
 * @see CacheResolver
 */
public interface CacheInvocationContext<A extends Annotation> extends CacheMethodDetails<A> {
    /**
     * @return The object the intercepted method was invoked on.
     */
    Object getTarget();

    /**
     * Returns a clone of the array of all method parameters.
     * 
     * @return An array of all parameters generation
     */
    CacheInvocationParameter[] getAllParameters();

    /**
     * Return an object of the specified type to allow access to the provider-specific API. If the provider's
     * implementation does not support the specified class, the {@link IllegalArgumentException} is thrown. 
     * 
     * @param cls he class of the object to be returned. This is normally either the underlying implementation class or an interface that it implements. 
     * @return an instance of the specified class 
     * @throws IllegalArgumentException if the provider doesn't support the specified class.
     */
    <T> T unwrap(java.lang.Class<T> cls);
}
