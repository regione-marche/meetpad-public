DO
$do$
BEGIN
if NOT EXISTS(SELECT * FROM cdst.rubrica_imprese WHERE fk_tipologia_conferenza='4' AND fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966'))
	THEN
	INSERT INTO cdst.rubrica_imprese(
		fk_tipologia_conferenza, fk_impresa, step_modificabili, lista_step_modificabili)
		VALUES ('4', (select id_impresa from cdst.impresa where partita_iva='09320630966'), true, '2,3');
	END IF;
if NOT EXISTS(SELECT * FROM cdst.rubrica_imprese WHERE fk_tipologia_conferenza='4' AND fk_impresa=(select id_impresa from cdst.impresa where partita_iva='09320630966'))
	THEN
	INSERT INTO cdst.rubrica_imprese(
		fk_tipologia_conferenza, fk_impresa, step_modificabili, lista_step_modificabili)
		VALUES ('5', (select id_impresa from cdst.impresa where partita_iva='09320630966'), true, '2,3');
	END IF;
END
$do$;