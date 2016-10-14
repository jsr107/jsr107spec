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

import javax.cache.Cache;
import javax.cache.event.CacheEntryListener;
import javax.cache.expiry.ExpiryPolicy;
import javax.cache.integration.CacheWriter;


/**
 * An invocable function that allows applications to perform compound operations
 * on a {@link javax.cache.Cache.Entry} atomically, according to the defined
 * consistency of a {@link Cache}.
 * <p>
 * Any {@link javax.cache.Cache.Entry} mutations will not take effect until after
 * the {@link EntryProcessor#process(MutableEntry, Object...)} method has completed
 * execution.
 * <p>
 * If an exception is thrown by an {@link EntryProcessor}, a Caching Implementation
 * must wrap any {@link Exception} thrown wrapped in an {@link
 * EntryProcessorException}. If this occurs no mutations will be made to the
 * {@link javax.cache.Cache.Entry}.
 * <p>
 * Implementations may execute {@link EntryProcessor}s in situ, thus avoiding
 * locking, round-trips and expensive network transfers.
 * 
 * <h3>Effect of {@link MutableEntry} operations</h3>
 * {@link javax.cache.Cache.Entry} access, via a call to
 * {@link javax.cache.Cache.Entry#getValue()}, will behave as if
 * {@link Cache#get(Object)} was called for the key.  This includes updating
 * necessary statistics, consulting the configured {@link ExpiryPolicy} and loading
 * from a configured {@link javax.cache.integration.CacheLoader}.
 * <p>
 * {@link javax.cache.Cache.Entry} mutation, via a call to
 * {@link MutableEntry#setValue(Object)}, will behave as if {@link
 * Cache#put(Object, Object)} was called for the key. This includes updating
 * necessary statistics, consulting the configured {@link
 * ExpiryPolicy}, notifying {@link CacheEntryListener}s and writing to a
 * configured {@link CacheWriter}.
 * <p>
 * {@link javax.cache.Cache.Entry} removal, via a call to
 * {@link MutableEntry#remove()}, will behave as if {@link Cache#remove(Object)}
 * was called for the key. This includes updating necessary statistics, notifying
 * {@link CacheEntryListener}s and causing a delete on a configured
 * {@link CacheWriter}.
 * <p>
 * As implementations may choose to execute {@link EntryProcessor}s remotely,
 * {@link EntryProcessor}s, together with specified parameters and return
 * values, may be required to implement {@link java.io.Serializable}.
 * 
 * <h3>Effect of multiple {@link MutableEntry} operations performed by one {@link
 * EntryProcessor}</h3>
 * Only the net effect of multiple operations has visibility outside of the Entry
 * Processor. The entry is locked by the entry processor for the entire scope
 * of the entry processor, so intermediate effects are not visible.
 * <h4>Example 1</h4>
 * In this example, an {@link EntryProcessor} calls:
 * <ol>
 * <li>{@link MutableEntry#getValue()}</li>
 * <li>{@link MutableEntry#setValue(Object)}</li>
 * <li>{@link MutableEntry#getValue()}</li>
 * <li>{@link MutableEntry#setValue(Object)}</li>
 * </ol>
 * This will have the following {@link Cache} effects:
 * <br>
 * Final value of the cache: last setValue<br>
 * Statistics: one get and one put as the second get and the first put are
 * internal to the EntryProcessor.<br>
 * Listeners: second put will cause either a put or an update depending on whether
 * there was an initial value for the entry.<br>
 * CacheLoader: Invoked by the first get only if the entry is not present, a
 * loader was registered and read through is enabled.<br>
 * CacheWriter: Invoked by the second put only as the first put was internal to
 * the Entry Processor.<br>
 * ExpiryPolicy: The first get and the second put only are visible to the
 * ExpiryPolicy.<br>
 * 
 * <h4>Example 2</h4>
 * In this example, an {@link EntryProcessor} calls:
 * <ol>
 * <li>{@link MutableEntry#getValue()}</li>
 * <li>{@link MutableEntry#remove()}}</li>
 * <li>{@link MutableEntry#getValue()}</li>
 * <li>{@link MutableEntry#setValue(Object)}</li>
 * </ol>
 * This will have the following {@link Cache} effects:
 * <br>
 * Final value of the cache: last setValue<br>
 * Statistics: one get and one put as the second get and the first put are
 * internal to the EntryProcessor.<br>
 * Listeners: second put will cause either a put or an update depending on whether
 * there was an initial value for the entry.<br>
 * CacheLoader: Invoked by the first get only if the entry is not present, a loader
 * was registered and read through is enabled.<br>
 * CacheWriter: Invoked by the second put only as the first put was internal to
 * the Entry Processor.<br>
 * ExpiryPolicy: The first get and the second put only are visible to the
 * ExpiryPolicy.<br>
 * 
 * <h4>Example 3</h4>
 * In this example, an {@link EntryProcessor} calls:
 * <ol>
 * <li>{@link MutableEntry#getValue()}</li>
 * <li>{@link MutableEntry#setValue(Object)}}</li>
 * <li>{@link MutableEntry#getValue()}</li>
 * <li>{@link MutableEntry#setValue(Object)}</li>
 * <li>{@link MutableEntry#remove()}</li>
 * </ol>
 * This will have the following {@link Cache} effects:
 * <br>
 * Final value of the cache: the entry is removed if it was present<br>
 * Statistics: one get and one remove as the second get and the two puts are
 * internal to the EntryProcessor.<br>
 * Listeners: remove if there was initial value in the cache, otherwise no
 * listener invoked.
 * <br> CacheLoader: Invoked by the first get only if the entry is not present,
 * a loader was registered and read through is enabled.
 * <br> CacheWriter: Invoked by the remove only as the two puts are internal to
 * the Entry Processor, provided that the first #getValue was non-null.<br>
 * ExpiryPolicy: The first get only is visible to the ExpiryPolicy. There is no
 * remove event in ExpiryPolicy.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * @param <T> the type of the return value
 * @author Greg Luck
 * @since 1.0
 */
public interface EntryProcessor<K, V, T> {

  /**
   * Process an entry.
   *
   * @param entry     the entry
   * @param arguments a number of arguments to the process.
   * @return the user-defined result of the processing, if any.
   * @throws EntryProcessorException if there is a failure in entry processing.
   */
  T process(MutableEntry<K, V> entry, Object... arguments)
      throws EntryProcessorException;
}
