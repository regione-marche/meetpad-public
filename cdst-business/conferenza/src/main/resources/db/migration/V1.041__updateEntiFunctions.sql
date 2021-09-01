CREATE OR REPLACE FUNCTION cdst.updateenti(
	)
    RETURNS text
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$

declare 
 enteCsv RECORD;
BEGIN
 for enteCsv in (select *
	from cdst.ente_appoggio_csv 
	)
 loop
	if NOT EXISTS(SELECT codice_ipa FROM cdst.ente WHERE codice_ipa = enteCsv.cod_amm AND codice_ufficio isNull) THEN
		CASE
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike enteCsv.comune) > 1 THEN
				INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES (enteCsv.cf, enteCsv.des_amm, 'false', enteCsv.mail1, 'false', enteCsv.cod_amm, (SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione),
					(SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia), (SELECT codice FROM cdst.comune WHERE descrizione iLike enteCsv.comune AND fk_provincia = (SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia)),
					(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), (SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm ), enteCsv.comune);
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike enteCsv.comune) = 1 THEN
				INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES (enteCsv.cf, enteCsv.des_amm, 'false', enteCsv.mail1, 'false', enteCsv.cod_amm, (SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione),
					(SELECT fk_provincia FROM cdst.comune WHERE descrizione iLike enteCsv.comune), (SELECT codice FROM cdst.comune WHERE descrizione iLike enteCsv.comune),
					(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), (SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm ), enteCsv.comune);
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))) > 1 THEN
				INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES (enteCsv.cf, enteCsv.des_amm, 'false', enteCsv.mail1, 'false', enteCsv.cod_amm, (SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione),
					(SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia), (SELECT codice FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune)) AND fk_provincia = (SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia)),
					(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), (SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm ), enteCsv.comune);
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))) = 1 THEN
				INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES (enteCsv.cf, enteCsv.des_amm, 'false', enteCsv.mail1, 'false', enteCsv.cod_amm, (SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione),
					(SELECT fk_provincia FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))), (SELECT codice FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))),
					(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), (SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm ), enteCsv.comune);
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike enteCsv.comune) = 0 AND (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))) = 0 THEN
				INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, 
					codice_ipa, fk_regione, fk_provincia, fk_comune, fk_tipologia_istat, fk_tipologia_amministrativa, descrizione_comune)
					VALUES (enteCsv.cf, enteCsv.des_amm, 'false', enteCsv.mail1, 'false', enteCsv.cod_amm, (SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione),
					(SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia), (SELECT codice FROM cdst.comune WHERE descrizione iLike enteCsv.comune),
					(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), (SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm ), enteCsv.comune);
			END CASE;
	else
		CASE																										 
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike enteCsv.comune) > 1 THEN
				UPDATE cdst.ente SET codice_fiscale_ente=enteCsv.cf, descrizione_ente=enteCsv.des_amm, pec_ente=enteCsv.mail1,	
					fk_regione=(SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione), fk_provincia=COALESCE((SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia), fk_provincia), 
					fk_comune=COALESCE((SELECT codice FROM cdst.comune WHERE descrizione iLike enteCsv.comune AND fk_provincia = (SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia)), fk_comune), 
					fk_tipologia_istat=(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), 
					fk_tipologia_amministrativa=(SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm),
					descrizione_comune=enteCsv.comune
					WHERE codice_ipa = entecsv.cod_amm AND codice_ufficio isnull;
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike enteCsv.comune) = 1 THEN
				UPDATE cdst.ente SET codice_fiscale_ente=enteCsv.cf, descrizione_ente=enteCsv.des_amm, pec_ente=enteCsv.mail1,	
					fk_regione=(SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione), fk_provincia=(SELECT fk_provincia FROM cdst.comune WHERE descrizione iLike enteCsv.comune), 
					fk_comune=(SELECT codice FROM cdst.comune WHERE descrizione iLike enteCsv.comune), 
					fk_tipologia_istat=(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), 
					fk_tipologia_amministrativa=(SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm),
					descrizione_comune=enteCsv.comune
					WHERE codice_ipa = entecsv.cod_amm AND codice_ufficio isnull;
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))) > 1 THEN
				UPDATE cdst.ente SET codice_fiscale_ente=enteCsv.cf, descrizione_ente=enteCsv.des_amm, pec_ente=enteCsv.mail1,	
					fk_regione=(SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione), fk_provincia=COALESCE((SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia), fk_provincia), 
					fk_comune=COALESCE((SELECT codice FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune)) AND fk_provincia = (SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia)), fk_comune), 
					fk_tipologia_istat=(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), 
					fk_tipologia_amministrativa=(SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm),
					descrizione_comune=enteCsv.comune
					WHERE codice_ipa = entecsv.cod_amm AND codice_ufficio isnull;
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))) = 1 THEN
				UPDATE cdst.ente SET codice_fiscale_ente=enteCsv.cf, descrizione_ente=enteCsv.des_amm, pec_ente=enteCsv.mail1,	
					fk_regione=(SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione), fk_provincia=COALESCE((SELECT fk_provincia FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))), fk_provincia), 
					fk_comune=COALESCE((SELECT codice FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))), fk_comune), 
					fk_tipologia_istat=(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), 
					fk_tipologia_amministrativa=(SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm),
					descrizione_comune=enteCsv.comune
					WHERE codice_ipa = entecsv.cod_amm AND codice_ufficio isnull;
			WHEN (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike enteCsv.comune) = 0 AND (SELECT COUNT(*) FROM cdst.comune WHERE descrizione iLike SUBSTRING(enteCsv.comune from 0 for LENGTH(enteCsv.comune))) = 0 THEN 
				UPDATE cdst.ente SET codice_fiscale_ente=enteCsv.cf, descrizione_ente=enteCsv.des_amm, pec_ente=enteCsv.mail1,	
					fk_regione=(SELECT codice FROM cdst.regione WHERE descrizione = enteCsv.regione), fk_provincia=COALESCE((SELECT codice FROM cdst.provincia WHERE sigla = enteCsv.provincia), fk_provincia), 
					fk_comune=COALESCE((SELECT codice FROM cdst.comune WHERE descrizione iLike enteCsv.comune), fk_comune), 
					fk_tipologia_istat=(SELECT codice FROM cdst.ente_tipologia_istat WHERE descrizione = enteCsv.tipologia_istat), 
					fk_tipologia_amministrativa=(SELECT codice FROM cdst.ente_tipologia_amministrativa WHERE descrizione = enteCsv.tipologia_amm),
					descrizione_comune=enteCsv.comune
					WHERE codice_ipa = entecsv.cod_amm AND codice_ufficio isnull;
		END CASE;
    end if;
    end loop;
    RETURN 'update complete';
END;

$BODY$;