
package javax.cache.interceptor;

import java.lang.reflect.Method;

import javax.cache.Cache;
import javax.cache.CacheManager;

/**
 * Default {@link CacheResolver} that uses the default {@link CacheManager} and finds the {@link Cache}
 * using {@link CacheManager#getCache(String)}.
 * 
 * @author Eric Dalquist
 */
public class DefaultCacheResolver implements CacheResolver {
    private final CacheManager cacheManager;
    
    public DefaultCacheResolver(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /* (non-Javadoc)
     * @see javax.cache.interceptor.CacheResolver#resolveCacheManger(java.lang.String, java.lang.reflect.Method)
     */
    public <K, V> Cache<K, V> resolveCacheManger(String cacheName, Method method) {
        // TODO 
        //return cacheManager.getCache(cacheName);
        return null;
    }

}
