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

  After 0.2
 ---------------------

 todo

    Remove ElementType.TYPE from @Target( {ElementType.METHOD, ElementType.TYPE} )

    You cannot use it as a class annotation so it is confusing to users.






 @author Greg Luck
 @since 1.0
 */
package javax.cache.annotation;
