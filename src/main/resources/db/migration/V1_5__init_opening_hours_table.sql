CREATE TABLE opening_hours (
    opening_hours_id        SERIAL       NOT NULL,
    day_of_the_week         VARCHAR(20)  NOT NULL,
    open_time               TIME         ,
    close_time              TIME         ,
    delivery_start_time     TIME         ,
    delivery_end_time       TIME         ,

    PRIMARY KEY (opening_hours_id)
);