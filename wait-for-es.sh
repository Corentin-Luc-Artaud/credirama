#!/bin/bash

echo "awaiting ES"

host="http://localhost:9200"

# next wait for ES status to turn to yellow
health="$(curl -fsSL "$host/_cat/health?h=status")"
health="$(echo "$health" | sed -r 's/^[[:space:]]+|[[:space:]]+$//g')" # trim whitespace (otherwise we'll have "green ")

until [ "$health" = "green" ] || [ "$health" = "yellow" ]; do
    health="$(curl -fsSL "$host/_cat/health?h=status")"
    health="$(echo "$health" | sed -r 's/^[[:space:]]+|[[:space:]]+$//g')" # trim whitespace (otherwise we'll have "green ")
    >&2 echo "Elastic Search is unavailable (health status is : $health and not green)- sleeping ..."
    sleep 1
done

echo "ES is up $health"