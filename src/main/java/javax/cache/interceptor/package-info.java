/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 This package contains annotations for adding caching interceptors to POJOs.

 For an end user not using annotations, there is no compile time dependency on CDI.
 <p/>
 For a user wishing to use an alternative annotations implementation there is also no dependency on CDI.

 For 0.2
 -------------------

 todo

    Create new @CachePut

    @CachePut(afterInvocation=true)
    public void updateFoo(String id, @CacheValue Foo foo)


 todo
     rename CachedInvocationContext to CacheInvocationContext
     rename CachingDefaults to CacheDefaults

 todo

        Remove InterceptorBinding

 todo
        Clean up Javadoc and add samples to Javadoc - Eric


  After 0.2
 ---------------------

 todo

    try and remove InvocationContext and pull up methods into CacheInvocationContext. Improve performance of annotations.
    Write up a summary of why the world changed for Rick.


 todo

    Then add excludes to the dependency


 todo

    Remove ElementType.TYPE from @Target( {ElementType.METHOD, ElementType.TYPE} )

    You cannot use it as a class annotation so it is confusing to users.






 @author Greg Luck
 @since 1.0
 */
package javax.cache.interceptor;
