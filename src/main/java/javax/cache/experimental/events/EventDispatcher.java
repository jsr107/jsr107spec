/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache.experimental.events;

import java.util.Set;

/**
 * @author Yannis Cosmadopoulos
 * @since 1.0
 */
public interface EventDispatcher {
    /**
     *
     * @param name
     * @param eventInterceptor
     * @param subscriptionTypes
     */
    void addEventInterceptor(String name, EventInterceptor eventInterceptor, Set<Enum> subscriptionTypes);

    /**
     *
     * @param name
     */
    void removeEventInterceptor(String name);

    /**
     *
     * @return
     */
    Set<Enum> getSupportedTypes();
}
