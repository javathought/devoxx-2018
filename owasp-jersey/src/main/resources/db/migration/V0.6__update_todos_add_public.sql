-- V0.4__update_users_add_bearer.sql
ALTER TABLE devoxx_tia.todos ADD
public TINYINT(1) not null default 0;