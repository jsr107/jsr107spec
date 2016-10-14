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
package javax.cache.annotation;

import java.lang.annotation.Annotation;

/**
 * Runtime information about an intercepted method invocation for a method
 * annotated with {@link CacheResult}, {@link CachePut}, {@link CacheRemove},
 * or {@link CacheRemoveAll}
 * <p>
 * Used with {@link CacheResolver#resolveCache(CacheInvocationContext)} to
 * determine the {@link javax.cache.Cache} to use at runtime for the method
 * invocation.
 *
 * @param <A> The type of annotation this context information is for. One of
 *            {@link CacheResult}, {@link CachePut}, {@link CacheRemove}, or {@link
 *            CacheRemoveAll}.
 * @author Eric Dalquist
 * @see CacheResolver
 */
public interface CacheInvocationContext<A extends Annotation>
    extends CacheMethodDetails<A> {

  /**
   * @return The object the intercepted method was invoked on.
   */
  Object getTarget();

  /**
   * Returns a clone of the array of all method parameters.
   *
   * @return An array of all parameters for the annotated method
   */
  CacheInvocationParameter[] getAllParameters();

  /**
   * Return an object of the specified type to allow access to the
   * provider-specific API. If the provider's
   * implementation does not support the specified class, the {@link
   * IllegalArgumentException} is thrown.
   *
   * @param <T> The type of the expected underlying {@link javax.cache.Cache} implementation.
   * @param cls the class of the object to be returned. This is normally either the
   *            underlying implementation class or an interface that it implements.
   * @return an instance of the specified class
   * @throws IllegalArgumentException if the provider doesn't support the specified
   *            class.
   */
  <T> T unwrap(java.lang.Class<T> cls);
}
