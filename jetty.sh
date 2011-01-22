#!/bin/sh
mvn clean test-compile
mvn exec:java -Dexec.mainClass="example.jetty.WebServer" -Dexec.classpathScope=test