INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('13', 'Protocollazione Paleo');
	
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('20', 'Protocollazione Documento Indizione', '13', 'DOCUMENTO_INDIZIONE', false, false);
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('21', 'Protocollazione Invia Integrazioni', '13', 'INTERAZIONI', false, false);
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('22', 'Protocollazione Richiesta Unica Integrazioni', '13', 'INTERAZIONI', false, false);
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('23', 'Protocollazione Verbale Riunione', '13', 'VERBALE_RIUNIONE', false, false);