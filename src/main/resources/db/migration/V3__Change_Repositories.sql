ALTER TABLE repo ADD hostname VARCHAR;

UPDATE repo SET hostname = 'https://libeldoc.bsuir.by' WHERE name = 'bsuir';

ALTER TABLE repo MODIFY hostname VARCHAR NOT NULL;