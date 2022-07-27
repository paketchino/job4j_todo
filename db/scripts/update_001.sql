create table if not exists accounts(
        id SERIAL PRIMARY KEY,
        name VARCHAR,
        login VARCHAR,
        password VARCHAR
);

create table if not exists items(
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    descriptionItem VARCHAR,
    created TIMESTAMP,
    done BOOLEAN,
    account_id INT NOT NULL REFERENCES accounts(id)
);
