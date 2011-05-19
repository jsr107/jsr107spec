
package javax.cache.interceptor;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.interceptor.InterceptorBinding;


/**
 * When a method annotated with {@link CacheResult} is invoked a {@link CacheKey} will be generated and 
 * {@link Cache#get(Object)} is called before the invoked method actually executes. If a value is found in the
 * cache it is returned and the annotated method is never actually executed. If no value is found the
 * annotated method is invoked and the returned value is stored in the cache with the generated key.
 * 
 * null return values and thrown exceptions are never cached.
 *
 * @author Eric Dalquist
 */
@Target( { ElementType.METHOD, ElementType.TYPE } )
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheResult {

    /**
     * (Optional) name of the cache.
     * <p/>
     * Defaults to ClassName.methodName
     */
    String cacheName() default "";

    /**
     * (Optional) If set to true the pre-invocation get is skipped and the annotated method is always executed with
     * the returned value being cached as normal. This is useful for create or update methods which should always
     * be executed and have their returned value placed in the cache.
     * <p/>
     * Defaults to false
     */
    boolean skipGet() default false;

    /**
     * (Optional) The {@link CacheResolver} to use to find the {@link Cache} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link CacheManager}
     */
    Class<? extends CacheResolver> cacheResovler() default CacheResolver.class;

    Class<? extends Annotation>[] cacheResolverQualifiers() default {};

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call {@link Cache#get(Object)}
     * {@link Cache#put(Object, Object)}
     * <p/>
     * Defaults to {@link DefaultCacheKeyGenerator}
     */
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default DefaultCacheKeyGenerator.class;

    Class<? extends Annotation>[] cacheKeyGeneratorQualifiers() default {};
}
