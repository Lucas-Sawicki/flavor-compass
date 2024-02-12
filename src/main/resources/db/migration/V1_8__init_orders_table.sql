CREATE TABLE orders (
    order_id        SERIAL                      NOT NULL,
    customer_id     INT                         NOT NULL,
    restaurant_id   INT                         NOT NULL,
    order_date      TIMESTAMP WITH TIME ZONE    NOT NULL,
    order_number    BIGINT                      NOT NULL,
    menu_item_id    INT                         NOT NULL,
    status          VARCHAR(20)                 DEFAULT 'pending',
    delivery_time   TIME ,

     PRIMARY KEY (order_id),
     UNIQUE (order_number),
     CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (customer_id),
     CONSTRAINT fk_order_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
     CONSTRAINT fk_order_menu_item
            FOREIGN KEY (menu_item_id)
                REFERENCES menu_item (menu_item_id)

);