/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */
package javax.cache;

/**
 * Optional features that may be present in an implementation.
 *
 * @author Yannis Cosmadopoulos
 * @author Greg Luck
 * @since 1.0
 */
public enum OptionalFeature {

    /**
     * Implementation supports XA and Local Transactions
     */
    TRANSACTIONS,

    /**
     * Implementation supports store by reference
     */
    STORE_BY_REFERENCE
}
