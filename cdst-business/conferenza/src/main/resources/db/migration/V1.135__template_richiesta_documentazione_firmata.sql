DO
$do$
DECLARE 
 tipo RECORD;
BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza_specializzazione tc
	)
 loop
	INSERT INTO cdst.template(
	nome_template, fk_tipo_evento, fk_tipologia_conferenza_specializzazione)
	VALUES ('template_richiesta_documentazione_firmata', '25', tipo.codice);
 end loop;
 end
 $do$;