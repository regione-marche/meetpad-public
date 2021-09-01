ALTER TABLE cdst.ente ADD COLUMN descrizione_comune character varying(255);
ALTER TABLE cdst.ente ADD COLUMN codice_ou character varying(255);
ALTER TABLE cdst.ente ADD COLUMN codice_ou_padre character varying(255);

DROP VIEW cdst.ricerca_conferenza;
DROP VIEW cdst.mailer_indizione;
ALTER TABLE cdst.ente ALTER COLUMN descrizione_ente TYPE character varying(1000);
CREATE OR REPLACE VIEW cdst.ricerca_conferenza AS
 SELECT cc.id_conferenza,
    cc.riferimento_istanza,
    tc.codice AS codice_tipologia_conferenza,
    tc.descrizione AS descrizione_tipologia_conferenza,
    st.codice AS codice_stato,
    st.descrizione AS descrizione_stato,
    cc.codice_fiscale_richiedente,
    cc.nome_richiedente,
    cc.cognome_richiedente,
    cc.cf_responsabile_conferenza,
    cc.cf_creatore_conferenza,
    pr.codice AS codice_provincia,
    pr.descrizione AS descrizione_provincia,
    cm.codice AS codice_comune,
    cm.descrizione AS descrizione_comune,
    cc.data_termine,
    cc.termine_richiesta_integrazioni_conferenza,
    cc.termine_espressione_pareri,
    cc.prima_sessione_simultanea,
    cc.indirizzo_sessione_simultanea,
    ammpr.id_ente,
    ammpr.descrizione_ente AS descrizione_amministrazione_procedente,
    cc.data_fine
   FROM cdst.conferenza cc
     JOIN cdst.tipologia_conferenza tc ON tc.codice::text = cc.fk_tipologia_conferenza::text
     JOIN cdst.provincia pr ON pr.codice::text = cc.fk_localizzazione_provincia::text
     JOIN cdst.comune cm ON cm.codice::text = cc.fk_localizzazione_comune::text
     JOIN cdst.stato st ON st.codice::text = cc.fk_stato::text
     JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente;
CREATE OR REPLACE VIEW cdst.mailer_indizione AS
 SELECT (pc.id_partecipante || '-'::text) || pp.id_persona AS id,
    cc.fk_stato,
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
    cc.data_termine,
    cc.orario_conferenza,
    cc.termine_espressione_pareri,
    cc.prima_sessione_simultanea,
    cc.riferimento_istanza,
    cc.indirizzo_sessione_simultanea,
    cc.cap_sessione_simultanea,
    cc.fk_comune_sessione_simultanea,
    cm.descrizione AS descrizione_comune_sessione_simultanea,
    cc.fk_provincia_sessione_simultanea,
    pv.descrizione AS descrizione_provincia_sessione_simultanea
   FROM cdst.accreditamento acc
     JOIN cdst.persona pp ON acc.fk_persona = pp.id_persona
     JOIN cdst.partecipante pc ON pc.id_partecipante = acc.fk_partecipante
     JOIN cdst.conferenza cc ON cc.id_conferenza = pc.fk_conferenza
     JOIN cdst.stato ss ON ss.codice::text = cc.fk_stato::text
     JOIN cdst.ente ee ON ee.id_ente = pc.fk_ente
     JOIN cdst.ruolo_partecipante rpart ON rpart.codice::text = pc.fk_ruolo_partecipante::text
     JOIN cdst.ruolo_persona rp ON rp.codice::text = acc.fk_ruolo_persona::text
     JOIN cdst.tipologia_conferenza tc ON tc.codice::text = cc.fk_tipologia_conferenza::text
     JOIN cdst.comune cm ON cm.codice::text = cc.fk_comune_sessione_simultanea::text
     JOIN cdst.provincia pv ON pv.codice::text = cc.fk_provincia_sessione_simultanea::text
     JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente
  WHERE acc.flag_accreditato = true;
  
 