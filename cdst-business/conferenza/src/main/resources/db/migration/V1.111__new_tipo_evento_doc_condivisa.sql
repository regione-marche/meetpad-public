INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('14', 'Documentazione Condivisa');

INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
	VALUES ('24', 'Inserimento Documentazione Condivisa', '14', 'DOCUMENTI_CONDIVISI', true, true);
	
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
	VALUES ('template_richiesta_documentazione_condivisa', '24', tipo.codice);
 end loop;
 end
 $do$;