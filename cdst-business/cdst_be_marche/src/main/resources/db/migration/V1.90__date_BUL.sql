DO
$do$
BEGIN

IF EXISTS (SELECT 1 from cdst.tipologia_conferenza where codice = '4') THEN	
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore) 
	VALUES ('17', '4', '1', 'true', 'istanza.dataTermine.null', 'definition.instance.expirationDate') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('18', '4', '2', 'false') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore) 
	VALUES ('19', '4', '3', 'true', 'istanza.termineDataIntegrazione.null', 'definition.instance.endIntegrationDate') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore) 
	VALUES ('20', '4', '4', 'true', 'istanza.dataPrimaSessione.null', 'definition.instance.firstSessionDate') ON CONFLICT DO NOTHING;
END IF;

END
$do$;