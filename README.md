JSR107 (JCache)
===============

About
-----

*JCache* is the Java caching API. It was defined by JSR107. It defines a standard Java Caching API for use by developers and a standard SPI ("Service
 Provider Interface") for use by implementers. 


## Releases

* 16 December 2017: 1.1.0 Maintenance Release. 
* 18 March 2014:  1.0.0. Final Release. Unchanged from 1.0.0-RC1.
* 16 December 2013:  1.0.0-RC1. This is the version matching the final specification and is what is up on https://jcp.org/aboutJava/communityprocess/final/jsr107/index.html.
* 21 October 2013:  1.0.0-PFD for the cache-api and 0.11 for other artifacts.Proposed Final Draft
* 26 August 2013:   0.10 Third Public Review Draft
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
      <version>1.0.0</version>
    </dependency>


Maven Central Releases
----------------------

Releases of jars for binaries, source and javadoc are available on Maven central.

Download the cache-api from <https://oss.sonatype.org/index.html#nexus-search;quick~javax-cache>

or use the following Maven snippet:

    <dependency>
      <groupId>javax.cache</groupId>
      <artifactId>cache-api</artifactId>
      <version>1.1.0</version>
    </dependency>

Javadoc
-------

The JavaDoc is available as a jar with the releases. You can also find the JavaDoc [online](http://www.javadoc.io/doc/javax.cache/cache-api/1.0.0).

Specification
-------------

The specification is available online as a [Google Doc](https://docs.google.com/document/d/1MZQstO9GJo_MUMy5iD5sxCrstunnQ1f85ekCng8LcqM/edit?usp=sharing).

Reference Implementation
------------------------

The reference implementation ("RI") source is available on [GitHub](https://github.com/jsr107/RI).

This implementation is not meant for production use. For that we would refer you
to one of the many high quality open source and commercial implementations of JCache. See the [official list of compatible implementions](https://jcp.org/aboutJava/communityprocess/implementations/jsr107/index.html),
and also the community maintained [JSR107 Test Zoo](https://github.com/cruftex/jsr107-test-zoo/blob/master/report.md).

The RI is there to ensure that the specification and API works as its only purpose.

For example, some things that we leave out:

- tiered storage. A simple on-heap store is used.
- replicated or distributed caching

Why did we do this? Because a much greater engineering effort, which gets put into the open source and commercial caches
which implement this API, is required to accomplish these things.

Having said that, the RI is Apache 2 and is a correct implementation of the spec. It can be used to create new cache
implementations.

Building From Source
--------------------

Building uses Maven in all modules. Maven 3.3.9 - 3.5.4 have been tested.

JCache is compatible with Java 6 to Java 11. We have tested building from Java 8 and Java 11.

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



Testing Implementions of JSR107
-------------------------------

See the [TCK User Guide](https://docs.google.com/document/d/1w3Ugj_oEqjMlhpCkGQOZkd9iPf955ZWHAVdZzEwYYdU/edit?usp=sharing)
for instructions on how to use this TCK.

Mailing list
------------

Please join the mailing list if you're interested in using or developing the software: <http://groups.google.com/group/jsr107>


Issue tracker
-------------

Please log issues to: <https://github.com/jsr107/jsr107spec/issues>


User Questions
--------------

Some of the expert group monitor the tags "jcache" and "jsr107" on [stackoverflow](http://stackoverflow.com).


Contributing
------------

Admission to the Expert Group is closed, but please feel free to post to the mailing list.


License
-------

The API is available under the Apache 2.0 license.

The TCK is available under a restricted Standalone TCK (SATCK) license and must be 
licensed from Oracle as is the usual case with JSRs.

The reference implementation is available under an Apache 2.0 license.

For details please read the license in each source code file.

Contributors
------------

This free, open source software was made possible by the JSR107 Expert Group who put many hours of hard work into it.


Copyright
---------

Copyright (c) JSR107 Expert Group
