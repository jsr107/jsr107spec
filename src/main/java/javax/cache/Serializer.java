/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache;

/**
 * Tagging interface for a binary representation of a value or key.
 * A vendor implementation may be a wrapper of a byte array, an interface to a stream or to a NIO buffer.
 *
 * @param <V> the type of cached values
 * @author Yannis Cosmadopoulos
 * @since 1.7
 */
public interface Serializer<V> {
    /**
     * Convert a value to a binary.
     *
     * @param value the value
     * @return binary representation of value
     * @throws CacheException is an error occurred during serialization
     * @throws NullPointerException if value is null
     */
    Binary<V> createBinary(V value);

    /**
     * Internal storage
     *
     * @param <V> type being stored
     */
    interface Binary<V> {
        /**
         * Get the stored value
         *
         * @return the value
         * @throws CacheException if an error occurred during de-serialization or if binary is not
         * a Binary obtained from a call to keyToBinary of a compatible serializer.
         */
        V get();
    }
}
