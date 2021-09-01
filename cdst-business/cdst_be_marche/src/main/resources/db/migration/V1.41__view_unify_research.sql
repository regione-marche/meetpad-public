-- View: cdst.ricerca_conferenza

-- DROP VIEW cdst.ricerca_conferenza;

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
	ammpr.id_ente AS id_ente,
	ammpr.descrizione_ente AS descrizione_amministrazione_procedente,
	cc.data_fine
   FROM cdst.conferenza cc
     JOIN cdst.tipologia_conferenza tc ON tc.codice::text = cc.fk_tipologia_conferenza::text
     JOIN cdst.provincia pr ON pr.codice::text = cc.fk_localizzazione_provincia::text
     JOIN cdst.comune cm ON cm.codice::text = cc.fk_localizzazione_comune::text
     JOIN cdst.stato st ON st.codice::text = cc.fk_stato::text
	 JOIN cdst.ente ammpr ON ammpr.id_ente = cc.fk_ente_procedente;


