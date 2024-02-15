CREATE TABLE restaurant (
    restaurant_id       SERIAL          NOT NULL,
    local_name          VARCHAR(100)    NOT NULL,
    address_id          INT             NOT NULL,
    phone               VARCHAR(20)     NOT NULL,
    email               VARCHAR(32)     NOT NULL,
    opening_hours_id    INT             NOT NULL,
    website             VARCHAR(64),
    owner_id            INT             NOT NULL,
    opinion_id          INT,



    PRIMARY KEY (restaurant_id),
      UNIQUE (phone),
      UNIQUE (email),
    CONSTRAINT fk_restaurant_address
        FOREIGN KEY (address_id)
            REFERENCES address (address_id),
    CONSTRAINT fk_restaurant_owner
        FOREIGN KEY (owner_id)
            REFERENCES owner (owner_id),
    CONSTRAINT fk_restaurant_opinion
                FOREIGN KEY (opinion_id)
                    REFERENCES opinion (opinion_id)
);