ALTER TABLE cdst.template ADD COLUMN fk_tipologia_conferenza_specializzazione character varying(255);
ALTER TABLE cdst.template ADD CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione)
        REFERENCES cdst.tipologia_conferenza_specializzazione (codice);
update cdst.template set fk_tipologia_conferenza_specializzazione = fk_tipologia_conferenza;
ALTER TABLE cdst.template DROP COLUMN fk_tipologia_conferenza;


ALTER TABLE cdst.rubrica_imprese ADD COLUMN fk_tipologia_conferenza_specializzazione character varying(255);
ALTER TABLE cdst.rubrica_imprese ADD CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione)
        REFERENCES cdst.tipologia_conferenza_specializzazione (codice);
update cdst.rubrica_imprese set fk_tipologia_conferenza_specializzazione = fk_tipologia_conferenza;

ALTER TABLE cdst.rubrica_partecipanti ADD COLUMN fk_tipologia_conferenza_specializzazione character varying(255);
ALTER TABLE cdst.rubrica_partecipanti ADD CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione)
        REFERENCES cdst.tipologia_conferenza_specializzazione (codice);        
update cdst.rubrica_partecipanti set fk_tipologia_conferenza_specializzazione = fk_tipologia_conferenza;


ALTER TABLE cdst.rubrica_richiedenti ADD COLUMN fk_tipologia_conferenza_specializzazione character varying(255);
ALTER TABLE cdst.rubrica_richiedenti ADD CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione)
        REFERENCES cdst.tipologia_conferenza_specializzazione (codice);        
update cdst.rubrica_richiedenti set fk_tipologia_conferenza_specializzazione = fk_tipologia_conferenza;

CREATE OR REPLACE VIEW cdst.ricerca_rubrica_imprese AS
 SELECT ri.id_rubrica_imprese,
    imp.id_impresa,
    imp.denominazione AS nome,
    imp.codice_fiscale,
    imp.partita_iva,
    tc.codice AS codice_tipologia_conferenza,
    tc.descrizione AS descrizione_tipologia_conferenza
   FROM cdst.rubrica_imprese ri
     JOIN cdst.tipologia_conferenza_specializzazione tc ON tc.codice::text = ri.fk_tipologia_conferenza_specializzazione::text
     JOIN cdst.impresa imp ON imp.id_impresa = ri.fk_impresa;
     
CREATE OR REPLACE VIEW cdst.ricerca_rubrica_partecipanti AS
 SELECT rub.id_rubrica_partecipanti,
    en.id_ente,
    en.descrizione_ente,
    en.codice_fiscale_ente,
    en.pec_ente AS email,
    tc.codice AS codice_tipologia_conferenza,
    tc.descrizione AS descrizione_tipologia_conferenza,
    rp.codice AS codice_ruolo_partecipante,
    rp.descrizione AS descrizione_ruolo_partecipante
   FROM cdst.rubrica_partecipanti rub
     JOIN cdst.ente en ON rub.fk_ente = en.id_ente
     JOIN cdst.ruolo_partecipante rp ON rp.codice::text = rub.fk_ruolo_partecipante::text
     JOIN cdst.tipologia_conferenza_specializzazione tc ON tc.codice::text = rub.fk_tipologia_conferenza_specializzazione::text;

CREATE OR REPLACE VIEW cdst.ricerca_rubrica_richiedenti AS
 SELECT rr.id_rubrica_richiedenti,
    tc.codice AS codice_tipo_conf_rubrica_richiedenti,
    tc.descrizione AS descrizione_tipo_conf_rubrica_richiedenti,
    tcf.codice AS codice_tipo_conf_rubrica_imprese,
    tcf.descrizione AS descrizione_tipo_conf_rubrica_imprese,
    per.id_persona,
    per.nome AS nome_richiedente,
    per.cognome AS cognome_richiedente,
    per.codice_fiscale AS codice_fiscale_richiedente,
    im.id_impresa,
    im.denominazione AS descrizione_impresa
   FROM cdst.rubrica_richiedenti rr
     JOIN cdst.tipologia_conferenza_specializzazione tc ON tc.codice::text = rr.fk_tipologia_conferenza_specializzazione::text
     JOIN cdst.persona per ON rr.fk_persona = per.id_persona
     LEFT JOIN cdst.rubrica_imprese ri ON rr.fk_rubrica_imprese = ri.id_rubrica_imprese
     LEFT JOIN cdst.tipologia_conferenza_specializzazione tcf ON tcf.codice::text = ri.fk_tipologia_conferenza_specializzazione::text
     LEFT JOIN cdst.impresa im ON ri.fk_impresa = im.id_impresa;
     
ALTER TABLE cdst.rubrica_imprese DROP COLUMN fk_tipologia_conferenza;
ALTER TABLE cdst.rubrica_partecipanti DROP COLUMN fk_tipologia_conferenza;
ALTER TABLE cdst.rubrica_richiedenti DROP COLUMN fk_tipologia_conferenza;

ALTER TABLE cdst.conferenza ADD COLUMN fk_tipologia_conferenza_specializzazione character varying(255);
ALTER TABLE cdst.conferenza ADD CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione)
        REFERENCES cdst.tipologia_conferenza_specializzazione (codice);
update cdst.conferenza set fk_tipologia_conferenza_specializzazione = fk_tipologia_conferenza;

DROP VIEW cdst.mailer_indizione;
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
    cc.fk_tipologia_conferenza_specializzazione,
    tc.descrizione AS descrizione_tipologia_conferenza_specializzazione,
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
     JOIN cdst.tipologia_conferenza_specializzazione tc ON tc.codice::text = cc.fk_tipologia_conferenza_specializzazione::text
     JOIN cdst.comune cm ON cm.codice::text = cc.fk_comune_sessione_simultanea::text
     JOIN cdst.provincia pv ON pv.codice::text = cc.fk_provincia_sessione_simultanea::text
     JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente
  WHERE acc.flag_accreditato = true;

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
     JOIN cdst.tipologia_conferenza_specializzazione tc ON tc.codice::text = cc.fk_tipologia_conferenza_specializzazione::text
     JOIN cdst.provincia pr ON pr.codice::text = cc.fk_localizzazione_provincia::text
     JOIN cdst.comune cm ON cm.codice::text = cc.fk_localizzazione_comune::text
     JOIN cdst.stato st ON st.codice::text = cc.fk_stato::text
     JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente;
     
ALTER TABLE cdst.conferenza DROP COLUMN fk_tipologia_conferenza;