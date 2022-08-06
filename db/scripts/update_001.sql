create table if not exists accounts(
                                       id SERIAL PRIMARY KEY,
                                       name VARCHAR NOT NULL UNIQUE ,
                                       login VARCHAR NOT NULL UNIQUE,
                                       password VARCHAR
);

create table if not exists items(
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR not null,
                                    descriptionItem VARCHAR not null ,
                                    created TIMESTAMP,
                                    done BOOLEAN,
                                    account_id INT REFERENCES accounts(id)
);


ALTER TABLE items ADD CONSTRAINT
