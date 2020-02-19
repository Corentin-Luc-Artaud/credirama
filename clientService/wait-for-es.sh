#!/bin/bash

echo "hello from script"

host="http://elasticsearch:9200"
cmd="java -jar /clientServiceApp.jar"


until $(curl --output /dev/null --silent --head --fail "$host"); do
    printf '.'
    sleep 1
done

# First wait for ES to start...
response=$(curl $host)
until [ "$response" = "200" ]; do
    response=$(curl --write-out %{http_code} --silent --output /dev/null "$host")
    >&2 echo "Elastic Search is unavailable - sleeping"
    sleep 1
done

# next wait for ES status to turn to yellow
health="$(curl -fsSL "$host/_cat/health?h=status")"
# health="$(echo "$health" | sed -r 's/^[[:space:]]+|[[:space:]]+$//g')" # trim whitespace (otherwise we'll have "green ")

until [ "$health" = "green" ] || [ "$health" = "yellow" ]; do
    health="$(curl -fsSL "$host/_cat/health?h=status")"
    # health="$(echo "$health" | sed -r 's/^[[:space:]]+|[[:space:]]+$//g')" # trim whitespace (otherwise we'll have "green ")
    >&2 echo "Elastic Search is unavailable (health status is : $health and not green)- sleeping ..."
    sleep 1
done

# echo "creating kibana index pattern"
# curl -X POST "localhost:5601/api/saved_objects/index-pattern/credirama" -H 'kbn-xsrf: true' -H 'Content-Type: application/json' -d'{"attributes": {"title": "credirama"}}'

>&2 echo "Elastic Search is up"
exec $cmd


