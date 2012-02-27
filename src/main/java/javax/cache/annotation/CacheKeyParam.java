/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Marks a method argument as part of the cache key.
 * If no arguments are marked all arguments are used. The exception is
 * for a method annotated with {@link CachePut} where the {@link CacheValue}
 * parameter is never included in the key
 * 
 * @author Rick Hightower
 * @since 1.0
 * 
 * @see CacheResult
 * @see CachePut
 * @see CacheRemoveEntry
 * @see CacheKeyInvocationContext#getKeyParameters()
 */
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheKeyParam {

}
