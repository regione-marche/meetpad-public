	
	INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato) VALUES('31', 'Caricamento verbale riunione interno', '5', 'VERBALE_RIUNIONE', true, true);
	INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato) VALUES('32', 'Archiviazione Verbale Riunione', '5', 'VERBALE_RIUNIONE', false, false);

	INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO', 'caricamento verbale riunione interno');
	
	INSERT INTO cdst.permesso(fk_permesso_ruolo, fk_permesso_azione, permission_strategy) VALUES ('RESPONSABILE', 'INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO', 'default_permission_strategy');
	INSERT INTO cdst.permesso(fk_permesso_ruolo, fk_permesso_azione, permission_strategy) VALUES ('CREATORE', 'INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE_INTERNO', 'default_permission_strategy');
	
	INSERT INTO cdst.observer_registry (id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione, priority) VALUES(NULL, 'SCRITTURA-DOCUMENT-INTRA-PALEO', 'Archiviazione documentale', '31', 'PaleoObserverDocumentaleIntraListnerInterface', '32', NULL, 9);
