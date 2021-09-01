INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('15', 'Documentazione Firmata');
	
UPDATE cdst.tipo_evento
	SET fk_oggetto_evento='15'
	WHERE codice='25';