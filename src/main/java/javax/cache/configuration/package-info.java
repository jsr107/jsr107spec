/**
 *  Copyright (c) 2011-2013 Terracotta, Inc.
 *  Copyright (c) 2011-2013 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 * This package contains configuration classes and interfaces.
 * <p>
 * Rather than configuration accepting user instances of interfaces for maximum
 * portability factories are configured instead, so that instances can be
 * instantiated where needed. To aid in this, FactoryBuilder can build factories
 * of the required type as shown in the following example.
 * <pre><code>
 *  MutableConfiguration&lt;String, Integer&gt; config = new MutableConfiguration&lt;String, Integer&gt;();
 *
 *  config.setTypes(String.class, Integer.class)
 *        .setStoreByValue(false)
 *        .setStatisticsEnabled(true)
 *        .setExpiryPolicyFactory(FactoryBuilder.factoryOf(
 *            new AccessedExpiryPolicy&lt;String&gt;(new Duration(TimeUnit.HOURS, 1))));
 * </code></pre>
 * <p>
 * {@link javax.cache.configuration.OptionalFeature}, though not specific to
 * cache configuration, allows application to determine the optional features
 * supported at runtime.
 *
 * @author Greg Luck
 * @since 1.0
 */
package javax.cache.configuration;
