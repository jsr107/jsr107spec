package javax.cache;

/**
 * Indicates the status in it's lifecycle of a resource.
 * @author Greg Luck
 */
public enum Status {

     /**
     * The resource has been created but not yet initialised. It cannot be used.
     */
    UNITIALISED,

    /**
     * The resoure has been started and is ready for service.
     */
    STARTED,

    /**
     * The resource has been stopped. It can no longer service requests.
     */
    STOPPED
}
