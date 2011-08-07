/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Context information about the intercepted method invocation for a method annotated
 * with one of the cache interceptors
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface CacheInvocationContext {
    /**
     * @return The object the intercepted method was invoked on.
     */
    Object getTarget();

    /**
     * @return The method invoked
     */
    Method getMethod();
    
    /**
     * @return An immutable Set of all Annotations on this method
     */
    Set<Annotation> getAnnotations();
    
    /**
     * Returns a clone of the array of all method parameters to be used in cache key generation.
     * The returned array may be the same or a subset of the array returned by {@link #getAllParameters()}
     * <br/>
     * Parameters are selected by the following rules:
     * <ul>
     *   <li>If no parameters are annotated with {@link CacheKeyParam} or {@link CacheValue} then all parameters are included</li>
     *   <li>If a {@link CacheValue} annotation exists and no {@link CacheKeyParam} then all parameters except the one annotated with {@link CacheValue} are included</li>
     *   <li>If one or more {@link CacheKeyParam} annotations exist only those parameters with the annotation are included</li>
     * </ul>
     * 
     * @return An array of all parameters to be used in cache key generation
     */
    CacheInvocationParameter[] getKeyParameters();

    /**
     * Returns a clone of the array of all method parameters. Implementors should use {@link #getKeyParameters()}
     * for the parameters to use when generating a cache key.
     * 
     * @return An array of all parameters generation
     */
    CacheInvocationParameter[] getAllParameters();
    
    /**
     * When a method is annotated with {@link CachePut} a {@link CacheValue} parameter annotation may be present 
     * 
     * @return The parameter annotated with {@link CacheValue}, if no parameter is annotated with {@link CacheValue} null is returned.
     */
    CacheInvocationParameter getValueParameter();

    /**
     * If available, the native context object is returned. For example javax.interceptor.InvocationContext for CDI or
     * org.aopalliance.intercept.MethodInvocation for AOPAlliance. NULL may be returned if the native context is not
     * available for an implementation.
     * 
     * @return The native context object, or null if none is available.
     */
    Object getNativeContext();
}
