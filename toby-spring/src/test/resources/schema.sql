DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       VARCHAR(10) PRIMARY KEY,
    name     VARCHAR(20) NOT NULL,
    password VARCHAR(10) NOT NULL
);
