CREATE OR REPLACE VIEW cdst.ricerca_rubrica_delegati
AS SELECT rr.id_rubrica_delegato,
    tc.codice AS codice_tipo_conf_rubrica_delegati,
    tc.descrizione AS descrizione_tipo_conf_rubrica_delegati,
    per.id_persona,
    per.nome AS nome_delegato,
    per.cognome AS cognome_delegato,
    per.codice_fiscale AS codice_fiscale_delegato,  
   	rr.nome_documento as nome_documento,
   	rr.rif_esterno AS rif_esterno
   FROM rubrica_delegati rr
     JOIN tipologia_conferenza_specializzazione tc ON tc.codice::text = rr.fk_tipologia_conferenza_specializzazione::text
     JOIN persona per ON rr.fk_persona = per.id_persona;