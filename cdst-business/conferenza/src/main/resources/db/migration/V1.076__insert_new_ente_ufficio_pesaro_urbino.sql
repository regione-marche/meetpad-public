DO 
$do$ 
DECLARE 
	tipo RECORD;
	BEGIN
		IF EXISTS (SELECT * FROM cdst.ente WHERE ente.descrizione_ente='Provincia di Pesaro e Urbino - Amministrativo - Ambiente - Trasporto privato') THEN
			UPDATE cdst.ente SET codice_ufficio='RO21KK' WHERE descrizione_ente='Provincia di Pesaro e Urbino - Amministrativo - Ambiente - Trasporto privato';
		ELSE
			INSERT INTO cdst.ente(codice_fiscale_ente, descrizione_ente, flag_amm_procedente, pec_ente, flag_amministrazione_principale, codice_ufficio)
			VALUES ('00212000418', 'Provincia di Pesaro e Urbino - Amministrativo - Ambiente - Trasporto privato', false, 'provincia.pesarourbino@legalmail.it', false, 'RO21KK');
			for tipo in (select * from cdst.tipologia_conferenza tc)
			loop
				IF(tipo.codice in ('4', '5'))THEN
					INSERT INTO cdst.rubrica_partecipanti(fk_tipologia_conferenza, fk_ente, fk_ruolo_partecipante)
					VALUES (tipo.codice, (select id_ente from cdst.ente where codice_fiscale_ente='00212000418' and codice_ufficio='RO21KK'), '3') ON CONFLICT DO NOTHING;
				END IF;
			end loop;
		END IF;
	for tipo in (select * from cdst.tipologia_conferenza tc)
	loop
		IF(tipo.codice in ('4', '5'))THEN
			INSERT INTO cdst.rubrica_partecipante_competenza_autorizzativa(id_rubrica_partecipanti, codice_competenza_autorizzativa)
			VALUES ((select id_rubrica_partecipanti from cdst.rubrica_partecipanti where fk_tipologia_conferenza=tipo.codice and fk_ente=(select id_ente from cdst.ente where codice_fiscale_ente='00212000418' and codice_ufficio='RO21KK')), '9') ON CONFLICT DO NOTHING;
		END IF;
	end loop;
end
$do$;