INSERT INTO users (user_id, email, password, active)
VALUES
    (1, 'john@example.com', 'password123', TRUE),
    (2, 'jane@example.com', 'password456', TRUE),
    (3, 'alice@example.com', 'password123', TRUE),
    (4, 'bob@example.com', 'password456', TRUE);

INSERT INTO users_roles (user_id, role_id) VALUES
    (1, 1),
    (2, 1),
    (3, 2),
    (4, 2);

INSERT INTO address (address_id, country, city, postal_code, street)
VALUES
    (1, 'USA', 'New York', '10001', 'Broadway'),
    (2, 'UK', 'London', 'SW1A 1AA', 'Westminster'),
    (3, 'USA', 'Las Vegas', '10002', 'Casino Royal'),
    (4, 'UK', 'London', 'SW1B 1BB', 'Short Way');

INSERT INTO owner (owner_id, name, surname, phone, user_id)
VALUES
    (1, 'John', 'Doe', '+22 222 222 222', 1),
    (2, 'Jane', 'Smith', '+33 333 333 333', 2);

INSERT INTO customer (customer_id, name, surname, phone, user_id, address_id)
VALUES
    (1, 'Alice', 'Johnson', '+00 000 000 000', 3, 1),
    (2, 'Bob', 'Smith', '+11 111 111 111', 4, 2);

INSERT INTO restaurant (restaurant_id, local_name, phone, website, email, address_id, owner_id)
VALUES
    (1, 'Restaurant A', '+33 123 456 890', 'www.restaurantA.com', 'info@restaurantA.com', 3, 1),
    (2, 'Restaurant B', '+22 987 654 320', 'www.restaurantB.com', 'info@restaurantB.com', 4, 2);

INSERT INTO opening_hours (opening_hours_id, day_of_the_week, open_time, close_time, delivery_start_time, delivery_end_time)
VALUES
    (1, 'MONDAY', '12:00:00', '00:00:00', '12:00:00', '00:00:00'),
    (2, 'TUESDAY', '12:00:00', '00:00:00', '12:00:00', '00:00:00'),
    (3, 'WEDNESDAY','12:00:00', '00:00:00', '12:00:00', '00:00:00'),
    (4, 'THURSDAY','12:00:00', '00:00:00', '12:00:00', '00:00:00'),
    (5, 'FRIDAY', '12:00:00', '00:00:00', '12:00:00', '00:00:00'),
    (6, 'SATURDAY', '12:00:00', '00:00:00', '12:00:00', '00:00:00'),
    (7, 'SUNDAY', '12:00:00', '00:00:00', '12:00:00', '00:00:00');
INSERT INTO restaurant_opening_hours (restaurant_id, opening_hours_id)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (2, 1),
    (2, 2),
    (2, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7);

INSERT INTO orders (order_id, order_date, order_number, status, delivery_time, customer_id, restaurant_id)
VALUES
    (1, '2024-03-15 10:00:00', 2024030874121, 'PENDING', '12:00:00', 1, 1),
    (2, '2024-03-15 11:00:00', 2024030874122, 'COMPLETED', '13:00:00', 2, 2);

INSERT INTO menu_item (menu_item_id, name, category, description, price, photo_url, restaurant_id)
VALUES
    (1, 'Item 1', 'Category A', 'Description A', 10.99, 'url1', 1),
    (2, 'Item 2', 'Category B', 'Description B', 8.50, 'url2', 1),
    (3, 'Item 3', 'Category C', 'Description C', 12.75, 'url3', 2),
    (4, 'Item 4', 'Category D', 'Description D', 9.99, 'url4', 2);

INSERT INTO order_item (order_item_id, quantity, menu_item_id, order_id)
VALUES
    (1, 2, 1, 1),
    (2, 1, 2, 1),
    (3, 3, 3, 2),
    (4, 2, 4, 2);


