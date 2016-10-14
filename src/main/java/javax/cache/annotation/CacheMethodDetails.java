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
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Static information about a method annotated with one of:
 * {@link CacheResult}, {@link CachePut}, {@link CacheRemove}, or {@link
 * CacheRemoveAll}
 * <p>
 * Used with {@link CacheResolverFactory#getCacheResolver(CacheMethodDetails)} to
 * determine the {@link CacheResolver} to use with the method.
 *
 * @param <A> The type of annotation this context information is for. One of
 *            {@link CacheResult}, {@link CachePut}, {@link CacheRemove}, or
 *            {@link CacheRemoveAll}.
 * @author Eric Dalquist
 * @see CacheResolverFactory
 */
public interface CacheMethodDetails<A extends Annotation> {
  /**
   * The annotated method
   *
   * @return The annotated method
   */
  Method getMethod();

  /**
   * An immutable Set of all Annotations on this method
   *
   * @return An immutable Set of all Annotations on this method
   */
  Set<Annotation> getAnnotations();

  /**
   * The caching related annotation on the method.
   * One of: {@link CacheResult}, {@link CachePut}, {@link CacheRemove}, or
   * {@link CacheRemoveAll}
   *
   * @return The caching related annotation on the method.
   */
  A getCacheAnnotation();

  /**
   * The cache name resolved by the implementation.
   * <p>
   * The cache name is determined by first looking at the cacheName attribute of
   * the method level annotation. If that attribute is not set then the class
   * level {@link CacheDefaults} annotation is checked. If that annotation does
   * not exist or does not have its cacheName attribute set then the following
   * cache name generation rules are followed:
   * <p>
   * "fully qualified class name"."method name"("fully qualified parameter class
   * names")
   * <p>
   * For example:
   * <pre><code>
   * package my.app;
   * 
   * public class DomainDao {
   *   &#64;CacheResult
   *   public Domain getDomain(String domainId, int index) {
   *     ...
   *   }
   * }
   * </code></pre>
   * <p>
   * Results in the cache name: "my.app.DomainDao.getDomain(java.lang.String,int)"
   *
   * @return The fully resolved cache name
   */
  String getCacheName();
}


