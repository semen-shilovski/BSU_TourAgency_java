create sequence tours_id_seq
increment 1
start 1;

CREATE TABLE if not exists tours
(
    id             INT PRIMARY KEY DEFAULT nextval('tours_id_seq'),
    name           VARCHAR(255) NOT NULL,
    type           VARCHAR(255) NOT NULL,
    price          double precision NOT NULL,
    is_last_minute BOOLEAN NOT NULL,
    discount       DOUBLE precision NOT NULL
);

ALTER TABLE tours
    ADD COLUMN client_id INT
        CONSTRAINT client_id REFERENCES client(id);

ALTER TABLE tours
    ADD COLUMN tour_agent_id INT
        CONSTRAINT tour_agent_id REFERENCES tour_agent(id);