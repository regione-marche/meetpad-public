INSERT INTO cdst.tipo_evento(codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('13', 'Trasmissione Integrazioni protocollate', '1', 'INTERAZIONI', 'true', 'true');
	
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc)
	VALUES ('13', '5', 'true');