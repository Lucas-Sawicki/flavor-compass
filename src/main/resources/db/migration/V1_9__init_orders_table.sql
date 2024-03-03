CREATE TABLE orders
(
    order_id        SERIAL                      NOT NULL,
    order_date      TIMESTAMP WITH TIME ZONE    NOT NULL,
    order_number    BIGINT                      NOT NULL UNIQUE,
    status          VARCHAR(20)                 DEFAULT 'PENDING',
    delivery_time   TIME,
    customer_id     INT                         NOT NULL,
    restaurant_id   INT                         NOT NULL,

    PRIMARY KEY (order_id),
    CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
    CONSTRAINT fk_order_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id)
);