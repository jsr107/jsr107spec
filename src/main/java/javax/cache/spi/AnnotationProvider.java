/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

package javax.cache.spi;

import javax.cache.OptionalFeature;

/**
 * Interface that should be implemented by a cache annotations provider.
 *
 * It is invoked by the {@link javax.cache.Caching} class to determine
 * which annotations related features are supported.
 * <p/>
 * An implementation of this interface must have a public no-arg constructor.
 * <p/>
 * @see javax.cache.Caching
 *
 * @author Eric Dalquist
 * @since 1.0
 */
public interface AnnotationProvider {

    /**
     * Indicates whether an optional feature related to annotations is supported
     * by this implementation.
     * 
     * @param optionalFeature the feature to check for
     * @return true if the feature is supported
     */
    boolean isSupported(OptionalFeature optionalFeature);
}
