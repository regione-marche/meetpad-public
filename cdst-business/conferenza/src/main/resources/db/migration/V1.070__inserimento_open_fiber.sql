DO
$do$
BEGIN
DELETE FROM cdst.rubrica_richiedenti;
DELETE FROM cdst.rubrica_imprese;
DELETE FROM cdst.impresa;	

INSERT INTO cdst.impresa(
		denominazione, codice_fiscale, partita_iva, indirizzo, fk_forma_giuridica, fk_regione, fk_provincia, fk_comune)
		VALUES ('Open Fiber S.p.A', '', '09320630966', 'Viale Certosa 2', '5', '03', '015', '015146');
INSERT INTO cdst.rubrica_imprese(
		fk_tipologia_conferenza, fk_impresa, step_modificabili, lista_step_modificabili)
		VALUES ('4', (select id_impresa from cdst.impresa where partita_iva='09320630966'), true, '2,3');
INSERT INTO cdst.rubrica_imprese(
		fk_tipologia_conferenza, fk_impresa, step_modificabili, lista_step_modificabili)
		VALUES ('5', (select id_impresa from cdst.impresa where partita_iva='09320630966'), true, '2,3');
INSERT INTO cdst.rubrica_richiedenti(
	fk_tipologia_conferenza, fk_persona, principale, fk_rubrica_imprese)
	VALUES ('4', (select id_persona from cdst.persona where codice_fiscale='MGLVMR62D30C283E'), true, 
	(select id_rubrica_imprese from cdst.rubrica_imprese where fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966') and fk_tipologia_conferenza='4'));
INSERT INTO cdst.rubrica_richiedenti(
	fk_tipologia_conferenza, fk_persona, principale, fk_rubrica_imprese)
	VALUES ('5', (select id_persona from cdst.persona where codice_fiscale='MGLVMR62D30C283E'), true, 
	(select id_rubrica_imprese from cdst.rubrica_imprese where fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966') and fk_tipologia_conferenza='5'));
END
$do$;