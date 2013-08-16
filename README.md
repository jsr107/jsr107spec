JSR107 (JCache)
===============

About
-----

*JCache* is the API being defined in JSR107. It defines a standard Java Caching API for use by developers and a standard SPI ("Service
 Provider Interface") for use by implementers.


## Releases

* 25 June 2013:     0.9 Second Public Review Draft
* 25 June 2013:     0.8 Public Review Draft
* 17 April 2013     0.7
* 12 February 2013  0.6
* 13 March 2012:    0.5 Early Draft Submission uses this release.
* December 2011:    0.4
* 12 October 2011:  0.3
* 16 August 2011:   0.2 Initial release

Maven snippet:

    <dependency>
      <groupId>javax.cache</groupId>
      <artifactId>cache-api</artifactId>
      <version>0.9</version>
    </dependency>


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
      <version>0.10-SNAPSHOT</version>
    </dependency>

Javadoc
-------

The JavaDoc is available as a jar with the releases. We also have the latest JavaDoc [online](https://jsr107.ci.cloudbees.com/job/jsr107api/ws/target/apidocs/index.html).

Specification
-------------

The specification is available online on as a [Google Doc](https://docs
.google.com/document/d/1YZ-lrH6nW871Vd9Z34Og_EqbX_kxxJi55UrSn4yL2Ak/edit?hl=en_US).

Reference Implementation
------------------------

The reference implementation ("RI") source is available on [GitHub](https://github.com/jsr107/RI).

This implementation is not meant for production use. For that we would refer you to one of the many open source and commercial
implementations of JCache.

The RI is there to ensure that the specification and API works.

For example, some things that we leave out:

- implementation of transactions.
- tiered storage. A simple on-heap store is used.
- replicated or distributed caching

Why did we do this? Because a much greater engineering effort, which gets put into the open source and commercial caches
which implement this API, is required to accomplish these things.

Having said that, the RI is Apache 2 and is a correct implementation of the spec. It can be used to create new cache
implementations.

Building From Source
--------------------

Building uses Maven in all modules. Maven 2.2.1 and 3.0.4 have been tested.

See each module's README.md for build instructions.

Please add the following to your settings.xml to enable the CDI RI to be sucked down from JBoss.

    <profiles>
            <profile>
                <id>jboss-public-repository</id>
                <repositories>
                    <repository>
                        <id>jboss-public-repository-group</id>
                        <name>JBoss Public Maven Repository Group</name>
                        <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
                        <layout>default</layout>
                        <releases>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </releases>
                        <snapshots>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </snapshots>
                    </repository>
                </repositories>
                <pluginRepositories>
                    <pluginRepository>
                        <id>jboss-public-repository-group</id>
                        <name>JBoss Public Maven Repository Group</name>
                        <url>https://repository.jboss.org/nexus/content/groups/public-jboss/</url>
                        <layout>default</layout>
                        <releases>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </releases>
                        <snapshots>
                            <enabled>true</enabled>
                            <updatePolicy>never</updatePolicy>
                        </snapshots>
                    </pluginRepository>
                </pluginRepositories>
            </profile>
    </profiles>



Continuous Integration
----------------------
All modules are built at http://jsr107.ci.cloudbees.com.


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


Issue tracker
-------------

Please log issues to: <https://github.com/jsr107/jsr107spec/issues>


Contributing
------------

Admission to the Expert Group is closed, but please feel free to post to the
mailing list.


License
-------

The API is available under the usual Java Community Process license.

The TCK is available under a restricted TCK license and must be licensed from
Oracle as is the usual case with JSRs.

The reference implementation is available under an Apache 2 license.

For details please read the license in each source code file.


Contributors
------------

This free, open source software was made possible by the JSR107 Expert Group who put many hours of hard work into it.


Copyright
---------

Copyright (c) JSR107 Expert Group