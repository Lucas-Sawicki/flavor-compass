CREATE TABLE roles
(
    role_id SERIAL NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (role_id),
    UNIQUE (role)
);
CREATE TABLE users
(
    user_id SERIAL NOT NULL,
    email       VARCHAR(32)     NOT NULL,
    password VARCHAR(128) NOT NULL,
    active BOOLEAN NOT NULL,
    UNIQUE (email),
    PRIMARY KEY (user_id)
);

CREATE TABLE users_roles (
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    PRIMARY KEY (user_id, role_id)
);


insert into roles (role_id, role) values (1, 'OWNER'), (2,'CUSTOMER'), (3, 'REST_API'), (4, 'ADMIN');

