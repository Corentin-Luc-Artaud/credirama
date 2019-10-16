FROM openjdk:8-jdk-alpine

ADD target/clientService-0.0.1-SNAPSHOT.jar clientServiceApp.jar

ENTRYPOINT ["java", "-jar", "clientServiceApp.jar"]

EXPOSE 8080