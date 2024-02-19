CREATE TABLE opinion
(
    opinion_id      SERIAL    PRIMARY KEY,
    customer_id     INT       NOT NULL,
    stars           INT       NOT NULL,
    comment         TEXT      NOT NULL,
    opinion_date    TIMESTAMP NOT NULL,
    CONSTRAINT opinion_stars_check CHECK (stars BETWEEN 1 AND 5),
    CONSTRAINT fk_opinion_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
);






