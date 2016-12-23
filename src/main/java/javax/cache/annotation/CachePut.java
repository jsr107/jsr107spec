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

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;


/**
 * When a method annotated with {@link CachePut} is invoked a {@link
 * GeneratedCacheKey} will be generated and {@link Cache#put(Object,
 * Object)} will be invoked on the specified cache storing the value marked with
 * {@link CacheValue}.
 * <p>
 * The default behavior is to call {@link Cache#put(Object, Object)}
 * after the annotated method is invoked, this behavior can be changed by setting
 * {@link #afterInvocation()} to false in which case
 * {@link Cache#put(Object, Object)} will be called before the annotated method is
 * invoked.
 * <p>
 * Example of caching the Domain object with a key generated from the String and
 * int parameters. The {@link CacheValue} annotation is used to designate which
 * parameter should be stored in the "domainDao" cache.
 * <pre><code>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CachePut(cacheName="domainCache")
 *   public void updateDomain(String domainId, int index, &#64;CacheValue Domain
 * domain) {
 *     ...
 *   }
 * }
 * </code></pre>
 * <p>
 * Exception Handling, only used if {@link #afterInvocation()} is true.
 * <ol>
 * <li>If {@link #cacheFor()} and {@link #noCacheFor()} are both empty then all
 * exceptions prevent the put</li>
 * <li>If {@link #cacheFor()} is specified and {@link #noCacheFor()} is not
 * specified then only exceptions that pass an instanceof check against the
 * cacheFor list result in a put</li>
 * <li>If {@link #noCacheFor()} is specified and {@link #cacheFor()} is not
 * specified then all exceptions that do not pass an instanceof check against the
 * noCacheFor result in a put</li>
 * <li>If {@link #cacheFor()} and {@link #noCacheFor()} are both specified then
 * exceptions that pass an instanceof check against the cacheFor list but do not
 * pass an instanceof check against the noCacheFor list result in a put</li>
 * </ol>
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @see CacheValue
 * @see CacheKey
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CachePut {

  /**
   * The name of the cache.
   * <p>
   * If not specified defaults first to {@link CacheDefaults#cacheName()} and if
   * that is not set it defaults to:
   * package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
   */
  @Nonbinding String cacheName() default "";

  /**
   * When {@link Cache#put(Object, Object)} should be called. If true it is called
   * after the annotated method invocation completes successfully. If false it is
   * called before the annotated method is invoked.
   * <p>
   * Defaults to true.
   * <p>
   * If true and the annotated method throws an exception the rules governing
   * {@link #cacheFor()} and {@link #noCacheFor()} will be followed.
   */
  @Nonbinding boolean afterInvocation() default true;

  /**
   * The {@link CacheResolverFactory} used to find the {@link CacheResolver} to
   * use at runtime.
   * <p>
   * The default resolver pair will resolve the cache by name from the default
   * {@link CacheManager}
   */
  @Nonbinding Class<? extends CacheResolverFactory> cacheResolverFactory()
      default CacheResolverFactory.class;

  /**
   * The {@link CacheKeyGenerator} to use to generate the {@link
   * GeneratedCacheKey} for interacting with the specified Cache.
   * <p>
   * Defaults to a key generator that uses {@link Arrays#deepHashCode(Object[])}
   * and {@link Arrays#deepEquals(Object[], Object[])} with the array
   * returned by {@link CacheKeyInvocationContext#getKeyParameters()}
   *
   * @see CacheKey
   */
  @Nonbinding Class<? extends CacheKeyGenerator> cacheKeyGenerator()
      default CacheKeyGenerator.class;

  /**
   * Defines zero (0) or more exception {@link Class classes}, that must be a
   * subclass of {@link Throwable}, indicating the exception types that <b>must</b>
   * cause the parameter to be cached. Only used if {@link #afterInvocation()} is
   * true.
   */
  @Nonbinding Class<? extends Throwable>[] cacheFor() default {};

  /**
   * Defines zero (0) or more exception {@link Class Classes}, which must be a
   * subclass of {@link Throwable}, indicating which exception types <b>must
   * not</b> cause the parameter to be cached. Only used if
   * {@link #afterInvocation()} is true.
   */
  @Nonbinding Class<? extends Throwable>[] noCacheFor() default {};
}
