/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import java.io.Serializable;

/**
 * A {@link Serializable}, immutable, thread-safe object that is used as a cache key.
 * <p/>
 * The implementation MUST follow the Java contract for {@link Object#hashCode()} and
 * {@link Object#equals(Object)} to ensure correct behavior.
 * <p/>
 * It is recommended that implementations also override {@link Object#toString()} and provide a human-readable string
 * representation of the key.
 *
 * @author Eric Dalquist
 * @since 1.0
 * @see CacheKeyGenerator
 */
public interface CacheKey extends Serializable {

    /**
     * The immutable hash code of the cache key.
     * 
     * @return The hash code of the object
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * Compare this {@link CacheKey} with another. If the two objects are equal their {@link #hashCode()} values
     * MUST be equal as well.
     *
     * @param o The other object to compare to.
     * @return true if the objects are equal
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object o);
}
