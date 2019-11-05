FROM openjdk:8-jdk-alpine
MAINTAINER WALID LARABI

## problems fixing links
# https://chaseonsoftware.com/notes_to_self/exec_user_process_docker/
# https://devopscube.com/run-scripts-docker-arguments/
# https://medium.com/@d.lopez.j/easily-connecting-your-spring-boot-applications-to-the-elastic-stack-with-log4j2-1809e81c6a2e

# Install cURL & bash
RUN apk --no-cache add curl
RUN apk --no-cache add bash


COPY target/clientService-0.0.1-SNAPSHOT.jar /clientServiceApp.jar
COPY ./wait-for-es.sh /
WORKDIR /
RUN ls
RUN chmod +x /wait-for-es.sh

# ENTRYPOINT ["java", "-jar", "/clientServiceApp.jar"]
ENTRYPOINT ["/bin/bash", "/wait-for-es.sh"]

CMD curl -XPUT 'http://elasticsearch:9200/credirama/'

EXPOSE 8080