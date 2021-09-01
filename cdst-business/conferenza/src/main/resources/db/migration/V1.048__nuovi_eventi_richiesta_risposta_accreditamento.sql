INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('12', 'Accreditamento');
	
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, flag_invio_email, flag_allegato)
	VALUES ('17', 'Richiesta Accreditamento', '12', 'true', 'false');
	
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, flag_invio_email, flag_allegato)
	VALUES ('18', 'Conferma Accreditamento', '12', 'true', 'false');
	
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, flag_invio_email, flag_allegato)
	VALUES ('19', 'Rifiuta Accreditamento', '12', 'true', 'false');

DO
$do$
DECLARE 
 tipo RECORD;
 BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza tc
	)
	loop
		INSERT INTO cdst.template(
			nome_template, fk_tipologia_conferenza, fk_tipo_evento)
			VALUES ('template_richiesta_accreditamento', tipo.codice, '17');
		
		INSERT INTO cdst.template(
			nome_template, fk_tipologia_conferenza, fk_tipo_evento)
			VALUES ('template_conferma_accreditamento', tipo.codice, '18');
		
		INSERT INTO cdst.template(
			nome_template, fk_tipologia_conferenza, fk_tipo_evento)
			VALUES ('template_rifiuta_accreditamento', tipo.codice, '19');
	end loop;
 end
 $do$;