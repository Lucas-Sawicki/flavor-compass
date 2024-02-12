CREATE TABLE owner
(
    owner_id    SERIAL          NOT NULL,
    name        VARCHAR(32)     NOT NULL,
    surname     VARCHAR(32)     NOT NULL,
    email       VARCHAR(32)     NOT NULL,
    password    VARCHAR(128)    NOT NULL,
    PRIMARY KEY (owner_id),
    UNIQUE (email)
);