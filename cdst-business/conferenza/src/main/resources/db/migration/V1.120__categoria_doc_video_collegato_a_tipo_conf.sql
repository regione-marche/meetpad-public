DO
$do$
DECLARE 
 tipo RECORD;
BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza_specializzazione tc
	)
 loop
	INSERT INTO cdst.categoria_documento_tipologia_conferenza(
	codice_categoria_documento, codice_tipologia_conferenza)
	VALUES ('16', tipo.codice);
 end loop;
 end
 $do$;