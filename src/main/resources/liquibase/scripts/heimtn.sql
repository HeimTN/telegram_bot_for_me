--liquibase formatted sql

--changeset mzaretskiy:1
CREATE TABLE notification_task(
id SERIAL PRIMARY KEY,
chat_id BIGINT NOT NULL,
text_msg TEXT NOT NULL,
time_msg TIMESTAMP NOT NULL
);