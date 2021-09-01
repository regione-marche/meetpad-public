DELETE FROM cdst.ente
	WHERE id_ente not in (select distinct fk_ente from cdst.partecipante union
			select distinct fk_ente from cdst.rubrica_partecipanti);
			
UPDATE cdst.ente SET codice_ipa='cdcoame', fk_regione='11', fk_provincia='041', fk_comune='041069', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='44';
		
UPDATE cdst.ente SET codice_ipa='c_a769', fk_regione='11', fk_provincia='042', fk_comune='042005', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2'
		WHERE id_ente='127';
		
UPDATE cdst.ente SET codice_ipa='c_c582', fk_regione='11', fk_provincia='043', fk_comune='043011', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='167';

UPDATE cdst.ente SET codice_ipa='c_d451', fk_regione='11', fk_provincia='042', fk_comune='042017', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2'
		WHERE id_ente='180';
		
UPDATE cdst.ente SET codice_ipa='c_d541', fk_regione='11', fk_provincia='041', fk_comune='041014', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='184';
		
UPDATE cdst.ente SET codice_ipa='c_d965', fk_regione='11', fk_provincia='042', fk_comune='042020', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='199';
		
UPDATE cdst.ente SET codice_ipa='c_e743', fk_regione='11', fk_provincia='041', fk_comune='041022', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='209';
		
UPDATE cdst.ente SET codice_ipa='c_f051', fk_regione='11', fk_provincia='043', fk_comune='043024', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='217';
		
UPDATE cdst.ente SET codice_ipa='c_f474', fk_regione='11', fk_provincia='041', fk_comune='041032', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='236';
		
UPDATE cdst.ente SET codice_ipa='c_f621', fk_regione='11', fk_provincia='043', fk_comune='043031', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='263';
		
UPDATE cdst.ente SET codice_ipa='c_f634', fk_regione='11', fk_provincia='042', fk_comune='042030', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='268';
		
UPDATE cdst.ente SET codice_ipa='c_f745', fk_regione='11', fk_provincia='042', fk_comune='042031', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='273';
		
UPDATE cdst.ente SET codice_ipa='c_g453', fk_regione='11', fk_provincia='041', fk_comune='041043', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='285';

UPDATE cdst.ente SET codice_ipa='c_g479', fk_regione='11', fk_provincia='041', fk_comune='041044', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='286';
		
UPDATE cdst.ente SET codice_ipa='c_g657', fk_regione='11', fk_provincia='043', fk_comune='043038', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='292';
		
UPDATE cdst.ente SET codice_ipa='c_h323', fk_regione='11', fk_provincia='043', fk_comune='043045', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='304';
		
UPDATE cdst.ente SET codice_ipa='c_h876', fk_regione='11', fk_provincia='043', fk_comune='043046', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2'
		WHERE id_ente='310';
		
UPDATE cdst.ente SET codice_ipa='c_h979', fk_regione='11', fk_provincia='042', fk_comune='042041', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='312';
		
UPDATE cdst.ente SET codice_ipa='c_i286', fk_regione='11', fk_provincia='043', fk_comune='043048', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='315';
		
UPDATE cdst.ente SET codice_ipa='c_l081', fk_regione='11', fk_provincia='041', fk_comune='041065', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='337';
		
UPDATE cdst.ente SET codice_ipa='c_l500', fk_regione='11', fk_provincia='041', fk_comune='041067', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='343';
		
UPDATE cdst.ente SET codice_ipa='c_l517', fk_regione='11', fk_provincia='043', fk_comune='043056', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='345';
		
UPDATE cdst.ente SET codice_ipa='c_m078', fk_regione='11', fk_provincia='043', fk_comune='043057', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='347';

UPDATE cdst.ente SET codice_ipa='c_m331', fk_regione='11', fk_provincia='041', fk_comune='041068', fk_tipologia_istat='27', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='349';
		
UPDATE cdst.ente SET codice_ipa='pn_msib', fk_regione='11', fk_provincia='043', fk_comune='043057', fk_tipologia_istat='20', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='641';

INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa)
	VALUES ('97904380587', 'Ministero per i beni e le attivita'' culturali', 'false', 'mbac-udcm@mailcert.beniculturali.it', 'false', 'm_bac', '12', '058', '058091', '11', '2');
	
UPDATE cdst.ente SET codice_fiscale_ente='97904380587', descrizione_ente='Ministero per i beni e le attivita'' culturali - Soprintendenza Archeologia belle arti e paesaggio delle Marche', codice_ipa='m_bac', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='11', 
	fk_tipologia_amministrativa='2', codice_ufficio='1FCAKD' 
		WHERE id_ente='683';

INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa)
	VALUES ('80208450587', 'ANAS S.p.A.', 'false', 'anas@postacert.stradeanas.it', 'false', 'as', '12', '058', '058091', '22', '4');
		
UPDATE cdst.ente SET descrizione_ente='Area Compartimentale Marche', codice_ipa='as', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='22', 
	fk_tipologia_amministrativa='4', codice_ufficio='RG3UEL' 
		WHERE id_ente='684';

INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa)
	VALUES ('80230390587', 'Ministero dello Sviluppo Economico', 'false', 'notifichepct@pec.mise.gov.it', 'false', 'm_svec', '12', '058', '058091', '11', '2');
		
UPDATE cdst.ente SET descrizione_ente='DGAT_Ispettorato territoriale Marche e Umbria', codice_ipa='m_svec', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='11', 
	fk_tipologia_amministrativa='2', codice_ufficio='G9I7VJ' 
		WHERE id_ente='688';
		
UPDATE cdst.ente SET codice_ipa='abftrm', fk_regione='12', fk_provincia='058', fk_comune='058091', fk_tipologia_istat='47', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='690';
		
UPDATE cdst.ente SET codice_ipa='adbpo', fk_regione='8', fk_provincia='034', fk_comune='034027', fk_tipologia_istat='47', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='692';
		
INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa)
	VALUES ('97532760580', 'Ministero delle Infrastrutture e dei Trasporti', 'false', 'm_inf@pec.mit.gov.it', 'false', 'm_inf', '12', '058', '058091', '11', '2');		
		
UPDATE cdst.ente SET descrizione_ente='DG per la vigilanza sulle concessionarie autostradali - UIT Roma', codice_ipa='m_inf', fk_regione='12', fk_provincia='058', fk_comune='058091', fk_tipologia_istat='11', 
	fk_tipologia_amministrativa='2', codice_ufficio='SD9HUQ' 
		WHERE id_ente='693';
		
UPDATE cdst.ente SET codice_ipa='umds', fk_regione='11', fk_provincia='044', fk_comune='044015', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='706';
		
UPDATE cdst.ente SET codice_ipa='umdm', fk_regione='11', fk_provincia='041', fk_comune='041009', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='707';

UPDATE cdst.ente SET codice_ipa='umavm', fk_regione='11', fk_provincia='041', fk_comune='041066', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='708';
		
UPDATE cdst.ente SET codice_ipa='umcn', fk_regione='11', fk_provincia='041', fk_comune='041007', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='709';
		
UPDATE cdst.ente SET codice_ipa='cm_esino', fk_regione='11', fk_provincia='042', fk_comune='042017', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='710';
		
UPDATE cdst.ente SET codice_ipa='umavp', fk_regione='11', fk_provincia='043', fk_comune='043047', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='711';
		
UPDATE cdst.ente SET codice_ipa='ummc', fk_regione='11', fk_provincia='043', fk_comune='043007', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='712';
		
UPDATE cdst.ente SET codice_ipa='cm_moaz', fk_regione='11', fk_provincia='043', fk_comune='043046', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='713';
		
UPDATE cdst.ente SET codice_ipa='umtv', fk_regione='11', fk_provincia='044', fk_comune='044001', fk_tipologia_istat='8', 
	fk_tipologia_amministrativa='2' 
		WHERE id_ente='714';
		
UPDATE cdst.ente SET fk_tipologia_istat='37', fk_tipologia_amministrativa='2' WHERE id_ente='715';
UPDATE cdst.ente SET fk_tipologia_istat='37', fk_tipologia_amministrativa='2' WHERE id_ente='716';
UPDATE cdst.ente SET fk_tipologia_istat='37', fk_tipologia_amministrativa='2' WHERE id_ente='717';	
UPDATE cdst.ente SET fk_tipologia_istat='37', fk_tipologia_amministrativa='2' WHERE id_ente='718';
UPDATE cdst.ente SET fk_tipologia_istat='37', fk_tipologia_amministrativa='2' WHERE id_ente='719';

UPDATE cdst.ente SET codice_ipa='p_PU', fk_regione='11', fk_provincia='041', fk_comune='041044', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='696';
	
UPDATE cdst.ente SET codice_ipa='p_AN', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='697';
	
UPDATE cdst.ente SET codice_ipa='p_FM', fk_regione='11', fk_provincia='109', fk_comune='109006', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='698';
	
UPDATE cdst.ente SET codice_ipa='p_mc', fk_regione='11', fk_provincia='043', fk_comune='043023', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='699';
	
UPDATE cdst.ente SET codice_ipa='p_ap', fk_regione='11', fk_provincia='044', fk_comune='044007', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='700';

UPDATE cdst.ente SET codice_ipa='p_AN', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='701';
	
UPDATE cdst.ente SET codice_ipa='p_PU', fk_regione='11', fk_provincia='041', fk_comune='041044', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='702';
	
UPDATE cdst.ente SET codice_ipa='p_ap', fk_regione='11', fk_provincia='044', fk_comune='044007', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='703';
	
UPDATE cdst.ente SET codice_ipa='p_FM', fk_regione='11', fk_provincia='109', fk_comune='109006', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='704';
	
UPDATE cdst.ente SET codice_ipa='p_mc', fk_regione='11', fk_provincia='043', fk_comune='043023', fk_tipologia_istat='7', 
	fk_tipologia_amministrativa='2' WHERE id_ente='705';
	
UPDATE cdst.ente SET codice_ipa='r_marche', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='37', 
	fk_tipologia_amministrativa='2' WHERE id_ente='685';

UPDATE cdst.ente SET codice_ipa='r_marche', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='37', 
	fk_tipologia_amministrativa='2' WHERE id_ente='686';
	
UPDATE cdst.ente SET codice_ipa='r_marche', fk_regione='11', fk_provincia='042', fk_comune='042002', fk_tipologia_istat='37', 
	fk_tipologia_amministrativa='2' WHERE id_ente='687';