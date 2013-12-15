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
