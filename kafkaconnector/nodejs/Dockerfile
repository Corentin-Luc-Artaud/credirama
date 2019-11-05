FROM node:10

WORKDIR /usr/src/app

COPY package.json ./

COPY kafka-elastic.js ./

COPY wait-for-kafka.sh ./

RUN npm install kafka-node

RUN npm install elasticsearch

CMD ["/bin/bash", "wait-for-kafka.sh"]
