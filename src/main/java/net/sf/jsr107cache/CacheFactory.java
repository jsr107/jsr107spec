package net.sf.jsr107cache;

import java.util.Map;

/**
 * CacheFactory is a service provider specific interface.
 * Service provider should implement CacheFactory to provide
 * the functionality to create a new implementation specific Cache object.
 */
public interface CacheFactory
{
    /**
     * creates a new implementation specific Cache object using the env parameters.
     * @param env implementation specific environment parameters passed to the
     * CacheFactory.
     * @return an implementation specific Cache object.
     * @throws CacheException if any error occurs.
     */
    public Cache createCache(Map env) throws CacheException;
}
