/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache;

/**
 * This class exclusively defines static methods to aid in the construction
 * and manipulation of {@link Factory} instances.
 *
 * @author Brian Oliver
 */
public final class Factories {

    /**
     * A private constructor to prevent instantiation.
     */
    private Factories() {
        //deliberately empty - no instances allowed!
    }

    /**
     * Constructs a {@link Factory} that will produce instances of the
     * specified class.
     * <p/>
     * Assumes the specified class as a no-args constructor.
     *
     * @param clazz  the class of instances to be produced by the returned
     *               {@link Factory}
     * @param <T>    the type of the instances produced by the {@link Factory}
     * @return a {@link Factory} for the specified clazz
     */
    public static <T> Factory<T> of(Class<T> clazz) {
        return new ClassFactory<T>(clazz);
    }


    /**
     * Constructs a {@link Factory} that will return the specified instance.
     *
     * @param instance  the instance the {@link Factory} will return
     * @param <T>       the type of the instances returned
     * @return a {@link Factory} for the instance
     */
    public static <T> Factory<T> of(T instance) {
        return new SingletonFactory<T>(instance);
    }


    /**
     * A {@link Factory} that instantiates a specific Class.
     *
     * @param <T> the type of the instance produced by the {@link Factory}
     */
    public static class ClassFactory<T> implements Factory<T> {

        /**
         * The Class of objects to instantiate when required.
         */
        private Class<T> clazz;

        /**
         * Constructor for the {@link ClassFactory}.
         *
         * @param clazz the Class to instantiate
         */
        public ClassFactory(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T create() {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create an instance of " + clazz, e);
            }
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;

            ClassFactory that = (ClassFactory) other;

            if (!clazz.equals(that.clazz)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }
    }

    /**
     * A {@link Factory} that always returns a specific instance. ie: the
     * factory returns a singleton, regardless of the number of times
     * {@link javax.cache.Factory#create()} is called.
     *
     * @param <T> the type of the instance produced by the {@link Factory}
     */
    public static class SingletonFactory<T> implements Factory<T> {

        /**
         * The singleton instance.
         */
        private T instance;

        /**
         * Constructor for the {@link SingletonFactory}.
         *
         * @param instance the instance to return
         */
        public SingletonFactory(T instance) {
            this.instance = instance;
        }

        @Override
        public T create() {
            return instance;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;

            SingletonFactory that = (SingletonFactory) other;

            if (!instance.equals(that.instance)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return instance.hashCode();
        }
    }
}
