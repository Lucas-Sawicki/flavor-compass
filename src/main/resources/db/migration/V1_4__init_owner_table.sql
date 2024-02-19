CREATE TABLE owner
(
    owner_id    SERIAL          NOT NULL,
    name        VARCHAR(32)     NOT NULL,
    surname     VARCHAR(32)     NOT NULL,
    phone       VARCHAR(20)     NOT NULL,
    user_id INT,
    PRIMARY KEY (owner_id),
    UNIQUE (phone),
    CONSTRAINT fk_owner_users
        FOREIGN KEY (user_id) REFERENCES users (user_id)

);