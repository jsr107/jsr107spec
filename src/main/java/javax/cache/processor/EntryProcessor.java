package javax.cache.processor;

import javax.cache.Cache;


/**
 * An invokable function that allows applications to perform compound
 * operations on a {@link Cache.Entry} atomically, according the defined
 * consistency of a {@link Cache}.
 * <p/>
 * Any {@link Cache.Entry} mutations will not take effect until after the
 * {@link EntryProcessor#process(MutableEntry, Object...)}
 * method has completed execution.
 * <p/>
 * If an exception is thrown by an {@link EntryProcessor}, the exception will
 * be returned wrapped in an {@link javax.cache.CacheException}.  No changes will be made
 * to the {@link Cache.Entry}.
 * <p/>
 * Implementations may execute {@link EntryProcessor}s in situ, thus avoiding
 * locking, round-trips and expensive network transfers.
 * <p/>
 * {@link Cache.Entry} access, via a call to
 * {@link javax.cache.Cache.Entry#getValue()}, will behave as if
 * {@link Cache#get(Object)} was called for the key.  This includes updating
 * necessary statistics, consulting the configured
 * {@link javax.cache.expiry.ExpiryPolicy} and loading from a configured
 * {@link javax.cache.integration.CacheLoader}.
 * <p/>
 * {@link Cache.Entry} mutation, via a call to
 * {@link MutableEntry#setValue(Object)}, will behave
 * as if {@link Cache#put(Object, Object)} was called for the key.  This
 * includes updating necessary statistics, consulting the configured
 * {@link javax.cache.expiry.ExpiryPolicy}, notifying
 * {@link javax.cache.event.CacheEntryListener}s and a writing to a configured
 * {@link javax.cache.integration.CacheWriter}.
 * <p/>
 * {@link Cache.Entry} removal, via a call to
 * {@link MutableEntry#remove()}, will behave
 * as if {@link Cache#remove(Object)} was called for the key.  This
 * includes updating necessary statistics, notifying
 * {@link javax.cache.event.CacheEntryListener}s and causing a delete on a
 * configured {@link javax.cache.integration.CacheWriter}.
 * <p/>
 * As implementations may choose to execute {@link EntryProcessor}s remotely,
 * {@link EntryProcessor}s, together with specified parameters and return
 * values, may be required to implement {@link java.io.Serializable}.
 *
 * Multiple Operations in One Entry Processor
 * Only the net effect of multiple operations has visibility outside of the Entry
 * Processor. The entry is locked by the entry processor for the entire scope
 * of the entry processor, so intermediate effects are not visible.
 *
 * For example, a getValue, setValue, getValue, setValue has the following effects:
 *
 * Final value of the cache: last setValue
 * Statistics: one get and one put as the second get and the first put are internal
 * to the EntryProcessor.
 * Listeners: second put will cause either a put or an update depending on whether
 * there was an initial value for the entry.
 * CacheLoader: Invoked by the first get only if a loader was registered
 * CacheWriter: Invoked by the second put only as the first put was internal to the
 * Entry Processor
 * ExpiryPolicy: The first get and the second put only are visible to the
 * ExpiryPolicy.
 *
 * For example, a getValue, remove, getValue, setValue has the following effects:
 *
 * Final value of the cache: last setValue
 * Statistics: one get and one put as the second get and the first put are internal
 * to the EntryProcessor.
 * Listeners: second put will cause either a put or an update depending on whether
 * there was an initial value for the entry.
 * CacheLoader: Invoked by the first get only if a loader was registered
 * CacheWriter: Invoked by the second put only as the first put was internal to the
 * Entry Processor
 * ExpiryPolicy: The first get and the second put only are visible to the
 * ExpiryPolicy.
 *
 *
 * For example, a getValue, setValue, getValue, setValue,
 * remove has the following effects:
 *
 * Final value of the cache: last setValue
 * Statistics: one get and one remove as the second get and the two puts are
 * internal to the EntryProcessor.
 * Listeners: remove if there was initial value in the cache, otherwise no listener
 * invoked.
 * CacheLoader: Invoked by the first get only if a loader was registered
 * CacheWriter: Invoked by the remove only as the two puts are internal to the
 * Entry Processor
 * ExpiryPolicy: The first get only is visible to the ExpiryPolicy. There is no
 * remove event in ExpiryPolicy.
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of cached values
 * @param <T> the type of the return value
 *
 * @author Greg Luck
 * @since 1.0
 */
public interface EntryProcessor<K, V, T> {

  /**
   * Process an entry.
   *
   * @param entry     the entry
   * @param arguments a number of arguments to the process.
   * @return the result of the processing, if any, which is user defined.
   */
  T process(MutableEntry<K, V> entry, Object... arguments);
}
