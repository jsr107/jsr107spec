package javax.cache;


/**
 * CacheException is a generic exception, which indicates
 * a cache error has occurred. All the other cache exceptions are the
 * subclass of this class. All the methods in the cache package only
 * throw CacheException or the sub class of it.
 * <P>
 *
 */
public class CacheException extends Exception
{
    /**
     * Constructs a new CacheException.
     */
    public CacheException()
    {
        super();
    }

    /**
     * Constructs a new CacheException with a message string.
     */
    public CacheException(String s)
    {
        super(s);
    }

    /**
     * Constructs a CacheException with a message string, and
     * a base exception
     */
    public CacheException(String s, Throwable ex)
    {
        super(s, ex);
    }
}
