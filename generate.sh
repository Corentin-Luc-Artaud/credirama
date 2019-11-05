#! /bin/bash
echo start generator

cd generator
mvn clean install
cd ..
java -jar generator/target/generator-1.0-SNAPSHOT-jar-with-dependencies.jar
