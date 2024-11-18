#!/bin/sh

cd "$(dirname "$0")"

test -d target/dependency || mvn dependency:copy-dependencies

mvn package &&
  java \
    -javaagent:target/headless-awt-1.0.0-SNAPSHOT.jar \
    -Djava.awt.headless=true \
    -cp target/headless-awt-1.0.0-SNAPSHOT.jar:target/headless-awt-1.0.0-SNAPSHOT-tests.jar:target/dependency/'*' \
    org.scijava.headless.DoWindowThings
