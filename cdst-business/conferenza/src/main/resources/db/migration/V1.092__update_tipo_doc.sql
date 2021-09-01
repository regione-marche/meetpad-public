UPDATE cdst.tipologia_documento
	SET fk_sezione_documentazione='DOCUMENTI_AGGIUNTIVI' WHERE codice='DOCUMENTAZIONE_AGGIUNTIVA';
	
UPDATE cdst.tipologia_documento
	SET fk_sezione_documentazione='DOCUMENTI_INTERAZIONE' WHERE codice='INTERAZIONI';
	
UPDATE cdst.tipologia_documento
	SET fk_sezione_documentazione='DOCUMENTI_INDIZIONE' WHERE codice='DOCUMENTO_INDIZIONE';
	
UPDATE cdst.tipologia_documento
	SET fk_sezione_documentazione='DOCUMENTI_PREISTRUTTORIA' WHERE codice='DOCUMENTO_PRE_ISTRUTTORIA';
	
UPDATE cdst.tipologia_documento
	SET fk_sezione_documentazione='DOCUMENTI_INTERAZIONE' WHERE codice='VERBALE_RIUNIONE';
	
UPDATE cdst.tipologia_documento
	SET fk_sezione_documentazione='DOCUMENTI_INTERAZIONE' WHERE codice='DETERMINA';
	
ALTER TABLE cdst.categoria_documento ADD COLUMN fk_tipologia_documento character varying(255);

UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='1';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='2';

UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='3';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='4';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='5';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='6';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTAZIONE_AGGIUNTIVA' WHERE codice='7';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='INTERAZIONI' WHERE codice='8';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='ESPRESSIONE_PARERI' WHERE codice='9';

UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='INTERAZIONI' WHERE codice='10';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='INTERAZIONI' WHERE codice='11';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='VERBALE_RIUNIONE' WHERE codice='12';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DETERMINA' WHERE codice='13';
	
UPDATE cdst.categoria_documento
	SET fk_tipologia_documento='DOCUMENTO_INDIZIONE' WHERE codice='14';
	
INSERT INTO cdst.categoria_documento(codice, descrizione, fk_tipologia_documento) VALUES ('15', 'categoriaDocumento.bozzaVerbale', 'DOCUMENTI_CONDIVISI');