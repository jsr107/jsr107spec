/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.configuration;

import java.io.Serializable;

/**
 * Constructs and returns a fully configured instance of a specific factory type.
 * <p>
 * Implementations may choose not to construct a new instance, but instead
 * return a previously created instance.
 * <p>
 * Implementations must correctly implement {@link Object#equals(Object)} and
 * {@link Object#hashCode()} as {@link Factory}s are often compared with each
 * other for equivalence.
 *
 * @param <T> the type of factory constructed
 * @author Brian Oliver
 * @since 1.0
 */
public interface Factory<T> extends Serializable {

  /**
   * Constructs and returns a fully configured instance of T.
   *
   * @return an instance of T.
   */
  T create();
}
