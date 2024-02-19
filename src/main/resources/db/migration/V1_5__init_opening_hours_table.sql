CREATE TABLE opening_hours (

    opening_hours_id        SERIAL       NOT NULL,
    day_of_the_week         VARCHAR(20)  NOT NULL,
    open_time               TIME         NOT NULL,
    close_time              TIME         NOT NULL,
    delivery_start_time     TIME         NOT NULL,
    delivery_end_time       TIME         NOT NULL
);