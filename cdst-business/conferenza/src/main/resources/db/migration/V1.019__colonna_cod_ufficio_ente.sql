ALTER TABLE cdst.ente ADD COLUMN codice_ufficio character varying(255);

UPDATE cdst.ente SET codice_ufficio = 'GQJG6M' WHERE id_ente='685';
UPDATE cdst.ente SET codice_ufficio = '0IVWZD' WHERE id_ente='686';
UPDATE cdst.ente SET codice_ufficio = 'B3RXC7' WHERE id_ente='687';

UPDATE cdst.ente SET codice_ufficio = 'VRBUDG' WHERE id_ente='697';
UPDATE cdst.ente SET codice_ufficio = 'QWEABA' WHERE id_ente='701';

UPDATE cdst.ente SET codice_ufficio = 'ufficio_AP_mobilita' WHERE id_ente='700';
UPDATE cdst.ente SET codice_ufficio = 'ufficio_AP_servizio_gestione_Sic_AP' WHERE id_ente='703';

UPDATE cdst.ente SET codice_ufficio = 'IOJ7G4' WHERE id_ente='699';
UPDATE cdst.ente SET codice_ufficio = 'DC5GFB' WHERE id_ente='705';

UPDATE cdst.ente SET codice_ufficio = 'VOMJCG' WHERE id_ente='696';
UPDATE cdst.ente SET codice_ufficio = 'NNDJCC' WHERE id_ente='702';

UPDATE cdst.ente SET codice_ufficio = '0ZW96W ' WHERE id_ente='698';
UPDATE cdst.ente SET codice_ufficio = 'CWV3FK' WHERE id_ente='704';

INSERT INTO cdst.ente(
	codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, codice_ufficio)
	VALUES ('80008630420', 'P.F. Tutela del Territorio di Ancona e Gestione del patrimonio', 'false', 'regione.marche.geniocivile.an@emarche.it', 'false', 'r_marche', '11', '042', '042002', 'HZRSSP');
INSERT INTO cdst.ente(
	codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, codice_ufficio)
	VALUES ('80008630420', 'P.F. Tutela del territorio di Ascoli Piceno', 'false', 'regione.marche.geniocivile.ap@emarche.it', 'false', 'r_marche', '11', '042', '042002', 'M9MI1Y');
INSERT INTO cdst.ente(
	codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, codice_ufficio)
	VALUES ('80008630420', 'P.F. Tutela del territorio di Fermo', 'false', 'regione.marche.geniocivile.fm@emarche.it', 'false', 'r_marche', '11', '042', '042002', 'GBEMB4');
INSERT INTO cdst.ente(
	codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, codice_ufficio)
	VALUES ('80008630420', 'P.F. Tutela del territorio di Macerata', 'false', 'regione.marche.geniocivile.mc@emarche.it', 'false', 'r_marche', '11', '042', '042002', 'I1KMIV');
INSERT INTO cdst.ente(
	codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, codice_ufficio)
	VALUES ('80008630420', 'P.F. Tutela del territorio di Pesaro-Urbino', 'false', 'regione.marche.geniocivile.pu@emarche.it', 'false', 'r_marche', '11', '042', '042002', 'WLHGL8');
	
UPDATE cdst.ente SET codice_fiscale_ente = '02566100414' WHERE id_ente='707';
UPDATE cdst.ente SET codice_fiscale_ente = '02561910411' WHERE id_ente='708';

UPDATE cdst.partecipante SET fk_ente = '713' WHERE fk_ente = '67';
DELETE FROM cdst.ente WHERE id_ente = '67';

UPDATE cdst.ente SET codice_fiscale_ente = '02565260417' WHERE id_ente='709';

UPDATE cdst.ente SET codice_fiscale_ente = '02227590441' WHERE id_ente='714';

UPDATE cdst.ente SET codice_fiscale_ente = '01874330432' WHERE id_ente='711';

UPDATE cdst.partecipante SET fk_ente = '710' WHERE fk_ente = '66';
DELETE FROM cdst.ente WHERE id_ente = '66';

UPDATE cdst.partecipante SET fk_ente = '712' WHERE fk_ente = '674';
DELETE FROM cdst.ente WHERE id_ente = '674';

UPDATE cdst.ente SET codice_fiscale_ente = '02228180440' WHERE id_ente='706';
