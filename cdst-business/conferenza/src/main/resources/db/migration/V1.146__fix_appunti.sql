UPDATE cdst.categoria_documento
SET fk_tipologia_documento='DOCUMENTI_CONDIVISI'
WHERE codice='18';

DELETE FROM cdst.tipologia_documento
WHERE codice='APPUNTI';
