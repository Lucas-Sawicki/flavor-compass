CREATE TABLE restaurant (
    restaurant_id       SERIAL          NOT NULL,
    local_name          VARCHAR(100)    NOT NULL,
    address_id          INT             NOT NULL,
    phone               VARCHAR(20)     NOT NULL,
    email               VARCHAR(32)     NOT NULL,
    website             VARCHAR(64),
    owner_id            INT             NOT NULL,


    PRIMARY KEY (restaurant_id),
      UNIQUE (phone),
      UNIQUE (email),
    CONSTRAINT fk_restaurant_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id),
    CONSTRAINT fk_restaurant_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id)
);

CREATE TABLE restaurant_opening_hours (
    restaurant_id INT,
    opening_hours_id INT,
    opening_hours_key VARCHAR(10),
    PRIMARY KEY (restaurant_id, opening_hours_id),
    CONSTRAINT fk_restaurant_opening_hours
            FOREIGN KEY (restaurant_id)
                REFERENCES restaurant (restaurant_id),
    CONSTRAINT fk_opening_hours_restaurant
            FOREIGN KEY (opening_hours_id)
                REFERENCES opening_hours (opening_hours_id)
);