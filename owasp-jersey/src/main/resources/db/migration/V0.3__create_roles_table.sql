-- V0.3__create_roles_table.sql

CREATE TABLE devoxx_tia.roles (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  uuid        BINARY(16) UNIQUE,
  name        VARCHAR(25),
  ui_role     VARCHAR(25)
);

CREATE TABLE devoxx_tia.user_roles (
  user_id     BIGINT NOT NULL,
  role_id     BIGINT NOT NULL,
  INDEX user_index (user_id)
);