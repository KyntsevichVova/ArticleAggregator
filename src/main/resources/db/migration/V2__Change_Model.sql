DROP TABLE IF EXISTS repo;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS article_author;

CREATE TABLE repo (
    id SERIAL,
    link VARCHAR,
    name VARCHAR,
    PRIMARY KEY(id)
);

CREATE TABLE author (
    id SERIAL,
    repo_id INTEGER,
    name VARCHAR NOT NULL,
    link VARCHAR,
    PRIMARY KEY(id),
    FOREIGN KEY(repo_id) REFERENCES repo(id)
);

CREATE TABLE article (
    id SERIAL,
    repo_id INTEGER,
    article_id VARCHAR,
    link VARCHAR NOT NULL,
    title VARCHAR,
    annotation TEXT,
    article_text TEXT,
    -- date_published, date_scrapped
    PRIMARY KEY(id),
    FOREIGN KEY(repo_id) REFERENCES repo(id)
);

CREATE TABLE article_author (
    article_id INTEGER,
    author_id INTEGER,
    PRIMARY KEY(article_id, author_id),
    FOREIGN KEY(article_id) REFERENCES article(id),
    FOREIGN KEY(author_id) REFERENCES author(id)
);

INSERT INTO repo (name, link) VALUES
    ('bsuir', 'libeldoc.bsuir.by');