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


