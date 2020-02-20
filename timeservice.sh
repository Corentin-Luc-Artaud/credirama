#! /bin/bash

echo build time-service
cd TimeService/
mvn clean package -DskipTests=true
echo start time-service
java -jar target/TimeService-0.0.1-SNAPSHOT.jar #&> /dev/null
