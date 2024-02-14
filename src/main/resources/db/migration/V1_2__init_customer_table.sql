CREATE TABLE customer
(
    customer_id SERIAL          NOT NULL,
    name        VARCHAR(32)     NOT NULL,
    surname     VARCHAR(32)     NOT NULL,
    email       VARCHAR(32)     NOT NULL,
    phone       VARCHAR(20)     NOT NULL,
    password    VARCHAR(128)    NOT NULL,
    address_id  INT             NOT NULL,
    PRIMARY KEY (customer_id),
    UNIQUE (email),
    UNIQUE (phone),
    CONSTRAINT fk_customer_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id)
);