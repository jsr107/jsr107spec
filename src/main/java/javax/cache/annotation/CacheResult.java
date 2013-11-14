/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.annotation;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.enterprise.util.Nonbinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * When a method annotated with {@link CacheResult} is invoked a
 * {@link GeneratedCacheKey} will be generated and
 * {@link Cache#get(Object)} is called before the annotated method
 * actually executes. If a value is found in the cache it is returned and the
 * annotated method is never actually executed. If no value is found the
 * annotated method is invoked and the returned value is stored in the cache
 * with the generated key.
 * <p>
 * null return values are cached by default, this behavior can be disabled by
 * setting the {@link #cacheNull()} property to false.
 * <p>
 * Exceptions are not cached by default. Caching of exceptions can be enabled by
 * specifying an {@link #exceptionCacheName()}. If an exception cache is specified
 * it is checked before invoking the annotated method and if a cached exception is
 * found it is re-thrown.
 * <p>
 * The {@link #cachedExceptions()} and {@link #nonCachedExceptions()} properties
 * can be used to control which exceptions are cached and which are not.
 * <p>
 * To always invoke the annotated method and still cache the result set
 * {@link #skipGet()} to true. This will disable the pre-invocation
 * {@link Cache#get(Object)} call. If {@link #exceptionCacheName()} is
 * specified the pre-invocation exception check is also disabled. This feature is
 * useful for methods that create or update objects to be cached.
 * <p>
 * Example of caching the Domain object with a key generated from the
 * <code>String</code> and <code>int</code> parameters.
 * <p>
 * With no {@link #cacheName()} specified a cache name of
 * "my.app.DomainDao.getDomain(java.lang.String,int)" will be generated.
 * <p><pre><code>
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
 * Example using the {@link GeneratedCacheKey} annotation so that only the domainId
 * parameter is used in key generation:
 * <pre><code>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheResult
 *   public Domain getDomain(@CacheKey String domainId, Monitor mon) {
 *     ...
 *   }
 * }
 * </code></pre>
 * <p>
 * If exception caching is enabled via specification of
 * {@link #exceptionCacheName()} the following rules are used to determine if a
 * thrown exception is cached:
 * <ol>
 * <li>If {@link #cachedExceptions()} and {@link #nonCachedExceptions()} are both
 * empty then all exceptions are cached</li>
 * <li>If {@link #cachedExceptions()} is specified and
 * {@link #nonCachedExceptions()} is not specified then only exceptions
 * which pass an instanceof check against the cachedExceptions list are cached</li>
 * <li>If {@link #nonCachedExceptions()} is specified and
 * {@link #cachedExceptions()} is not specified then all exceptions
 * which do not pass an instanceof check against the nonCachedExceptions list are
 * cached</li>
 * <li>If {@link #cachedExceptions()} and {@link #nonCachedExceptions()} are both
 * specified then exceptions which pass an instanceof check against the
 * cachedExceptions list but do not pass an instanceof check against the
 * nonCachedExceptions list are cached</li>
 * </ol>
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @see CacheKey
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheResult {

  /**
   * The name of the cache.
   * <p>
   * If not specified defaults first to {@link CacheDefaults#cacheName()} an if
   * that is not set it defaults to:
   * package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
   */
  @Nonbinding String cacheName() default "";

  /**
   * If set to true the pre-invocation {@link Cache#get(Object)} is
   * skipped and the annotated method is always executed with the returned value
   * being cached as normal. This is useful for create or update methods which
   * should always be executed and have their returned value placed in the cache.
   * <p>
   * If true and an {@link #exceptionCacheName()} is specified the pre-invocation
   * check for a thrown exception is also skipped. If an exception is thrown during
   * invocation it will be cached following the standard exception caching rules.
   * <p>
   * Defaults to false.
   *
   * @see CachePut
   */
  @Nonbinding boolean skipGet() default false;

  /**
   * If set to false null return values will not be cached. If true (the default)
   * null return values will be cached.
   * <p>
   * Defaults to true.
   */
  @Nonbinding boolean cacheNull() default true;

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
   * The {@link CacheKeyGenerator} to use to generate the {@link GeneratedCacheKey}
   * for interacting with the specified Cache.
   * <p>
   * Defaults to a key generator that uses
   * {@link java.util.Arrays#deepHashCode(Object[])} and
   * {@link java.util.Arrays#deepEquals(Object[], Object[])} with the array
   * returned by {@link CacheKeyInvocationContext#getKeyParameters()}
   *
   * @see CacheKey
   */
  @Nonbinding Class<? extends CacheKeyGenerator> cacheKeyGenerator()
      default CacheKeyGenerator.class;

  /**
   * The name of the cache to cache exceptions.
   * <p>
   * If not specified no exception caching is done.
   */
  @Nonbinding String exceptionCacheName() default "";

  /**
   * Defines zero (0) or more exception {@link Class classes}, which must be a
   * subclass of {@link Throwable}, indicating which exception types which
   * <b>must</b> be cached. Only consulted if {@link #exceptionCacheName()} is
   * specified.
   */
  @Nonbinding Class<? extends Throwable>[] cachedExceptions() default {};

  /**
   * Defines zero (0) or more exception {@link Class Classes}, which must be a
   * subclass of {@link Throwable}, indicating which exception types
   * <b>must not</b> be cached. Only consulted if {@link #exceptionCacheName()}
   * is specified.
   */
  @Nonbinding Class<? extends Throwable>[] nonCachedExceptions() default {};
}
