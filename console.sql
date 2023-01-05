

CREATE DATABASE car_rental;

--Таблица Клиент с полями: id, фамилия и имя клиента, паспорт, телефонный номер, стаж вождения

CREATE TABLE client
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    passport VARCHAR(32) NOT NULL,
    phone_number VARCHAR(128) NOT NULL,
    driving_experience INT NOT NULL
);

--Таблица Персонал с полями: id, фамилия и имя сотрудника, должность, телефон

CREATE TABLE staff
(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    position VARCHAR(32) NOT NULL,
    phone_number VARCHAR(128) NOT NULL

);

--Таблица Тип кузова автомобиля

CREATE TABLE body_type
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
);

--Таблица Модель автомобиля с полями: id, название модели, вместимость

CREATE TABLE model
(
    id SERIAL PRIMARY KEY,
    model_name VARCHAR(128) NOT NULL,
    capacity INT NOT NULL
);

--Таблица статус автомобиля (т.е. может быть в прокате, свободен, в ремонте) с полями: id, статус

CREATE TABLE rental_status
(
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);


--Таблица Автомобиль с полями: id, марка автомобиля, id модели,  id типа кузова, цвет, год выпуска, статус

CREATE TABLE car
(
    id SERIAL PRIMARY KEY,
    brand_name VARCHAR(32) NOT NULL,
    model_id INT REFERENCES model(id),
    body_type_id INT REFERENCES body_type(id),
    color VARCHAR(32) NOT NULL,
    year_issue INT NOT NULL,
    status_id INT REFERENCES rental_status(id)

);

--Таблица Повреждения автомобиля с полями: дата, id клиента, id автомобиля, описание повреждения

CREATE TABLE car_damage
(
    id SERIAL PRIMARY KEY,
    date_damage DATE NOT NULL,
    client_id INT REFERENCES client(id),
    car_id INT REFERENCES car(id),
    damage_description VARCHAR(128) NOT NULL

);

--Таблица Прокат автомобиля с полями: id, день начала аренды, продолжительность(в днях), id клиента-арендатора,
-- id автомобиля, стоимость аренды в сутки, id администратора

CREATE TABLE rent
(
    id SERIAL PRIMARY KEY,
    date_start DATE NOT NULL,
    duration INT NOT NULL,
    client_id INT REFERENCES client(id),
    car_id INT REFERENCES car(id),
    rental_price DOUBLE PRECISION NOT NULL,
    administrator_id INT REFERENCES staff(id)
);



