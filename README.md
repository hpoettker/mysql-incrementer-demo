# MySQL incrementer demo

## Introduction

This repository allows to easily start two MySQL instances with active-active replication.

The two instances listen on ports 3306 and 3307, respectively.

The credentials for both are `root/root` and `user/password`.

The Spring Boot application is a show-case for `MysqlIdentityColumnMaxValueIncrementer`, which is safe to use with
MySQL 8 and an active-active replication.

## Starting and Stopping the containers

### Starting the containers

To start the containers, change to the project directory and use

```shell
docker-compose up -d
```

After half a minute, both containers should have started and be healthy:

```shell
docker ps
```

### Checking source status

You can check the source status of the instances with

```shell
docker exec mysql-1 mysql -u root -proot -e "SHOW MASTER STATUS\G"
```

and

```shell
docker exec mysql-2 mysql -u root -proot -e "SHOW MASTER STATUS\G"
```

### Checking replica status

You can check the replica status of the instances with

```shell
docker exec mysql-1 mysql -u root -proot -e "SHOW REPLICA STATUS\G"
```

and

```shell
docker exec mysql-2 mysql -u root -proot -e "SHOW REPLICA STATUS\G"
```

### Stopping the containers

To stop the containers, change to the project directory and use

```shell
docker-compose down
```
