
package javax.cache.interceptor;

import javax.interceptor.InvocationContext;

/**
 * Generates cache keys for intercepted method invocations. Implementations MUST be thread-safe
 * 
 * @author Eric Dalquist
 */
public interface CacheKeyGenerator {

    /**
     * Called for each intercepted method invocation. Generate a {@link CacheKey} from the {@link InvocationContext} data.
     * 
     * @param invocationContext The intercepted method invocation
     * @return A non-null cache key for the invocation.
     */
    public CacheKey generateCacheKey(InvocationContext invocationContext);
}
