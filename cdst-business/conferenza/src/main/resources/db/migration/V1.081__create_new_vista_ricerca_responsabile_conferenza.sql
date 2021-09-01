CREATE OR REPLACE VIEW cdst.ricerca_responsabile_conferenza AS
 SELECT rc.id_responsabile_conferenza,
 	en.id_ente AS id_amministrazione_procedente,
    en.descrizione_ente AS amministrazione_procedente,
    ps.id_persona AS id_persona_responsabile,
    ps.nome AS nome_responsabile,
    ps.cognome AS cognome_responsabile,
    ps.codice_fiscale AS codice_fiscale_responsabile
   FROM cdst.responsabile_conferenza rc
     JOIN cdst.persona ps ON ps.id_persona::text = rc.fk_persona::text
     JOIN cdst.ente en ON en.id_ente::text = rc.fk_ente::text;