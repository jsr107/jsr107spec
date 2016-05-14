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

/**
 * The annotations in this package provide method interceptors for user supplied
 * classes.
 * <p>
 * In the case of a the {@link javax.cache.annotation.CacheResult} annotation,
 * if the cache can satisfy the request a result is returned by the method from
 * cache, not from method execution. For the mutative annotations such as
 * {@link javax.cache.annotation.CacheResult} the annotation allows the cached
 * value to be mutated so that it will be correct the next time
 * {@link javax.cache.annotation.CacheResult} is used.
 * <p>
 * Any operations against a cache via an annotation will have the same behaviour
 * as if the {@link javax.cache.annotation.CacheResult} methods were used. So
 * if the same underlying cache is used for an annotation and a direct API call,
 * the same data would be returned. Annotations therefore provide an additional
 * API for interacting with caches.
 * <p>
 * To use these annotations you'll need a library or framework that processes
 * these annotations and intercepts calls to your application objects
 * to provide the caching behaviour. This would commonly be provided by a
 * dependency injection framework such as defined by CDI in Java EE.
 *
 *  @author Eric Dalquist
 *  @author Greg Luck
 *  @since 1.0
 */
package javax.cache.annotation;
