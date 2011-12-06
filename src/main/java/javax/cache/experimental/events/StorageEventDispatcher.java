/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.experimental.events;

import javax.cache.Cache;
import java.util.Set;

/**
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface StorageEventDispatcher extends EventDispatcher {
    /**
     *
     * @param <K>
     * @param <V>
     */
    interface EntryEvent<K, V> extends Event {
        /**
         *
         * @return
         */
        Set<Cache.Entry<K, V>> getEntries();

        /**
         *
         */
        static enum Type {
            /**
             *
             */
            INSERTING,
            /**
             *
             */
            INSERTED,
            /**
             *
             */
            UPDATING,
            /**
             *
             */
            UPDATED,
            /**
             *
             */
            REMOVING,
            /**
             *
             */
            REMOVED,
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     */
    interface InvocationEvent<K, V> extends Event {
        /**
         *
         * @return
         */
        Set<Cache.Entry<K, V>> getEntries();

        /**
         *
         * @return
         */
        Cache.EntryProcessor<K, V> getEntryProcessor();

        /**
         *
         */
        static enum Type {
            /**
             *
             */
            EXECUTING,
            /**
             *
             */
            EXECUTED
        }
    }
}
