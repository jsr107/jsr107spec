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
