# Demos for SpringOne 2GX 2014 "Fastest Servlets in the West"

This is the demo repository for "Fastest Servlets in the West".

## Getting Started

Here are the interesting folders.

### TomcatInstances

This folder contains a few different configurations for Tomcat.  You can use one of the configurations by using the `bin/start.sh` or `bin/stop.sh` scripts.

### SimpleWebProject

This is a web project that contains some simple resources for testing.  Included are some static files, a servlet, a filter, a JSP page and a servlet which uses Thymeleaf to generate it's content.  These are all endpoints exposed for usage with performance testing scripts.

### JMeterScripts

This contains various JMeter scripts which can be used for load testing.

### scripts

This contains a script for automatically downloading Tomcat for use with the Tomcat instance configurations in this repo, and it contains a script which can be used to start the various different Tomcat instance configurations.  This script is used by the `start.sh` and `stop.sh` scripts included with each instance, but can also be used directly.
