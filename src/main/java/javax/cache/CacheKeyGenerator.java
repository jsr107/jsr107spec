
package javax.cache;

import java.io.Serializable;

import javax.interceptor.InvocationContext;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface CacheKeyGenerator<T extends Serializable> {
    T generateKey(InvocationContext invocationContext);

    T generateKey(Object... parameters);
}