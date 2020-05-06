ALTER TABLE repo ADD hostname VARCHAR;

UPDATE repo SET hostname = 'https://libeldoc.bsuir.by' WHERE name = 'bsuir';

ALTER TABLE repo ALTER COLUMN hostname SET NOT NULL;