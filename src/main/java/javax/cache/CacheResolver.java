
package javax.cache;

import javax.interceptor.InvocationContext;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public interface CacheResolver {
    public <K, V> Cache<K, V> resoveCache(Cacheable annotation, InvocationContext invocationContext);
}
