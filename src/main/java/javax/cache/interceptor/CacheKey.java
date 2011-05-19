
package javax.cache.interceptor;

import java.io.Serializable;

/**
 * A serializable, immutable, thread-safe object that can be used for a cache key.
 * 
 * The implementation MUST follow the java contract for {@link Object#hashCode()} and
 * {@link Object#equals(Object)} to ensure correct behavior.
 * 
 * @author Eric Dalquist
 */
public interface CacheKey extends Serializable {

    /**
     * The immutable hash code of the object.
     */
    public int hashCode();
    
    /**
     * Compare this {@link CacheKey} with another. If the two objects are equal their {@link #hashCode()} values
     * MUST be equal as well.
     * 
     * @param o The other object to compare to.
     * @return true if the objects are equal
     */
    public boolean equals(Object o);
}
