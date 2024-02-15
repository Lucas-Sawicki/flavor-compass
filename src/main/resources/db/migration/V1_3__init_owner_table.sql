CREATE TABLE owner
(
    owner_id    SERIAL          NOT NULL,
    name        VARCHAR(32)     NOT NULL,
    surname     VARCHAR(32)     NOT NULL,
    phone       VARCHAR(20)     NOT NULL,
    PRIMARY KEY (owner_id),
    UNIQUE (phone)
);