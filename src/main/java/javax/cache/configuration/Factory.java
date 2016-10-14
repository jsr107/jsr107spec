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
