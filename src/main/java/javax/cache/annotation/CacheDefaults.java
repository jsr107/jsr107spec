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

import javax.cache.CacheManager;
import javax.enterprise.util.Nonbinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;


/**
 * Allows the configuration of defaults for {@link CacheResult}, {@link CachePut},
 * {@link CacheRemove}, and {@link CacheRemoveAll} at the class level. Without
 * the method level annotations this annotation has no effect.
 * <p>
 * Following is an example of specifying a default cache name that is used by
 * the annotations on the getDomain and deleteDomain methods. The annotation for
 * getAllDomains would use the "allDomains" cache name specified in the method
 * level annotation.
 * <pre><code>
 * package my.app;
 * 
 * &#64;CacheDefaults(cacheName="domainCache")
 * public class DomainDao {
 *   &#64;CacheResult
 *   public Domain getDomain(String domainId, int index) {
 *     ...
 *   }
 * 
 *   &#64;CacheRemove
 *   public void deleteDomain(String domainId, int index) {
 *     ...
 *   }
 * 
 *   &#64;CacheResult(cacheName="allDomains")
 *   public List&lt;Domain&gt; getAllDomains() {
 *     ...
 *   }
 * }
 * </code></pre>
 *
 * @author Rick Hightower
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheDefaults {

  /**
   * The default name of the cache for the annotated class
   * <p>
   * If not specified defaults to:
   * package.name.ClassName.methodName(package.ParameterType,package.ParameterType)
   * <p>
   * Applicable for {@link CacheResult}, {@link CachePut}, {@link CacheRemove},
   * and {@link CacheRemoveAll}
   */
  @Nonbinding String cacheName() default "";

  /**
   * The {@link CacheResolverFactory} used to find the {@link CacheResolver} to
   * use at runtime.
   * <p>
   * The default resolver pair will resolve the cache by name from the default
   * {@link CacheManager}
   * <p>
   * Applicable for {@link CacheResult}, {@link CachePut}, {@link CacheRemove},
   * and {@link CacheRemoveAll}
   */
  @Nonbinding Class<? extends CacheResolverFactory> cacheResolverFactory()
      default CacheResolverFactory.class;

  /**
   * The {@link CacheKeyGenerator} to use to generate the
   * {@link GeneratedCacheKey} for interacting with the specified Cache.
   * <p>
   * Defaults to a key generator that uses {@link Arrays#deepHashCode(Object[])}
   * and {@link Arrays#deepEquals(Object[], Object[])} with the array returned by
   * {@link CacheKeyInvocationContext#getKeyParameters()}
   * </p>
   * Applicable for {@link CacheResult}, {@link CachePut}, and {@link CacheRemove}
   *
   * @see CacheKey
   */
  @Nonbinding Class<? extends CacheKeyGenerator> cacheKeyGenerator()
      default CacheKeyGenerator.class;
}
