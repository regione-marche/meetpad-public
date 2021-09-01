DO
$do$
DECLARE 
 tipo RECORD;
 categoria RECORD;
BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza tc
	)
 loop
	for categoria in (select *
	from cdst.categoria_documento cd
	)
	loop
		INSERT INTO cdst.categoria_documento_tipologia_conferenza(codice_categoria_documento, codice_tipologia_conferenza)
		VALUES (categoria.codice, tipo.codice) ON CONFLICT DO NOTHING;
	end loop;
 end loop;
 end
 $do$;