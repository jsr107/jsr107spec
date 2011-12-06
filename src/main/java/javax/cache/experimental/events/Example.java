/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.experimental.events;

import javax.cache.Cache;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public final class Example {
    private static final Logger LOGGER = Logger.getLogger("javax.cache.experimental");

    private Example() {

    }

    /**
     *
     */
    private static final class MyStorageEventDispatcher implements StorageEventDispatcher {
        @Override
        public void addEventInterceptor(String name, EventInterceptor eventInterceptor, Set<Enum> subscriptionTypes) {
            StringBuilder sb = new StringBuilder();
            sb.append("----- adding EventInterceptor ").append(name).append(" for types:");
            for (Enum e : subscriptionTypes) {
                sb.append(" ").append(e);
            }
            LOGGER.info(sb.toString());
        }
        
        @Override
        public void removeEventInterceptor(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<Enum> getSupportedTypes() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     *
     */
    private static final class MyEventInterceptor implements EventInterceptor {
        @Override
        public void onEvent(Event event) {
            Enum type = event.getType();
            if (type == MyEntryEvent.Type.INSERTING) {
                MyEntryEvent event1 = (MyEntryEvent) event;
                Set entries = event1.getEntries();
                LOGGER.info("----1 " + type + " entries=" + entries);
            } else if (type == MyInvocationEvent.Type.EXECUTING) {
                MyInvocationEvent event1 = (MyInvocationEvent) event;
                Set entries = event1.getEntries();
                LOGGER.info("----2 " + type + " entries=" + entries + " processor=" + event1.getEntryProcessor());
            } else {
                LOGGER.info("----3 " + type);
            }
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     */
    private static final class MyEntryEvent<K, V> implements StorageEventDispatcher.EntryEvent<K, V> {
        private final Type type;
        private final HashSet<Cache.Entry<K, V>> entries = new HashSet<Cache.Entry<K, V>>();

        private MyEntryEvent(Type type) {
            this.type = type;
        }
        
        @Override
        public Set<Cache.Entry<K, V>> getEntries() {
            return entries;
        }

        @Override
        public Type getType() {
            return type;
        }

        private MyEntryEvent<K, V> addEntry(Cache.Entry<K, V> entry) {
            entries.add(entry);
            return this;
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     */
    private static final class MyInvocationEvent<K, V> implements StorageEventDispatcher.InvocationEvent<K, V> {
        private final Type type;
        private final Cache.EntryProcessor<K, V> processor;
        private final HashSet<Cache.Entry<K, V>> entries = new HashSet<Cache.Entry<K, V>>();

        private MyInvocationEvent(Type type, Cache.EntryProcessor<K, V> processor) {
            this.type = type;
            this.processor = processor;
        }

        @Override
        public Set<Cache.Entry<K, V>> getEntries() {
            return entries;
        }

        @Override
        public Cache.EntryProcessor<K, V> getEntryProcessor() {
            return processor;
        }

        @Override
        public Type getType() {
            return type;
        }

        private MyInvocationEvent<K, V> addEntry(Cache.Entry<K, V> entry) {
            entries.add(entry);
            return this;
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     */
    private static final class MyEntryProcessor<K, V> implements Cache.EntryProcessor<K, V> {
        @Override
        public Object processAll(Collection<Cache.MutableEntry<? extends K, ? extends V>> mutableEntries) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object process(Cache.MutableEntry<K, V> kvMutableEntry) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     */
    private static final class MyEntry<K, V> implements Cache.Entry<K, V> {
        private final K key;
        private final V value;

        private MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "<" + key + ", " + value + ">";
        }
    }

    private void testDispatcher() {
        HashSet<Enum> events = new HashSet<Enum>();
        events.add(StorageEventDispatcher.EntryEvent.Type.INSERTING);
        events.add(StorageEventDispatcher.InvocationEvent.Type.EXECUTING);
        MyStorageEventDispatcher dispatcher = new MyStorageEventDispatcher();
        MyEventInterceptor interceptor = new MyEventInterceptor();
        dispatcher.addEventInterceptor("myInterceptor", interceptor, events);
    }


    private void testInterceptor() {
        MyEventInterceptor interceptor = new MyEventInterceptor();
        MyEntryEvent<Integer, String> event1 = new MyEntryEvent<Integer, String>(MyEntryEvent.Type.INSERTING);
        event1.addEntry(new MyEntry<Integer, String>(1, "a1"));
        interceptor.onEvent(event1);

        MyEntryProcessor<Integer, String> processor = new MyEntryProcessor<Integer, String>();
        MyInvocationEvent<Integer, String> event2 = new MyInvocationEvent<Integer, String>(MyInvocationEvent.Type.EXECUTING, processor);
        event2.addEntry(new MyEntry<Integer, String>(2, "a2"));
        interceptor.onEvent(event2);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Example example = new Example();

        example.testDispatcher();
        example.testInterceptor();
    }
}
