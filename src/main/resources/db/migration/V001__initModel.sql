drop table if exists cars CASCADE;
drop table if exists orders CASCADE;

create table cars
(
    car_id            serial primary key,
    car_name          text not null,
    car_license_plate text not null
);

create index cars_index on cars (car_id);

create table orders
(
    order_id    serial primary key,
    order_price float not null,
    order_date  timestamp not null
);

create index orders_index on orders (order_id);