package net.sf.jsr107cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *    A cache, being a mechanism for efficient temporary storage of objects
 * for the purpose of improving the overall performance of an application
 * system, should not be necessary for the application to function correctly,
 * it only improves the performance.
 * <p>
 *    A cache could be scoped, for examples to a JVM, all JVMs on a node, all
 * nodes in a cluster, etc. Operations that are scoped to a cache such as put
 * or load would affect all JVMs in the cache.  So the object loaded in 1 JVM
 * would be equally available to all other JVMs in the cache.
 * <p>
 *   Objects are identified in the cache by a key. A key can be any Java
 * object that implements the equals and  hashcode methods. If the object is
 * to be distributed or persisted (if supported) it must implement
 * serializable.
 * <p/>
 *   Each object in the cache will have a <code>CacheEntry<code> object associated with
 * it. This object will encapsulate the metadata associated with the cached
 * object. Mainly it represents the object statistics.
 * <p/>
 *   "CacheStatistics" represents the read-only statistics of the cache,
 * while "CacheAttributes" represents the user settable attributes of the
 * cache.
 */
public interface Cache extends Map
{
    /**
     * Returns true if the cache contains the specified key.  The search is
     * scoped to the cache. Other caches in the system will not be searched
     * and a CacheLoader will not be called.
     * @return true, if the cache contains the specified key.
     */
    public boolean containsKey(Object key);

    /**
     * @return true if the cache contains one or more keys to the specified value.
     */
    public boolean containsValue(Object value);

    /**
     * Returns a set view of the objects currently contained in the cache.
     * A CacheLoader will not be called. The behavior is unspecified for the
     * case when an object is remove from the cache while the return set is
     * being traversed.
     */
    public Set entrySet();

    /**
     * Equality is based on the Set returned by entrySet.  Equal will return
     * true if the two objects are referencing the same object or
     * entrySet.equals(((Map)o).entrySet()) returns true.
     */
    public boolean equals(Object o);

    /**
     * @param ht a hashtable which holds a pointer pointing to the
     * declarative cache description.
     * @throws CacheException if any error occurs.
     */

    /**
     * @return the hash code value for this the cache.
     */
    public int hashCode();

    /**
     * @return true if entrySet().isEmpty() returns true.
     */
    public boolean isEmpty();

    /**
     * Returns a set view of the keys currently contained in the cache.  A
     * CacheLoader will not be called. The behavior is unspecified for the
     * case when an object is remove from the cache while the return set is
     * being traversed.
     */
    public Set keySet();

    /**
     * Copies all of the mappings from the specified map to the cache.  This
     * would be equivalent to t.entrySet() then iterating through the Set and
     * calling put with each key value pair.
     */
    public void putAll(Map t);

    /**
     * @return the number of objects in the cache. This should be the same
     * value as entrySet().size();
     */
    public int size();

    /**
     * @return a collection view of the values contained in the cache.
     */
    public Collection values();

    /**
     * The get method will return, from the cache, the object associated with
     * the argument "key". If the object is not in the cache, the associated
     * cache loader will be called. If no loader is associated with the object,
     * a null is returned.  If a problem is encountered during the retrieving
     * or loading of the object, an exception (to be defined) will be thrown.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.load method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the load method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and
     * not finding the object in the cache. In both cases a null is returned.
     */
    public Object get(Object key);

    // REVIEW adam@bea.com 23-Jun-04 - I think that it is strange that get()
    // doesn't throw an exception whereas getAll() does.  I understand the
    // historical reason for it, but think it looks ugly nonetheless.

    /**
     * The getAll method will return, from the cache, a Map of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception will be thrown.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.loadAll method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and
     * not finding the object in the cache. In both cases a null is returned.
     */
    public Map getAll(Collection keys) throws CacheException;

    /**
     * The load method provides a means to "pre load" the cache. This method
     * will, asynchronously, load the specified object into the cache using
     * the associated cacheloader. If the object already exists in the cache,
     * no action is taken. If no loader is associated with the object, no object
     * will be loaded into the cache.  If a problem is encountered during the
     * retrieving or loading of the object, an exception should
     * be logged.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.load method.  The cache will not dereference the object. If
     * no "arg" value is provided a null will be passed to the load method.
     * The storing of null values in the cache is permitted, however, the get
     * method will not distinguish returning a null stored in the cache and not
     * finding the object in the cache. In both cases a null is returned.
     */
    public void load(Object key) throws CacheException;

    /**
     * The loadAll method provides a means to "pre load" objects into the cache.
     * This method will, asynchronously, load the specified objects into the
     * cache using the associated cache loader. If the an object already exists
     * in the cache, no action is taken. If no loader is associated with the
     * object, no object will be loaded into the cache.  If a problem is
     * encountered during the retrieving or loading of the objects, an
     * exception (to be defined) should be logged.
     * The getAll method will return, from the cache, a Map of the objects
     * associated with the Collection of keys in argument "keys". If the objects
     * are not in the cache, the associated cache loader will be called. If no
     * loader is associated with an object, a null is returned.  If a problem
     * is encountered during the retrieving or loading of the objects, an
     * exception (to be defined) will be thrown.
     * If the "arg" argument is set, the arg object will be passed to the
     * CacheLoader.loadAll method.  The cache will not dereference the object.
     * If no "arg" value is provided a null will be passed to the loadAll
     * method.
     */
    public void loadAll(Collection keys) throws CacheException;

    /**
     * The peek method will return the object associated with "key" if it
     * currently exists (and is valid) in the cache. If not, a null is
     * returned.  With "peek" the CacheLoader will not be invoked and other
     * caches in the system will not be searched.
     */
    public Object peek(Object key);

    /**
     * The put method adds the object "value" to the cache identified by the
     * object "key".
     */
    public Object put(Object key, Object value);

    /**
     * Returns the CacheEntry object associated with the object identified by
     * "key". If the object is not in the cache a null is returned.
     */
    public CacheEntry getCacheEntry(Object key);

    /**
     * Returns the CacheStatistics object associated with the cache.
     * May return null if the cache does not support statistics gathering.
     */
    public CacheStatistics getCacheStatistics();

    /**
     * The remove method will delete the object from the cache including the
     * key, the associated value and the associated CacheStatistics object.
     */
    public Object remove(Object key);

    /**
     * The clear method will remove all objects from the cache including the
     * key, the associated value and the associated CacheStatistics object.
     */
    public void clear();

    /**
     * The evict method will remove objects from the cache that are no longer
     * valid.  Objects where the specified expiration time has been reached.
     */
    public void evict();

    /** Add a listener to the list of cache listeners */
    public void addListener(CacheListener listener);

    /** Remove a listener from the list of cache listeners */
    public void removeListener(CacheListener listener);
}
