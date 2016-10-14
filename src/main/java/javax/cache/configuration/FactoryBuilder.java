/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.cache.configuration;

import java.io.Serializable;

/**
 * A convenience class that defines generically typed static methods to aid in
 * the building of {@link Factory} instances.
 * <p>
 * {@link Factory} is used by {@link MutableConfiguration} to avoid adding
 * non-Serializable instances that would assume usage in the local JVM.
 * <p>
 * Two styles of builder are available:
 * <ul>
 *   <li>those taking a Class or className. A new instance will be created by
 *   the {@link Factory}
 *   </li>
 *   <li>those taking a Serializable instance. That instance will be created
 *   by the {@link Factory}. As the instance is Serializable it no assumption of
 *   usage in the local JVM is implied.
 *   </li>
 * </ul>
 *
 * Factory instances can also be created in other ways.
 *
 * @author Brian Oliver
 * @author Greg Luck
 * @since 1.0
 */
public final class FactoryBuilder {

  /**
   * A private constructor to prevent instantiation.
   */
  private FactoryBuilder() {
    //deliberately empty - no instances allowed!
  }

  /**
   * Constructs a {@link Factory} that will produce factory instances of the
   * specified class.
   * <p>
   * The specified class must have a no-args constructor.
   *
   * @param clazz the class of instances to be produced by the returned
   *              {@link Factory}
   * @param <T>   the type of the instances produced by the {@link Factory}
   * @return a {@link Factory} for the specified clazz
   */
  public static <T> Factory<T> factoryOf(Class<T> clazz) {
    return new ClassFactory<T>(clazz);
  }

  /**
   * Constructs a {@link Factory} that will produce factory instances of the
   * specified class.
   * <p>
   * The specified class must have a no-args constructor.
   *
   * @param className the class of instances to be produced by the returned
   *                  {@link Factory}
   * @param <T>       the type of the instances produced by the {@link Factory}
   * @return          a {@link Factory} for the specified clazz
   */
  public static <T> Factory<T> factoryOf(String className) {
    return new ClassFactory<T>(className);
  }

  /**
   * Constructs a {@link Factory} that will return the specified factory
   * Serializable instance.
   * <p>
   * If T is not Serializable use {@link #factoryOf(Class)} or
   * {@link #factoryOf(String)}.
   *
   * @param instance the Serializable instance the {@link Factory} will return
   * @param <T>      the type of the instances returned
   * @return a {@link Factory} for the instance
   */
  public static <T extends Serializable> Factory<T> factoryOf(T instance) {
    return new SingletonFactory<T>(instance);
  }


  /**
   * A {@link Factory} that instantiates a specific Class.
   *
   * @param <T> the type of the instance produced by the {@link Factory}
   */
  public static class ClassFactory<T> implements Factory<T>, Serializable {

    /**
     * The serialVersionUID required for {@link Serializable}.
     */
    public static final long serialVersionUID = 201305101626L;

    /**
     * The name of the Class.
     */
    private String className;

    /**
     * Constructor for the {@link ClassFactory}.
     *
     * @param clazz the Class to instantiate
     */
    public ClassFactory(Class<T> clazz) {
      this.className = clazz.getName();
    }

    /**
     * Constructor for the {@link ClassFactory}.
     *
     * @param className the name of the Class to instantiate
     */
    public ClassFactory(String className) {
      this.className = className;
    }

    @Override
    public T create() {
      try {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        Class<?> clazz = loader.loadClass(className);

        return (T) clazz.newInstance();
      } catch (Exception e) {
        throw new RuntimeException("Failed to create an instance of " + className, e);
      }
    }

    @Override
    public boolean equals(Object other) {
      if (this == other) return true;
      if (other == null || getClass() != other.getClass()) return false;

      ClassFactory that = (ClassFactory) other;

      if (!className.equals(that.className)) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return className.hashCode();
    }
  }

  /**
   * A {@link Factory} that always returns a specific instance. ie: the
   * factory returns a singleton, regardless of the number of times
   * {@link Factory#create()} is called.
   *
   * @param <T> the type of the instance produced by the {@link Factory}
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
