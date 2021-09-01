INSERT INTO cdst.tipologia_documento(
	codice, descrizione, fk_sezione_documentazione)
	VALUES ('VIDEO', 'tipologiaDocumento.video', 'DOCUMENTI_INTERAZIONE');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipologia_documento)
	VALUES ('16', 'categoriaDocumento.videoRiunione', 'VIDEO');