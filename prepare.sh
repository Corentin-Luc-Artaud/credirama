#!/usr/bin/env bash
# DELETE ALREADY EXISTING IMAGES
result=$( docker ps -a -q )
echo "Stopping and deleting containers"
if [[ -n "$result" ]]; then
  docker stop $(docker ps -a -q)
  docker rm $(docker ps -a -q)
else
  echo "No such container"
fi

result=$( docker images -q clientservice:latest )
echo "Deleting clientservice:latest image"
if [[ -n "$result" ]]; then
  docker rmi clientservice:latest
else
  echo "No such container"
fi

result=$( docker images -q timeservice:latest )
echo "Deleting timeservice:latest image"
if [[ -n "$result" ]]; then
  docker rmi timeservice:latest
else
  echo "No such container"
fi

cd  ./models
echo "Building models"
mvn clean package install -DskipTests=true

cd ../generator
echo "Packaging generator"
mvn clean package -DskipTests=true

cd ../clientService
echo "Building clientservice:latest image"
mvn clean package install -DskipTests=true
docker build -t clientservice:latest .

cd  ../TimeService
echo "Building timeservice:latest image"
mvn clean package -DskipTests=true
docker build -t timeservice:latest .

cd ../kafkaconnector
echo "Building kafkaconnector:latest image"
docker build-t kafkaconnector:latest

cd ../analystservice
echo "Building analystservice:latest image"
docker build-t analystservice:latest

# RUN DOCKER-COMPOSE
cd ..
echo "Running Docker compose"
#docker-compose build --force-recreate
docker-compose up launch-barebone && docker-compose up launch-services
