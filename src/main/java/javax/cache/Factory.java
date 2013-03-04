/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache;

/**
 * Constructs and returns a fully configured instance of a specific type.
 * <p/>
 * Implementations may choose not to construct a new instance, but instead
 * return a previously created instance.
 * <p/>
 * Implementations must correctly implement {@link Object#equals(Object)} and
 * {@link Object#hashCode()} as {@link Factory}s are often compared with each
 * other for equivalence.
 *
 * @param <T> the type of object constructed
 *
 * @author Brian Oliver
 */
public interface Factory<T> {

    /**
     * Constructs and returns a fully configured instance of T.
     *
     * @return an instance of T.
     */
    T create();
}
