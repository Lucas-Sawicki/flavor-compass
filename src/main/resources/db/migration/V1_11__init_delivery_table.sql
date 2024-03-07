CREATE TABLE delivery_range (
    delivery_range_id SERIAL PRIMARY KEY,
    city VARCHAR(40),
    street VARCHAR(40)
);


CREATE TABLE delivery (
    restaurant_id       INT NOT NULL,
    delivery_range_id INT NOT NULL,
    CONSTRAINT fk_restaurant_delivery_range
        FOREIGN KEY (restaurant_id)
             REFERENCES restaurant(restaurant_id),
    CONSTRAINT fk_delivery_range_restaurant
      FOREIGN KEY (delivery_range_id)
            REFERENCES delivery_range(delivery_range_id)
);