
package javax.cache.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.interceptor.InterceptorBinding;


/**
 * When a method annotated with {@link CacheRemoveAll} is invoked all elements in the specified cache
 * will be removed via the {@link Cache#removeAll()} method
 * 
 * @author Eric Dalquist
 */
@Target( { ElementType.METHOD } )
@Retention(RetentionPolicy.RUNTIME)
@InterceptorBinding
public @interface CacheRemoveAll {

    /**
     * (Optional) name of the cache.
     * <p/>
     * Defaults to ClassName.methodName
     */
    String cacheName() default "";

    /**
     * (Optional) When {@link Cache#removeAll()} should be called. If true it is called after the annotated method
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
}
