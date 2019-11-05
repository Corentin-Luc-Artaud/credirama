#! /bin/bash

URL=localhost:8080/dump/
DIR=./dumps

if [ ! -d $DIR ]; then
    mkdir $DIR
fi

printf -v date '%(%Y-%m-%d_%H-%M-%S)T' -1
file="${DIR}/dump_${date}.json"
echo $file
curl -s $URL | python -m json.tool > $file