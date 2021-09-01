
UPDATE cdst.permesso_azione
	SET descrizione='caricamento verbale riunione interno', fk_tipo_evento='31'
	WHERE codice='INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO';

	
UPDATE cdst.categoria_documento
	SET descrizione='categoriaDocumento.verbaleConferenza', fk_tipo_evento='31', fk_tipologia_documento='VERBALE_RIUNIONE'
	WHERE codice='28';
	