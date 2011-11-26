/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache;

/**
 * Allows execution of code which may mutate a cache entry with exclusive
 * access (including reads) to that entry.
 * <p/>
 * This enables a way to perform compound operations without transactions
 * involving a cache entry atomically. Such operations may include mutations.
 * <p/>
 * The mutations can be potentially done in place avoiding expensive network transfers.
 * An example is a value which is a list and you simply want to append a new element to it.
 * <p/>
 * An entry processor cannot invoke any cache operations, including processor operations.
 * <p/>
 * If executed in a JVM remote from the one invoke was called in, an EntryProcessor equal
 * to the local one will execute the invocation. For remote to execution to succeed, the
 * EntryProcessor implementation class must be in the excecuting class loader as must K and
 * V if {@link javax.cache.EntryProcessor.Entry#getKey()} or {@link javax.cache.EntryProcessor.Entry#getValue()}
 * is invoked.
 *
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * @author Greg Luck
 * @author Yannis Cosmadopoulos
 */
public interface EntryProcessor<K, V> {

    //todo add processAll

    /**
     * Process an entry
     * @param entry the entry
     * @return the result
     */
    Object process(Entry<K, V> entry);

    /**
     * An accessor and mutator to the underlying Cache
     * @param <K>
     * @param <V>
     */
    public interface Entry<K, V> extends Cache.Entry<K, V> {

        /**
         * Checks for the existence of the entry in the cache
         * @return
         */
        boolean exists();

        /**
         * Removes the entry from the Cache
         */
        void remove();

        /**
         * Sets or replaces the value associated with the key
         * If {@link #exists} is false and setValue is called
         * then a mapping is added to the cache visible once the EntryProcessor
         * completes. Moreover a second invocation of {@link #exists()}
         * will return true.
         * @param value
         */
        void setValue(V value);
    }
}
