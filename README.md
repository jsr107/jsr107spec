JSR107 (JCache)
===============

About
-----

*JCache* is the API being defined in JSR107. It defines a standard Java Caching API for use by developers and a standard SPI ("Service
 Provider Interface") for use by implementers.


Releases
--------

The stable releases of this software are tagged with version numbers, starting with 0.2. Eventually, when the specification is further
along releases will match the specification number.

The following stable releases have been made:

* 16 August 2011: 0.2

Snapshot Releases
-----------------

Snapshot releases of jars for binaries, source and javadoc are available.

Download the cache-api from <https://oss.sonatype.org/index.html#nexus-search;quick~javax-cache>

or use the following Maven snippet:

    <repository>
        <id>sonatype-nexus-snapshots</id>
        <name>Sonatype Nexus Snapshots</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>

    <dependency>
      <groupId>javax.cache</groupId>
      <artifactId>cache-api</artifactId>
      <version>0.2</version>
    </dependency>

Javadoc
-------

The JavaDoc is available as a jar with the releases. We also have the latest JavaDoc [online](https://jsr107.ci.cloudbees.com/job/jsr107api/ws/target/apidocs/index.html).

Specification
-------------

The evolving specification is available online on as a [Google Doc](https://docs.google.com/document/d/1YZ-lrH6nW871Vd9Z34Og_EqbX_kxxJi55UrSn4yL2Ak/edit?hl=en_US).

Reference Implementation
------------------------

The reference implementation ("RI") source is available on [GitHub](https://github.com/jsr107/RI).

This implementation is not meant for production use. For that we would refer you to one of the many open source and commercial
implementations of JCache.

The RI is there to ensure that the specification and API works.

For example, some things that we leave out:

- implementation of transactions.
- concurrency. The RI is not exhaustively tested for thread safety.
- tiered storage. A simple on heap store is used.
- replicated or distributed caching

Why did we do this? Because a much greater engineering effort, which gets put into the open source and commercial caches
which implement this API, is required to accomplish these things.

Having said that, the RI is Apache 2 and is a correct implementation of the spec. It can be used to create new cache
implementations.

Building From Source
--------------------

Building uses Maven in all modules. Maven 2.2.1 and 3.0.4-SNAPSHOT have been tested.

See each module's README.md for build instructions.


Testing Implementions of JSR107
-------------------------------

The jsr107tck module contains a domain, tests and an implementation-tester. The implementation-tester allows an implementation, packaged
as a Maven module, to be specified on the command line and tested. For example a fictional Acme company implementation could be tested as follows:

    mvn -Dtest=CacheManagerFactoryTest \
        -Dimplementation-groupId=acme.cache \
        -Dimplementation-artifactId=acme-cache \
        -Dimplementation-version=0.1-SNAPSHOT \
        test

Mailing list
------------

Please join the mailing list if you're interested in using or developing the software: <http://groups.google.com/group/jsr107>


IRC
---

We will be using the `#jsr107` channel on Freenode for chat.

We also have set up a commit hook which publishes commits to the channel.


Issue tracker
-------------

Please log issues to: <https://github.com/jsr107/jsr107spec/issues>


Contributing
------------

Right now code contribution is limited to the Expert Group, but please feel free to post to the mailing list.


License
-------

The API is available under the JPA license and may be freely used.

The TCK is available under a restricted TCK license although the tests.

The reference implementation is available under an Apache 2 license.

For details please read the license in each source code file.


Contributors
------------

This free, open source software was made possible by the JSR107 Expert Group who put many hours of hard work into it.


Copyright
---------

Copyright (c) JSR107 Expert Group