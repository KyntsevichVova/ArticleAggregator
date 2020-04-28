DROP TABLE IF EXISTS article;

CREATE TABLE article (
    id SERIAL NOT NULL,
    year INTEGER,
    title VARCHAR,
    PRIMARY KEY (id)
);