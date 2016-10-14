/**
 * Copyright 2011-2016 Terracotta, Inc.
 * Copyright 2011-2016 Oracle America Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
