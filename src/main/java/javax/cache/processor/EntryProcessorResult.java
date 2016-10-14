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
package javax.cache.processor;

import javax.cache.CacheException;

/**
 * A mechanism to represent and obtain the result of processing
 * a {@link javax.cache.Cache} entry using an {@link EntryProcessor}.
 *
 * @param <T> the type of the return value
 *
 * @author Brian Oliver
 * @since 1.0
 */
public interface EntryProcessorResult<T> {
  /**
   * Obtain the result of processing an entry with an {@link EntryProcessor}.
   * <p>
   * If an exception was thrown during the processing of an entry, either by
   * the {@link EntryProcessor} itself or by the Caching implementation,
   * the exceptions will be wrapped and re-thrown as a
   * {@link EntryProcessorException} when calling this method.
   *
   * @return  the result of processing
   *
   * @throws CacheException           if the implementation failed to execute
   *                                  the {@link EntryProcessor}
   * @throws EntryProcessorException  if the {@link EntryProcessor} raised
   *                                  an exception, this exception will be
   *                                  used to wrap the causing exception
   */
  T get() throws EntryProcessorException;
}
