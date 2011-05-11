package javax.cache;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public @interface Cacheable {
    String value() default "";

    String cacheName() default "";

    String exceptionCacheName() default "";

    boolean cacheNull() default true;

    Class<? extends CacheResolver> cacheResovler() default CacheResolver.class;

    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default CacheKeyGenerator.class;
}
