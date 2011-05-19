package javax.cache.interceptor;

import javax.interceptor.InvocationContext;

/**
 * Creates a {@link DefaultCacheKey} for the {@link InvocationContext}.
 *
 * @author Eric Dalquist
 * @version $Revision$
 */
public class DefaultCacheKeyGenerator implements CacheKeyGenerator {

    /* (non-Javadoc)
     * @see javax.cache.interceptor.CacheKeyGenerator#generateCacheKey(javax.interceptor.InvocationContext)
     */
    public CacheKey generateCacheKey(InvocationContext invocationContext) {
        final Object[] parameters = invocationContext.getParameters();

        //TODO is this needed or does InvocationContext.getParameters() return an array we can be sure wont be modified
        final Object[] parametersClone = new Object[parameters.length];
        System.arraycopy(parameters, 0, parametersClone, 0, parameters.length);

        return new DefaultCacheKey(parametersClone);
    }

}
