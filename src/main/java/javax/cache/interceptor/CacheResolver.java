
package javax.cache.interceptor;

import java.lang.reflect.Method;

import javax.cache.Cache;

/**
 * Determines the {@link Cache} to use for a specified cache name and annotated method. Implementations MUST be thread-safe
 * 
 * @author Eric Dalquist
 */
public interface CacheResolver {

    /**
     * @param cacheName The name of the cache specified in the {@link CacheResult}, {@link CacheRemoveEntry}, or {@link CacheRemoveAll} annotation
     * @param method The annotated method
     * @return The {@link Cache} instance to be used by the intercepter
     */
    public <K, V> Cache<K, V> resolveCacheManger(String cacheName, Method method);
}
