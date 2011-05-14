
package javax.cache.interceptor;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheManager;

/**
 * Default {@link CacheResolver} that uses the default {@link CacheManager} and finds the {@link Cache}
 * using {@link CacheManager#getCache(String)}.
 * 
 * @author Eric Dalquist
 */
public class DefaultCacheResolver implements CacheResolver {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    
    private final CacheManager cacheManager;
    
    public DefaultCacheResolver(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /* (non-Javadoc)
     * @see javax.cache.interceptor.CacheResolver#resolveCacheManger(java.lang.String, java.lang.reflect.Method)
     */
    public <K, V> Cache<K, V> resolveCacheManger(String cacheName, Method method) {
        // TODO once CacheManager is well defined
        Cache<K, V> cache = null; //cacheManager.getCache(cacheName);
        if (cache == null) {
            logger.warning("No Cache named '" + cacheName + "' was found in the CacheManager, a copy of the default cache will be created.");
            cache = null; //cacheManager.createCacheFromDefault(cacheName);
        }
        return cache;
    }

}
