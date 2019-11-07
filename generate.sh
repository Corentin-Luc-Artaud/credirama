#! /bin/bash

./wait-for-es.sh

echo start generator
java -jar generator/target/generator-1.0-SNAPSHOT-jar-with-dependencies.jar
