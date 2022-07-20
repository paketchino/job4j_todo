create table if not exists items(
    id SERIAL PRIMARY KEY,
    name varchar,
    descriptionItem VARCHAR,
    created TIMESTAMP,
    done BOOLEAN
)