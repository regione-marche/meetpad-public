CREATE OR REPLACE VIEW cdst.ricerca_rubrica_partecipanti AS
	SELECT id_rubrica_partecipanti,
		en.id_ente,
		en.descrizione_ente,
		en.codice_fiscale_ente,
		en.pec_ente AS email,
		tc.codice AS codice_tipologia_conferenza,
		tc.descrizione AS descrizione_tipologia_conferenza,
		rp.codice AS codice_ruolo_partecipante,
		rp.descrizione AS descrizione_ruolo_partecipante
		FROM cdst.rubrica_partecipanti rub
		join cdst.ente en on rub.fk_ente=en.id_ente
		join cdst.ruolo_partecipante rp on rp.codice=rub.fk_ruolo_partecipante
		join cdst.tipologia_conferenza tc on tc.codice=rub.fk_tipologia_conferenza;