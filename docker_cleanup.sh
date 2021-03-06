#!/usr/bin/env bash

docker-compose down 

# docker volume rm $(docker volume ls -qf dangling=true)
# docker volume ls -qf dangling=true | xargs -r docker volume rm

# docker network ls
# docker network ls | grep "bridge"
# docker network rm $(docker network ls | grep "bridge" | awk '/ / { print $1 }')

docker volume rm $(docker volume ls | grep -i credirama)

docker images
docker rmi $(docker images --filter "dangling=true" -q --no-trunc)

docker images | grep "none"
docker rmi $(docker images | grep "none" | awk '/ / { print $3 }')

# docker ps
# docker ps -a
# docker rm $(docker ps -qa --no-trunc --filter "status=exited")