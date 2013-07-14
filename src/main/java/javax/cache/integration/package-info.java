/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * This package contains interfaces for integration.
 * <p/>
 * It contains the {@link javax.cache.integration.CacheLoader} and
 * {@link javax.cache.integration.CacheWriter} interfaces which
 * allow loading from and writing to other systems respectively.
 * <p/>
 * A cache with a registered loader can be configured as a read-through cache, so
 * that access will automatically load missing entries. And similarly a cache
 * with a registered writer can be configured
 * as a write-through cache, so that changes are written through to an underlying
 * system.
 * <p/>
 * In addition a common idiom is to use a loader to initially
 * populate or refresh a cache. For that purpose there is the {@link
 * javax.cache.Cache#loadAll(java.util.Set, boolean, CompletionListener)}
 * method.
 *
 * @author Greg Luck
 * @since 1.0
 */
package javax.cache.integration;
