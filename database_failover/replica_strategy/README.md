# MySQL Failover Demo

This is a showcase for MySQL deployment in clustered configuration and how to failover.

## Topology

The deployment consists of the following components

* Database
  * MySQL 1 instance (configured as a slave to MySQL 2)
  * MySQL 2 instance (configured as a slave to MySQL 1)
  * HAProxy instance - MySQL 1 is configured as the primary server, MySQL 2 as a backup
  
## Preparing the environment and demonstration

Start the components
```
docker-compose up --build
```


Open a terminal, connect to the first database
```
# Connect to MySQL 1, create two records
  docker-compose exec mysql1 bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD inventory'

# Insert two records
  INSERT INTO customers VALUES (default, 'Sami','Lazrak','sami.lazrak@example.com');
  INSERT INTO customers VALUES (default, 'Walid','Larabi','walid.larabi@example.com');
```

Open a terminal, connect to the second database 
```
# Connect to MySQL 2, check server UUID and create two records
  docker-compose exec mysql2 bash -c 'mysql -u root -p$MYSQL_ROOT_PASSWORD inventory'

# Check that the records are replicated in the mysql2
  SELECT * FROM customers;
  
# Create 2 records on this database
  INSERT INTO customers VALUES (default, 'Alexandre','Bolot','alexandre.bolot@example.com');
  INSERT INTO customers VALUES (default, 'Corentin','Artaud','corentin.artaud@example.com');
```

Go back to the first database

```
# Connect to MySQL 2, check server UUID and create two records
  SELECT * FROM customers;
```

Everything is replicated

