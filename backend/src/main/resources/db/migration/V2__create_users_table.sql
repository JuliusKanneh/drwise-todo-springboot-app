CREATE TABLE users (
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username        VARCHAR(255) UNIQUE NOT NULL CHECK (username <> ''),
    password_hash   VARCHAR(255) NOT NULL CHECK (password_hash <> ''),
    created_date    TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_date   TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
)