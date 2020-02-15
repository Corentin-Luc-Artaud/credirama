FROM debezium/connect:1.0

ENV KAFKA_CONNECT_JDBC_DIR=/kafka/connect/kafka-connect-jdbc
ARG KAFKA_JDBC_VERSION=5.3.2

# Deploy MySQL JDBC Driver
COPY mysql-connector-java-8.0.19.jar /kafka/libs


# Deploy Kafka Connect JDBC
#RUN mkdir /kafka/connect/kafka-connect-jdbc
COPY kafka-connect-jdbc-5.4.0.jar /kafka/libs

