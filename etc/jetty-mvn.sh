#!/bin/sh
mvn clean test-compile exec:java -Dexec.mainClass="example.jetty.WebServer" -Dexec.classpathScope=test