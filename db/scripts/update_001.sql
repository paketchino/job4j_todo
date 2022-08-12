drop table accounts cascade ;

drop table items cascade ;

drop table categories cascade ;

drop table  items_categories cascade ;

create table if not exists accounts(
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR NOT NULL UNIQUE ,
                                       login VARCHAR NOT NULL UNIQUE,
                                       password VARCHAR
);

create table if not exists items (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR not null,
                                    description VARCHAR(255) not null,
                                    created TIMESTAMP,
                                    done BOOLEAN
);


create table if not exists categories
(
    id serial primary key,
    name varchar(90)

);

create table if not exists items_categories
(
    categories_id int not null references categories(id),
    items_id int not null references items(id)
);

