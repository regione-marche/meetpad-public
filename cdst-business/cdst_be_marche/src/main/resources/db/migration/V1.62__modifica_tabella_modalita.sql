UPDATE cdst.conferenza SET fk_modalita_sessione_simultanea='1' WHERE fk_modalita_sessione_simultanea='3';

DELETE FROM cdst.modalita WHERE codice = '3';

UPDATE cdst.modalita SET descrizione='Riunione on line' WHERE codice='1';
UPDATE cdst.modalita SET descrizione='Riunione fisica' WHERE codice='2';