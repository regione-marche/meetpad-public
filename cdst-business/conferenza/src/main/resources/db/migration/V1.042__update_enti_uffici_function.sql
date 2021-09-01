CREATE OR REPLACE FUNCTION cdst.updateentiUffici(
	)
    RETURNS text
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$
	declare 
 ufficiCsv RECORD;
 enteBase RECORD;
BEGIN
 for ufficiCsv in (select *
	from cdst.ente_uffici_appoggio_csv 
	)
 loop
	for enteBase in (SELECT * FROM cdst.ente WHERE codice_ipa = ufficiCsv.cod_amm AND codice_ufficio isNull)
	loop
	if NOT EXISTS(SELECT codice_ipa FROM cdst.ente WHERE codice_ipa = ufficiCsv.cod_amm AND codice_ufficio=ufficiCsv.cod_uni_ou) THEN 
		INSERT INTO cdst.ente(
		codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, codice_ufficio, descrizione_comune, codice_ou, codice_ou_padre)
		VALUES (enteBase.codice_fiscale_ente, ufficiCsv.des_ou, 'false', ufficiCsv.mail1, 'false', ufficiCsv.cod_amm, enteBase.fk_regione, 
		enteBase.fk_provincia, enteBase.fk_comune, enteBase.fk_tipologia_istat, enteBase.fk_tipologia_amministrativa, ufficiCsv.cod_uni_ou, enteBase.descrizione_comune, ufficiCsv.cod_ou, ufficiCsv.cod_ou_padre);
	else 
		UPDATE cdst.ente
		SET codice_fiscale_ente=enteBase.codice_fiscale_ente, descrizione_ente=ufficiCsv.des_ou, flag_amm_procedente='false', 
		pec_ente=ufficiCsv.mail1, flag_amministrazione_principale='false', fk_regione=enteBase.fk_regione, fk_provincia=enteBase.fk_provincia, fk_comune=enteBase.fk_comune, 
		fk_tipologia_istat=enteBase.fk_tipologia_istat, fk_tipologia_amministrativa=enteBase.fk_tipologia_amministrativa, descrizione_comune=enteBase.descrizione_comune,
		codice_ou=ufficiCsv.cod_ou, codice_ou_padre=ufficiCsv.cod_ou_padre
		WHERE codice_ipa = ufficiCsv.cod_amm AND codice_ufficio=ufficiCsv.cod_uni_ou;
    end if;
	end loop;
    end loop;
    RETURN 'update complete';
END;

$BODY$;