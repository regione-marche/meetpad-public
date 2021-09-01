update cdst.ente set descrizione_ente='Regione Marche – P.F. Trasporto Pubblico Locale, Logistica e Viabilità'''
	where codice_fiscale_ente='80008630420' and codice_ufficio='B3RXC7';
	
UPDATE cdst.rubrica_partecipanti
	SET fk_ruolo_partecipante='5'
	WHERE fk_ente=(select id_ente from cdst.ente where codice_fiscale_ente='80008630420' and codice_ufficio='B3RXC7');