create table if not exists client
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

create table if not exists tour_agent
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NOT NULL
)