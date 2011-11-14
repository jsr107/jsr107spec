/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Static information about a method annotated with one of:
 * {@link CacheResult}, {@link CachePut}, {@link CacheRemoveEntry}, or {@link CacheRemoveAll}
 * <p/>
 * Used with {@link CacheResolverFactory#getCacheResolver(CacheMethodDetails)} to determine the {@link CacheResolver} to use
 * with the method. 
 * 
 * @author Eric Dalquist
 * @version $Revision$
 * @param <A> The type of annotation this context information is for. One of {@link javax.cache.annotation.CacheResult},
 * {@link javax.cache.annotation.CachePut}, {@link javax.cache.annotation.CacheRemoveEntry}, or
 * {@link javax.cache.annotation.CacheRemoveAll}.
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
     * One of: {@link CacheResult}, {@link CachePut}, {@link CacheRemoveEntry}, or {@link CacheRemoveAll}
     * 
     * @return The caching related annotation on the method.
     */
    A getCacheAnnotation();
    
    /**
     * The cache name resolved by the implementation.
     * <p/> 
     * The cache name is determined by first looking at the cacheName attribute of the method level annotation. If
     * that attribute is not set then the class level {@link CacheDefaults} annotation is checked. If that annotation
     * does not exist or does not have its cacheName attribute set then the following cache name generation rules are
     * followed:
     * <p/>
     * "fully qualified class name"."method name"("fully qualified parameter class names")
     * <p/>
     * For example:
     * <p><blockquote><pre>
     * package my.app;
     * 
     * public class DomainDao {
     *   &#64;CacheResult
     *   public Domain getDomain(String domainId, int index) {
     *     ...
     *   }
     * }
     * </pre></blockquote></p>
     * Results in the cache name: "my.app.DomainDao.getDomain(java.lang.String,int)"
     * 
     * @return The fully resolved cache name
     */
    String getCacheName();
}
