#!/bin/sh

cd "$(dirname "$0")"

test -d target/dependency || mvn dependency:copy-dependencies

mvn package &&
  java \
    --add-exports java.desktop/java.awt.peer=ALL-UNNAMED \
    --add-exports java.desktop/sun.awt=ALL-UNNAMED \
    --add-exports java.desktop/sun.java2d.pipe=ALL-UNNAMED \
    -javaagent:target/headless-awt-1.0.0-SNAPSHOT.jar \
    -Djava.awt.headless=true \
    -cp target/headless-awt-1.0.0-SNAPSHOT.jar:target/headless-awt-1.0.0-SNAPSHOT-tests.jar:target/dependency/'*' \
    org.scijava.headless.DoWindowThings
