/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.interceptor.InvocationContext;

/**
 * Creates a {@link DefaultCacheKey} for the {@link InvocationContext}.
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.7
 */
public class DefaultCacheKeyGenerator implements CacheKeyGenerator {

    /**
     *
     * @see javax.cache.interceptor.CacheKeyGenerator#generateCacheKey(javax.interceptor.InvocationContext)
     */
    public CacheKey generateCacheKey(InvocationContext invocationContext) {
        final Object[] parameters = invocationContext.getParameters();
        Annotation[][] parameterAnnotations = invocationContext.getMethod().getParameterAnnotations();
        List<Object> keyParams = null;
        boolean foundKeyParams=false;
        
        int index = 0;
        for (Annotation[] paramAnnotations : parameterAnnotations) {
            for (Annotation ann : paramAnnotations) {
                if (ann.annotationType()==CacheKeyParam.class) {
                    foundKeyParams = true;
                    /* Lazy initialize the keyParams. */
                    if (keyParams==null) {
                        keyParams = new ArrayList<Object>();
                    }
                    keyParams.add(parameters[index]);
                }
            }
            index++;
        }
        
        if (!foundKeyParams) {
            return new DefaultCacheKey(parameters);            
        } else {
            return new DefaultCacheKey(keyParams.toArray(new Object[keyParams.size()]));                        
        }
    }

}
