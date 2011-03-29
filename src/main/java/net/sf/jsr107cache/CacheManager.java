package net.sf.jsr107cache;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * CacheManager is used in J2SE environments for looking up named caches.
 */
public class CacheManager {

    private static final String FACTORY_PROPERTY_NAME = "net.sf.jsr107cache.CacheFactory";
    private static final String DEFAULT_FACTORY_NAME = "ri.cache.BasicCacheFactory";

    protected static CacheManager instance = new CacheManager();

    // REVIEW brian@quiotix.com
    // Should this be a HashMap<String, WeakReference<Cache>>?
    private final Map caches = Collections.synchronizedMap(new HashMap());

    /**
     * Returns the singleton CacheManager
     */
    public static CacheManager getInstance() {
        return instance;
    }

    public Cache getCache(String cacheName) {
        return (Cache) caches.get(cacheName);
    }

    public void registerCache(String cacheName, Cache cache) {
        caches.put(cacheName, cache);
    }

    public CacheFactory getCacheFactory() throws CacheException {
        String factoryName = findFactory(FACTORY_PROPERTY_NAME);

        try {
            ClassLoader cl = findClassLoader();
            Class spiClass = Class.forName(factoryName, true, cl);
            return (CacheFactory) spiClass.newInstance();
        } catch (ClassNotFoundException cnfe) {
            throw new CacheException("Could not find class: '" + factoryName + "'");
        } catch (ClassCastException cce) {
            throw new CacheException("Class: '" + factoryName + "' does not implement CacheFactory");
        } catch (InstantiationException ie) {
            throw new CacheException("Could not instantiate: '" + factoryName + "'");
        } catch (IllegalAccessException iae) {
            throw new CacheException("Could not find public default constructor for: '" + factoryName + "'");
        }
    }

    private  ClassLoader findClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) cl = ClassLoader.getSystemClassLoader();
        return cl;
    }

    private  boolean isEmptyString(String s) {
        return s == null || "".equals(s);
    }

    String findFactory(String factoryId) {

        // Use the system property first
        try {
            String factoryClass = System.getProperty(factoryId);
            if (!isEmptyString(factoryClass)) return factoryClass;
        } catch (SecurityException ignore) {
        }

        // try to read from $java.home/lib/jcache.properties
        try {
            String configFile = System.getProperty("java.home") +
                    File.separator + "lib" + File.separator + "jcache.properties";
            File f = new File(configFile);
            if (f.exists()) {
                InputStream in = new FileInputStream(f);
                try {
                    Properties props = new Properties();
                    props.load(in);
                    String factoryClass = props.getProperty(factoryId);
                    if (!isEmptyString(factoryClass)) return factoryClass;
                } finally {
                    in.close();
                }
            }
        } catch (SecurityException ignore) {
        } catch (IOException ignore) {
        }

        // try to find services in CLASSPATH
        try {
            ClassLoader cl = findClassLoader();
            InputStream is = cl.getResourceAsStream("META-INF/services/" + factoryId);
            if (is != null) {
                BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                try {
                    String factoryName = r.readLine();
                    if (!isEmptyString(factoryName)) return factoryName;
                } finally {
                    r.close();
                }
            }
        } catch (IOException ignore) {
        }

        return DEFAULT_FACTORY_NAME;
    }
}
