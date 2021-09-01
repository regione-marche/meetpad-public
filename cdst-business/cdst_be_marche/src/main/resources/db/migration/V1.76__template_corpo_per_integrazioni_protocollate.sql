DO
$do$
DECLARE 
 tipo RECORD;
BEGIN
 for tipo in (
 	(select *
	from cdst.tipologia_conferenza tc 
	 where not exists 
	 	(select 1 from cdst.template where fk_tipologia_conferenza = tc.codice and fk_tipo_evento = '13')
	)
 )
 loop
   INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_corpo', tipo.codice, '13') ON CONFLICT DO NOTHING;
 end loop;
 end
 $do$;