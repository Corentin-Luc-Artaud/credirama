FROM openjdk:8-jdk-alpine

ADD target/TimeService-0.0.1-SNAPSHOT.jar timeServiceApp.jar

ENTRYPOINT ["java", "-jar", "timeServiceApp.jar"]

EXPOSE 8080