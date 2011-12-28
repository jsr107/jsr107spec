/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */


package javax.cache.annotation;


/**
 * Static information about a method annotated with: {@link CacheResult}
 * <p/>
 * Used with {@link CacheResolverFactory#getExceptionCacheResolver(CacheResultMethodDetails)} to determine the {@link CacheResolver} to use
 * with the method. 
 * 
 * @author Eric Dalquist
 * @version $Revision$
 * @see CacheResolverFactory
 */
public interface CacheResultMethodDetails extends CacheMethodDetails<CacheResult> {
    /**
     * The exception cache name resolved by the implementation.
     * <p/> 
     * The exception cache name is determined by first looking at the exceptionCacheName attribute of the method level
     * annotation. If that attribute is not set then the class level {@link CacheDefaults} annotation is checked. If
     * that annotation does not exist or does not have its exceptionCacheName attribute set no exception caching is done.
     * 
     * @return The fully resolved cache name, null if no exceptionCacheName is configured
     */
    String getExceptionCacheName();
}
