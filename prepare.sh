# DELETE ALREADY EXISTING IMAGES
result=$( docker ps -a -q )
echo "Stopping and deleting containers"
if [[ -n "$result" ]]; then
  docker stop $(docker ps -a -q)
  docker rm $(docker ps -a -q)
else
  echo "No such container"
fi

# CREATE NETWORK IN IT DOESNT EXIST
docker network ls|grep tracker-postgres > /dev/null || docker network create tracker-postgres

result=$( docker images -q clientservice:latest )
echo "Deleting clientservice:latest image"
if [[ -n "$result" ]]; then
  docker rmi clientservice:latest
else
  echo "No such container"
fi

cd  ./clientService
echo "Building clientservice:latest image"
mvn clean package -DskipTests=true
docker build -t clientservice:latest .

# RUN DOCKER-COMPOSE
cd ..
echo "Running Docker compose"
docker-compose up --build