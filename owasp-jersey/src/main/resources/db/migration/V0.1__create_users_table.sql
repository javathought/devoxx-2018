-- V0.2__create_users_table.sql

create database devoxx_tia;

GRANT ALL ON devoxx_tia.* TO 'test'@'%';

CREATE TABLE devoxx_tia.users (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  uuid        BINARY(16) UNIQUE,
  name        VARCHAR(25),
  password    VARCHAR(71)
);