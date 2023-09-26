--liquibase formatted sql

--changeset mzaretskiy:1
CREATE TABLE notification_task(
id SERIAL PRIMARY KEY,
chat_id BIGINT NOT NULL,
text_msg TEXT NOT NULL,
time_msg TIMESTAMP NOT NULL
);
--changeset mzaretskiy:2
CREATE SEQUENCE hibernate_sequence
  START 1
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  CACHE 1;