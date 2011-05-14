
package javax.cache.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.interceptor.InterceptorBinding;


/**
 * When a method annotated with {@link CacheRemoveEntry} is invoked a {@link CacheKey} will be generated and 
 * {@link Cache#remove(K)} will be invoked on the specified cache.
 * 
 * @author Eric Dalquist
 */
@Target( { ElementType.METHOD, ElementType.TYPE } )
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheRemoveEntry {

    /**
     * (Optional) name of the cache.
     * <p/>
     * Defaults to ClassName.methodName
     */
    String cacheName() default "";

    /**
     * (Optional) When {@link Cache#remove(K)} should be called. If true it is called after the annotated method
     * invocation completes successfully. If false it is called before the annotated method is invoked.
     * <p/>
     * Defaults to true.
     */
    boolean afterInvocation() default true;

    /**
     * (Optional) The {@link CacheResolver} to use to find the {@link Cache} the intercepter will interact with.
     * <p/>
     * Defaults to resolving the cache by name from the default {@link CacheManager}
     */
    Class<? extends CacheResolver> cacheResovler() default CacheResolver.class;

//    Qualifier[] cacheResolverQualifiers() default {};

    /**
     * (Optional) The {@link CacheKeyGenerator} to use to generate the cache key used to call {@link Cache#remove(K)}
     * <p/>
     * Defaults to {@link DefaultCacheKeyGenerator}
     */
    Class<? extends CacheKeyGenerator> cacheKeyGenerator() default DefaultCacheKeyGenerator.class;

//    Qualifier[] cacheKeyGeneratorQualifiers() default {};
}
