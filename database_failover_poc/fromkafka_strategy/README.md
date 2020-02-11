# MySQL Failover Demo

This is a showcase for MySQL deployment in clustered configuration and demonstration of Debezium execution after a database failure.

## Topology

The deployment consists of the following components

* Database
  * MySQL 1 instance (configured as a slave to MySQL 2) with GTID enabled
  * MySQL 2 instance (configured as a slave to MySQL 1) with GTID enabled
  * HAProxy instance - MySQL 1 is configured as the primary server, MySQL 2 as a backup
* Streaming system
  * Apache ZooKeeper
  * Apache Kafka broker
  * Apache Kafka Connect with Debezium MySQL Connector - the connector will connect to HAProxy

## Demonstration

Start the components and register Debezium to stream changes from the database
```
docker-compose up --build
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d @register-mysql.json
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" http://localhost:8083/connectors/ -d @jdbc-sink.json
```


Open a terminal, connect to the database and create two records.
```
# Connect to MySQL 1, check server UUID and create two records
docker-compose exec mysql1 bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD inventory'
docker-compose exec mysql2 bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD inventory'

  SHOW GLOBAL VARIABLES LIKE 'server_uuid';
  INSERT INTO customers VALUES (default, 'Sami','Lazrak','sami.lazrak@example.com');
  INSERT INTO customers VALUES (default, 'Walid','Larabi','walid.larabi@example.com');
  INSERT INTO customers VALUES (default, 'Chapy','Morinho','chaps@example.com');

```


```
# Connect to MySQL 2, check server UUID and create two records
docker-compose exec mysql2 bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD inventory'
  SHOW GLOBAL VARIABLES LIKE 'server_uuid';
  INSERT INTO customers VALUES (default, 'Alexandre','Bolot','alexandre.bolot@example.com');
  INSERT INTO customers VALUES (default, 'Corentin','Artaud','corentin.artaud@example.com');
```

