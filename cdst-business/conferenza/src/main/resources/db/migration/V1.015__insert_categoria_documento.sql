INSERT INTO cdst.categoria_documento(
	codice, descrizione)
	VALUES ('6', 'categoriaDocumento.comune');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione)
	VALUES ('7', 'categoriaDocumento.provincia');
	
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
		IF (tipo.codice in ('4', '5') AND categoria.codice in ('6', '7')) THEN
		INSERT INTO cdst.categoria_documento_tipologia_conferenza(codice_categoria_documento, codice_tipologia_conferenza)
		VALUES (categoria.codice, tipo.codice) ON CONFLICT DO NOTHING;
		END IF;
		IF (tipo.codice in ('4', '5') AND categoria.codice in ('1', '5')) THEN
		DELETE FROM cdst.categoria_documento_tipologia_conferenza 
		WHERE (codice_categoria_documento = categoria.codice AND codice_tipologia_conferenza = tipo.codice);
		END IF;										
	end loop;
 end loop;
 end
 $do$;