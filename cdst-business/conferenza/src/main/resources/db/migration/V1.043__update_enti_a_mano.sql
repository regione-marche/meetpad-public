UPDATE cdst.comune
	SET descrizione='Reggio Emilia'
	WHERE codice='035033';
	
UPDATE cdst.comune
	SET descrizione='Reggio Calabria'
	WHERE codice='080063';
	
UPDATE cdst.ente
	SET fk_regione='12', fk_provincia='058', fk_comune='058091', descrizione_comune='Roma' WHERE codice_fiscale_ente='07516911000';
	
UPDATE cdst.ente
	SET fk_regione='12', fk_provincia='058', fk_comune='058091', descrizione_comune='Roma' WHERE codice_fiscale_ente='01585570581';


INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES('80003270917', 'Comune di San Teodoro', 'false', 'protocollo@pec.comunesanteodoro.it', 'false', 
					'c_i32k', '20', '090', '090092', '27', '2', 'San Teodoro');	

INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES('82004710909', 'Comune di Trinita'' D''Agultu e Vignola', 'false', 'protocollo.trinitadagultu@legalmail.it', 'false', 
					'c_l428', '20', '090', '090074', '27', '2', 'Trinita'' d''Agultu e Vignola');
					
INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES('81000550905', 'Comune di Ala Dei Sardi', 'false', 'protocollo.aladeisardi@legalmail.it', 'false', 
					'c_wfs6', '20', '090', '090002', '27', '2', 'Ala'' dei Sardi');
					
INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES('93013410910', 'ISTITUTO COMPRENSIVO - SAN TEODORO', 'false', 'ssic854009@pec.istruzione.it', 'false', 
					'istsc_nuic84600e', '20', '090', '090002', '6', '2', 'San Teodoro');
					
INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES('93049130912', 'Unione Dei Comuni Riviera di Gallura', 'false', 'protocollo@pec.unionerivieradigallura.gov.it', 'false', 
					'udcrg', '20', '090', '090002', '27', '2', 'San Teodoro');