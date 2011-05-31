/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.interceptor;

import java.util.Arrays;

/**
 * Default cache key implementation. Keeps a reference to a copy of the entire parameter array from
 * {@link javax.interceptor.InvocationContext#getParameters()} and uses {@link Arrays#deepHashCode(Object[])} to
 * implement {@link #hashCode()} and {@link Arrays#deepEquals(Object[], Object[])} to implement
 * {@link #equals(Object)}
 *
 * @author Eric Dalquist
 */
public class DefaultCacheKey implements CacheKey {

    private static final long serialVersionUID = 1L;

    private final Object[] parameters;
    private final int hashCode;

    /**
     * Constructs a default cache key
     *
     * @param parameters the paramters to use
     */
    public DefaultCacheKey(Object[] parameters) {
        this.parameters = parameters;
        this.hashCode = Arrays.deepHashCode(parameters);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DefaultCacheKey other = (DefaultCacheKey) obj;
        if (!Arrays.deepEquals(this.parameters, other.parameters))
            return false;
        return true;
    }
}
