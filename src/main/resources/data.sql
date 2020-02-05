DROP TABLE IF EXISTS CARS;
DROP TABLE IF EXISTS MANUFACTURER;

CREATE TABLE manufacturer
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    founded INT          NOT NULL
);

CREATE TABLE cars
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    model           VARCHAR(255) NOT NULL,
    price           double       NOT NULL,
    production_year DATE         NOT NULL,
    manufacturer_id INT,
    FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id)
);

INSERT INTO manufacturer (name, founded)
VALUES ('test manufacturer', 2010);

INSERT INTO cars (model, price, production_year, manufacturer_id)
VALUES ('test', 100.00, NOW(), 1);