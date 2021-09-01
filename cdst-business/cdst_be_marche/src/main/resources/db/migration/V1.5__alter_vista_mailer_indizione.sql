DROP VIEW cdst.mailer_indizione;

CREATE OR REPLACE VIEW cdst.mailer_indizione AS
 SELECT cc.fk_stato,
    pp.id_persona,
    pp.nome,
    pp.cognome,
    pp.codice_fiscale,
    ee.id_ente,
    pc.id_partecipante,
    ee.codice_fiscale_ente,
    ee.descrizione_ente,
    cc.fk_ente_procedente,
    ammpr.descrizione_ente AS descrizione_amministrazione_procedente,
        CASE btrim(pp.email::text)
            WHEN ''::text THEN ee.pec_ente
            ELSE pp.email
        END AS email,
    acc.fk_ruolo_persona,
    rp.descrizione AS descrizione_ruolo_persona,
    cc.fk_tipologia_conferenza,
    tc.descrizione AS descrizione_tipologia_conferenza,
    pc.fk_ruolo_partecipante,
    rpart.descrizione AS descrizione_ruolo_partecipante,
    pc.competenza,
    cc.id_conferenza,
    cc.oggetto_determinazione,
    cc.localizzazione_indirizzo,
    cc.termine_richiesta_integrazioni_conferenza,
    cc.termine_espressione_pareri,
    cc.prima_sessione_simultanea,
    cc.riferimento_istanza,
    cc.indirizzo_sessione_simultanea
   FROM accreditamento acc
     JOIN persona pp ON acc.fk_persona = pp.id_persona
     JOIN partecipante pc ON pc.id_partecipante = acc.fk_partecipante
     JOIN conferenza cc ON cc.id_conferenza = pc.fk_conferenza
     JOIN stato ss ON ss.codice::text = cc.fk_stato::text
     JOIN ente ee ON ee.id_ente = pc.fk_ente
     JOIN ruolo_partecipante rpart ON rpart.codice::text = pc.fk_ruolo_partecipante::text
     JOIN ruolo_persona rp ON rp.codice::text = acc.fk_ruolo_persona::text
     JOIN tipologia_conferenza tc ON tc.codice::text = cc.fk_tipologia_conferenza::text
     JOIN ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente
  WHERE acc.flag_accreditato = true;

ALTER TABLE cdst.mailer_indizione
    OWNER TO cdst;

