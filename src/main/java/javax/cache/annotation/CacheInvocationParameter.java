/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * A parameter to an intercepted method invocation. Contains the parameter value as well
 * static type and annotation information about the parameter.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface CacheInvocationParameter {

    /**
     * The parameter type as declared on the method.
     */
    Class<?> getRawType();

    /**
     * @return The parameter value
     */
    Object getValue();

    /**
     * @return An immutable Set of all Annotations on this method parameter, never null.
     */
    Set<Annotation> getAnnotations();
    
    /**
     * The index of the parameter in the original parameter array as returned by {@link CacheInvocationContext#getAllParameters()}
     * 
     * @return The index of the parameter in the original parameter array.
     */
    int getParameterPosition();
}
