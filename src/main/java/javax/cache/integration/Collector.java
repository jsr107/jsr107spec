package javax.cache.integration;

/**
 * A Collector streams results from asynchronous operations.
 * <p/>
 * A Cache provider will use one or more implementation specific threads for
 * invoking methods on this interface.
 * <p/>
 * A Collector must be thread safe.
 * <p/>
 * Reuse or concurrent use of a Collector across multiple Cache method calls
 * is not supported.
 * <p/>
 * Reuse of a Collector between Cache method calls is not supported.
 * @author Greg Luck
 * @author Brian Oliver
 * @since 1.1
 * @param <T> the type of the Collector
 */
public interface Collector<T> extends CompletionListener {

    /**
     * Called by the provider.
     * @param t the type of the object
     */
    void onNext(T t);


}
