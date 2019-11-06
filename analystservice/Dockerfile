FROM openjdk:8u151-jre

WORKDIR /code

COPY target/analystservice-jar-with-dependencies.jar /code

CMD ["java", "-jar", "/code/analystservice-jar-with-dependencies.jar"]

EXPOSE 9300
