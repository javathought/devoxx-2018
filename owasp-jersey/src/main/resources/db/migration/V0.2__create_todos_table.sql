-- V0.3__create_todos_table.sql

CREATE TABLE devoxx_tia.todos (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  uuid        BINARY(16) UNIQUE,
  user_id     BIGINT,
  summary     VARCHAR(50),
  description TEXT
);