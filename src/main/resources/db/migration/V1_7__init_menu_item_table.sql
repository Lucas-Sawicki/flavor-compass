CREATE TABLE menu_item
(
    menu_item_id    SERIAL        NOT NULL,
    name            VARCHAR(25)   NOT NULL,
    category        VARCHAR(50)   NOT NULL,
    description     TEXT          NOT NULL,
    price           DECIMAL(7, 2) NOT NULL,
    photo_url       VARCHAR(200)  NOT NULL,
    restaurant_id   INT           NOT NULL,

    PRIMARY KEY (menu_item_id),
    CONSTRAINT fk_menu_item_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id)
);