/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 *  Copyright (c) 2011-2016 Terracotta, Inc.
 *  Copyright (c) 2011-2016 Oracle and/or its affiliates.
 *
 *  All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
 * When a method annotated with {@link CacheRemove} is invoked a {@link
 * GeneratedCacheKey} will be generated and {@link Cache#remove(Object)} will be
 * invoked on the specified cache.
 * <p>
 * The default behavior is to call {@link Cache#remove(Object)} after
 * the annotated method is invoked, this behavior can be changed by setting
 * {@link #afterInvocation()} to false in which case
 * {@link Cache#remove(Object)} will be called before the annotated
 * method is invoked.
 * <p>
 * Example of removing a specific Domain object from the "domainCache". A {@link
 * GeneratedCacheKey} will be generated from the String and int parameters and
 * used to call {@link Cache#remove(Object)} after the deleteDomain
 * method completes successfully.
 * <pre><code>
 * package my.app;
 * 
 * public class DomainDao {
 *   &#64;CacheRemove(cacheName="domainCache")
 *   public void deleteDomain(String domainId, int index) {
 *     ...
 *   }
 * }
 * </code></pre>
 * <p>
 * Exception Handling, only used if {@link #afterInvocation()} is true.
 * <ol>
 * <li>If {@link #evictFor()} and {@link #noEvictFor()} are both empty then all
 * exceptions prevent the remove</li>
 * <li>If {@link #evictFor()} is specified and {@link #noEvictFor()} is not
 * specified then only exceptions that pass an instanceof check against the
 * evictFor list result in a remove</li>
 * <li>If {@link #noEvictFor()} is specified and {@link #evictFor()} is not
 * specified then all exceptions that do not pass an instanceof check against the
 * noEvictFor result in a remove</li>
 * <li>If {@link #evictFor()} and {@link #noEvictFor()} are both specified then
 * exceptions that pass an instanceof check against the evictFor list but do not
 * pass an instanceof check against the noEvictFor list result in a remove</li>
 * </ol>
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @author Greg Luck
 * @see CacheKey
 * @since 1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {

  /**
   * The name of the cache.
   * <p>
   * If not specified defaults first to {@link CacheDefaults#cacheName()},
   * and if that is not set then to:
   * package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
   */
  @Nonbinding String cacheName() default "";

  /**
   * When {@link Cache#remove(Object)}  should be called. If true it is called
   * after the annotated method invocation completes successfully. If false it is
   * called before the annotated method is invoked.
   * <p>
   * Defaults to true.
   * <p>
   * If true and the annotated method throws an exception the remove will not be
   * executed.
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
   * Defaults to a key generator that uses
   * {@link java.util.Arrays#deepHashCode(Object[])}
   * and {@link java.util.Arrays#deepEquals(Object[], Object[])} with the array
   * returned by {@link CacheKeyInvocationContext#getKeyParameters()}
   *
   * @see CacheKey
   */
  @Nonbinding Class<? extends CacheKeyGenerator> cacheKeyGenerator()
      default CacheKeyGenerator.class;

  /**
   * Defines zero (0) or more exception {@link Class classes}, that must be a
   * subclass of {@link Throwable}, indicating the exception types that must cause
   * a cache eviction. Only used if {@link #afterInvocation()} is true.
   */
  @Nonbinding Class<? extends Throwable>[] evictFor() default {};

  /**
   * Defines zero (0) or more exception {@link Class Classes}, that must be a
   * subclass of {@link Throwable}, indicating the exception types that must
   * <b>not</b> cause a cache eviction. Only used if {@link #afterInvocation()} is
   * true.
   */
  @Nonbinding Class<? extends Throwable>[] noEvictFor() default {};
}
