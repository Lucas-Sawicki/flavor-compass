CREATE TABLE menu_item
(
    menu_item_id    SERIAL        NOT NULL,
    name            VARCHAR(25)   NOT NULL,
    category        VARCHAR(50)   NOT NULL,
    description     TEXT          NOT NULL,
    price           DECIMAL(7, 2) NOT NULL,
    available       BOOLEAN       NOT NULL,
    opinion_id      INT,
    photo_url       VARCHAR(200),
    PRIMARY KEY (menu_item_id),
    CONSTRAINT fk_menu_item_opinion
            FOREIGN KEY (opinion_id)
                REFERENCES opinion (opinion_id)
);