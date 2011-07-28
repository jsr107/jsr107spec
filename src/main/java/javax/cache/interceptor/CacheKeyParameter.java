/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * A method invocation parameter that should be considered when generating a {@link CacheKey}
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface CacheKeyParameter {
    /**
     * @see Class#getGenericSuperclass()
     */
    Type getBaseType();
    /**
     * @see 
     */
    Class<?> getRawType();
    /**
     * @return Index of the parameter in the array returned by {@link javax.interceptor.InvocationContext#getParameters()}
     */
    int getPosition();
    /**
     * @return The parameter value
     */
    Object getValue();
    /**
     * @return A Set of all Annotations on this method parameter
     */
    Set<Annotation> getAnnotations();
}
