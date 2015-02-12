package javax.cache.integration;

/**
 * A Collector streams results from asynchronous operations.
 * <p/>
 * A Collector will use an implementation specific thread for the call.
 *
 * @author Greg Luck
 * @since 1.1
 * @param <T> the type of the Collector
 */
public interface Collector<T> extends CompletionListener {

    /**
     * Called by the provider
     * @param t the type of the object
     */
    void onNext(T t);


}
