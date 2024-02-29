CREATE TABLE opinion
(
    opinion_id      SERIAL    PRIMARY KEY,
    customer_id     INT       NOT NULL,
    stars           INT       NOT NULL,
    comment         TEXT      NOT NULL,
    opinion_date    TIMESTAMP NOT NULL,
    restaurant_id   INT       ,
    menu_item_id    INT       ,
    CONSTRAINT opinion_stars_check CHECK (stars BETWEEN 1 AND 5),
    CONSTRAINT fk_opinion_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id),
     CONSTRAINT fk_restaurant_opinion
        FOREIGN KEY (restaurant_id)
            REFERENCES restaurant (restaurant_id),
      CONSTRAINT fk_menu_item_opinion
        FOREIGN KEY (menu_item_id)
            REFERENCES menu_item (menu_item_id)
);






