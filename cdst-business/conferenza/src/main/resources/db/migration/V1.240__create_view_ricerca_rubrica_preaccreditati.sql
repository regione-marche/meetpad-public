CREATE OR REPLACE VIEW cdst.ricerca_rubrica_preaccreditati
AS SELECT rr.id_rubrica_preaccreditato,
    tc.codice AS codice_tipo_conf_preaccreditato,
    tc.descrizione AS descrizione_tipo_conf_preaccreditato,
    rp.codice AS codice_tipo_profilo_preaccreditato,
    rp.descrizione AS descrizione_tipo_profilo_preaccreditato,
    per.id_persona,
    per.nome AS nome_preaccreditato,
    per.cognome AS cognome_preaccreditato,
    per.codice_fiscale AS codice_fiscale_preaccreditato,
    ee.codice_fiscale_ente AS codice_fiscale_ente_preaccreditato,
    ee.descrizione_ente AS descrizione_ente_preaccreditato,
    ee.id_ente AS id_ente
   FROM cdst.rubrica_preaccreditati rr
     JOIN cdst.tipologia_conferenza_specializzazione tc ON tc.codice::text = rr.fk_tipologia_conferenza_specializzazione::text
     JOIN cdst.ruolo_persona rp ON rp.codice::text = rr.fk_ruolo_persona::text
     JOIN ente ee ON ee.id_ente = rr.fk_ente
     JOIN cdst.persona per ON rr.fk_persona = per.id_persona;