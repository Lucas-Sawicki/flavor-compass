CREATE TABLE order_item (
    order_item_id   INT           NOT NULL,
    order_id        INT           NOT NULL,
    menu_item_id    INT           NOT NULL,
    quantity        INT           NOT NULL,

    PRIMARY KEY (order_item_id),
    CONSTRAINT fk_order_item_order
            FOREIGN KEY (order_id)
                REFERENCES orders (order_id),
    CONSTRAINT fk_order_item_menu_item_id
                FOREIGN KEY (menu_item_id)
                    REFERENCES menu_item (menu_item_id)
);