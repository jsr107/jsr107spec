/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.configuration;

import java.io.Serializable;

/**
 * A factory for creating from a known instance, used for testing.
 * @author Greg Luck
 */
public class InstanceFactoryBuilder {


  /**
   * Constructs a {@link javax.cache.configuration.Factory} that will return the specified factory instance.
   *
   * @param instance the Serializable instance the {@link javax.cache.configuration.Factory} will return
   * @param <T>      the type of the instances returned
   * @return a {@link javax.cache.configuration.Factory} for the instance
   */
  public static <T extends Serializable> Factory<T> factoryOf(T instance) {
    return new SingletonFactory<T>(instance);
  }

  /**
   * A {@link javax.cache.configuration.Factory} that always returns a specific instance. ie: the
   * factory returns a singleton, regardless of the number of times
   * {@link javax.cache.configuration.Factory#create()} is called.
   *
   * @param <T> the type of the instance produced by the {@link javax.cache.configuration.Factory}
   */
  public static class SingletonFactory<T> implements Factory<T>, Serializable {

    /**
     * The serialVersionUID required for {@link java.io.Serializable}.
     */
    public static final long serialVersionUID = 201305101634L;

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
