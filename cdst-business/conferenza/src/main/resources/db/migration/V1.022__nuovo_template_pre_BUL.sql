DO
$do$
DECLARE 
 tipo RECORD;
 temp RECORD;
BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza 
	)
 loop
	for temp in (select *
	from cdst.template 
	)
	loop
		IF (tipo.codice = '5' AND temp.fk_tipo_evento = '2') THEN
		UPDATE cdst.template SET nome_template = 'template_new_indizione_meetpad_preistruttoria_BUL' 
			WHERE fk_tipologia_conferenza = tipo.codice AND fk_tipo_evento = temp.fk_tipo_evento;
		END IF;						
	end loop;
 end loop;
 end
 $do$;