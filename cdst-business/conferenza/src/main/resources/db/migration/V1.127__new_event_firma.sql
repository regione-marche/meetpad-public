INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('25', 'Caricamento Documento Firma', 'DOCUMENTO_FIRMATO', true, true);
	
UPDATE cdst.categoria_documento
	SET  descrizione='categoriaDocumento.documentoFirmato', fk_tipo_evento='25'
	WHERE codice='17';