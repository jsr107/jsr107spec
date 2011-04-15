package javax.cache;

import java.io.Serializable;

/**
 * A strategy for copying values on put and get.
 * @param <T> type
 * @author Greg Luck
 */
interface CopyStrategy<T> extends Serializable {

    /**
     * Deep copies some object and returns an internal storage-ready copy
     *
     * @param value the value to copy
     * @return the storage-ready copy
     */
    T copyForWrite(final T value);

    /**
     * Reconstruct an object from its storage-ready copy.
     *
     * @param storedValue the storage-ready copy
     * @return the original object
     */
    T copyForRead(final T storedValue);
}

