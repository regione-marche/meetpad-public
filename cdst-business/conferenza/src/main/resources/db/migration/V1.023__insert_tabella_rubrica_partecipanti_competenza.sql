DO
$do$
DECLARE 
 tipo RECORD;
 BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza tc
	)
 loop
 	IF (tipo.codice in ('4', '5')) THEN

INSERT INTO cdst.rubrica_partecipanti(fk_tipologia_conferenza, fk_ente, fk_ruolo_partecipante)
	VALUES (tipo.codice, (SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio='HZRSSP'), '5');
INSERT INTO cdst.rubrica_partecipanti(fk_tipologia_conferenza, fk_ente, fk_ruolo_partecipante)
	VALUES (tipo.codice, (SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio='M9MI1Y'), '5');
INSERT INTO cdst.rubrica_partecipanti(fk_tipologia_conferenza, fk_ente, fk_ruolo_partecipante)
	VALUES (tipo.codice, (SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio='GBEMB4'), '5');
INSERT INTO cdst.rubrica_partecipanti(fk_tipologia_conferenza, fk_ente, fk_ruolo_partecipante)
	VALUES (tipo.codice, (SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio='I1KMIV'), '5');
INSERT INTO cdst.rubrica_partecipanti(fk_tipologia_conferenza, fk_ente, fk_ruolo_partecipante)
	VALUES (tipo.codice, (SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio='WLHGL8'), '5');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='93030270669')), '4');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='93030270669')), '5');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='93030270669')), '6');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='93030270669')), '7');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='97532760580')), '3');
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80208450587')), '1');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='92038990344')), '2');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='97077330583')), '2');
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='00369930425' AND codice_ufficio = 'VRBUDG')), '8');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='00369930425' AND codice_ufficio = 'QWEABA')), '9');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01116550441' AND codice_ufficio = 'ufficio_AP_mobilita')), '9');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01116550441' AND codice_ufficio = 'ufficio_AP_servizio_gestione_Sic_AP')), '8');
	
UPDATE cdst.ente SET codice_ufficio = '0ZW96W' WHERE codice_fiscale_ente = '90038780442' AND descrizione_ente = 'Provincia di Fermo - Servizio Viabilità e Infrastrutture';

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='90038780442' AND codice_ufficio = '0ZW96W')), '9');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='90038780442' AND codice_ufficio = 'CWV3FK')), '8');
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80001250432' AND codice_ufficio = 'IOJ7G4')), '9');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80001250432' AND codice_ufficio = 'DC5GFB')), '8');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='00212000418' AND codice_ufficio = 'VOMJCG')), '9');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='00212000418' AND codice_ufficio = 'NNDJCC')), '9');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'GQJG6M')), '18');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = '0IVWZD')), '10');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'B3RXC7')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'HZRSSP')), '11');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'HZRSSP')), '12');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'HZRSSP')), '13');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'HZRSSP')), '14');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'HZRSSP')), '15');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'GBEMB4')), '11');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'GBEMB4')), '12');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'GBEMB4')), '13');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'GBEMB4')), '14');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'GBEMB4')), '15');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'M9MI1Y')), '11');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'M9MI1Y')), '12');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'M9MI1Y')), '13');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'M9MI1Y')), '14');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'M9MI1Y')), '15');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'I1KMIV')), '11');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'I1KMIV')), '12');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'I1KMIV')), '13');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'I1KMIV')), '14');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'WLHGL8')), '11');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'WLHGL8')), '12');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'WLHGL8')), '13');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80008630420' AND codice_ufficio = 'WLHGL8')), '14');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01585570581')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01585570581')), '17');

UPDATE cdst.partecipante SET fk_ente = '708' WHERE fk_ente = '668';
DELETE FROM cdst.ente WHERE id_ente = '668';

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02561910411')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02561910411')), '17');
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='83012360430')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='83012360430')), '17');

UPDATE cdst.partecipante SET fk_ente = '709' WHERE fk_ente = '670';
DELETE FROM cdst.ente WHERE id_ente = '670';
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02565260417')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02565260417')), '17');
	
UPDATE cdst.partecipante SET fk_ente = '714' WHERE fk_ente = '675';
DELETE FROM cdst.ente WHERE id_ente = '675';

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02227590441')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02227590441')), '17');

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='81002870426')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='81002870426')), '17');
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01874730433')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01874730433')), '17');
	
UPDATE cdst.partecipante SET fk_ente = '707' WHERE fk_ente = '671';
DELETE FROM cdst.ente WHERE id_ente = '671';

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02566100414')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02566100414')), '17');
	
UPDATE cdst.partecipante SET fk_ente = '711' WHERE fk_ente = '669';
DELETE FROM cdst.ente WHERE id_ente = '669';

INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01874330432')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='01874330432')), '17');

UPDATE cdst.partecipante SET fk_ente = '706' WHERE fk_ente = '673';
DELETE FROM cdst.ente WHERE id_ente = '673';
	
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02228180440')), '16');
INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
	VALUES ((SELECT id_rubrica_partecipanti FROM cdst.rubrica_partecipanti WHERE fk_tipologia_conferenza=tipo.codice AND fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='02228180440')), '17');

END IF;
end loop;
end
 $do$;