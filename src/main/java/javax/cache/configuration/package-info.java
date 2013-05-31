/**
 *  Copyright (c) 2011 Terracotta, Inc.
 *  Copyright (c) 2011 Oracle and/or its affiliates.
 *
 *  All rights reserved. Use is subject to license terms.
 */

/**
 This package contains configuration classes and interfaces.
 <p/>
 Rather than configuration accepting user instances of interfaces for maximum
 portability factories are configured instead, so that instances can be
 instantiated where needed. To aid in this, FactoryBuilder can build factories
 of the required type as shown in the following example.
 <code>
   MutableConfiguration config = new MutableConfiguration<String, Integer>()
          .setTypes(String.class, Integer.class);

   config.setStoreByValue(false).setStatisticsEnabled(true)
          .setExpiryPolicyFactory(
          FactoryBuilder.factoryOf(new Accessed<String, Integer>
          (new Duration(TimeUnit.HOURS, 1))));
 </code>
 <p/>
{@link OptionalFeature}, though not cache configuration,
 makes known to user code those optional features supported by the caching
 configuration to support runtime adaptation.

 @author Greg Luck
 @since 1.0
 */
package javax.cache.configuration;
