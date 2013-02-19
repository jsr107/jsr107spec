/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * The annotations in this package provide method interceptors for user supplied classes.
 *
 * In the case of a the {@link CacheResult} annotation, if the cache can satisfy the request a result is returned
 * by the method from cache, not from method execution. For the mutative annotations such as {@link CachePut}
 * the annotation allows the cached value to be mutated so that it will be correct the next time {@link CacheResult} is used.
 * <p/>
 * Any operations against a cache via an annotation will have the same behaviour as if the {@link Cache} methods were used. So
 * if the same underlying cache is used for an annotation and a direct API call, the same data would be returned. Annotations
 * therefore provide an additional API for interacting with caches.
 * <p/>
 * In order to use these annotations, you'll need a library or framework which processes these annotations and intercepts calls
 * to your application objects to provide the caching behaviour. This would commonly be provided by a dependency injection framework
 * such as defined by CDI in Java EE.
 *
 *  @author Eric Dalquist
 *  @author Greg Luck
 *  @since 1.0
 */
package javax.cache.annotation;
