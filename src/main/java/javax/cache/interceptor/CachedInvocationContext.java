/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.util.List;

import javax.interceptor.InvocationContext;

/**
 * An extension of InvocationContext specific to cache annotated methods.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface CachedInvocationContext extends InvocationContext {

    /**
     * @return The list of method parameters that should be considered when generating a cache key
     */
    List<CacheKeyParameter> getCacheKeyParameters();
}
