/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.interceptor;

import java.io.Serializable;

/**
 * A serializable, immutable, thread-safe object that is used as a cache key.
 * <p/>
 * The implementation MUST follow the Java contract for {@link Object#hashCode()} and
 * {@link Object#equals(Object)} to ensure correct behavior.
 *
 * @author Eric Dalquist
 * @since 1.0
 */
public interface CacheKey extends Serializable {

    /**
     * The immutable hash code of the object.
     */
    @Override
    int hashCode();

    /**
     * Compare this {@link CacheKey} with another. If the two objects are equal their {@link #hashCode()} values
     * MUST be equal as well.
     *
     * @param o The other object to compare to.
     * @return true if the objects are equal
     */
    @Override
    boolean equals(Object o);
}
