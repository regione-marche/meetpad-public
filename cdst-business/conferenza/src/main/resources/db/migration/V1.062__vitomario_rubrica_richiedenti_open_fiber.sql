DO
$do$
BEGIN
if NOT EXISTS(SELECT codice_fiscale FROM cdst.persona WHERE codice_fiscale = 'MGLVMR62D30C283E') THEN
	INSERT INTO cdst.persona(
	codice_fiscale, cognome, email, nome)
	VALUES ('MGLVMR62D30C283E', 'Magliaro', 'vitomario.magliaro@openfiber.it', 'Vito Mario');
	END IF;
if NOT EXISTS(SELECT * FROM cdst.rubrica_richiedenti WHERE fk_tipologia_conferenza='4' AND fk_persona=
	(select id_persona from cdst.persona where codice_fiscale='MGLVMR62D30C283E') AND fk_rubrica_imprese=
	(select id_rubrica_imprese from cdst.rubrica_imprese where fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966') and fk_tipologia_conferenza='4'))
	THEN
	INSERT INTO cdst.rubrica_richiedenti(
	fk_tipologia_conferenza, fk_persona, principale, fk_rubrica_imprese)
	VALUES ('4', (select id_persona from cdst.persona where codice_fiscale='MGLVMR62D30C283E'), true, 
	(select id_rubrica_imprese from cdst.rubrica_imprese where fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966') and fk_tipologia_conferenza='4'));
	END IF;
if NOT EXISTS(SELECT * FROM cdst.rubrica_richiedenti WHERE fk_tipologia_conferenza='5' AND fk_persona=
	(select id_persona from cdst.persona where codice_fiscale='MGLVMR62D30C283E') AND fk_rubrica_imprese=
	(select id_rubrica_imprese from cdst.rubrica_imprese where fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966') and fk_tipologia_conferenza='5'))
	THEN
	INSERT INTO cdst.rubrica_richiedenti(
	fk_tipologia_conferenza, fk_persona, principale, fk_rubrica_imprese)
	VALUES ('5', (select id_persona from cdst.persona where codice_fiscale='MGLVMR62D30C283E'), true, 
	(select id_rubrica_imprese from cdst.rubrica_imprese where fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966') and fk_tipologia_conferenza='5'));
	END IF;	 
END
$do$;