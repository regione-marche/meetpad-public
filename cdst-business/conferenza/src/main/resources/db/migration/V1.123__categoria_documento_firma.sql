INSERT INTO cdst.tipologia_documento(
	codice, descrizione, fk_sezione_documentazione)
	VALUES ('DOCUMENTO_FIRMATO', 'tipologiaDocumento.documentoFirmato', 'DOCUMENTI_FIRMA');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipologia_documento)
	VALUES ('17', 'categoriaDocumento.verbaleFirmato', 'DOCUMENTO_FIRMATO');