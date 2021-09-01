DO 
$do$ 
DECLARE 
	tipo RECORD;
	BEGIN
		UPDATE cdst.tipologia_conferenza SET descrizione='tipologiaConferenza.decisoriaSimultanea' WHERE codice='4';
		UPDATE cdst.tipologia_conferenza SET descrizione='tipologiaConferenza.istruttoriaSimultanea' WHERE codice='5';
		
	for tipo in (select * from cdst.tipologia_conferenza tc)
	loop
		INSERT INTO cdst.tipologia_conferenza_specializzazione(
		codice, descrizione, fk_tipologia_conferenza)
		VALUES (tipo.codice, 'tipologiaConferenzaSpec'||'.'||split_part(tipo.descrizione, '.', 2), tipo.codice);
	end loop;
end
$do$;