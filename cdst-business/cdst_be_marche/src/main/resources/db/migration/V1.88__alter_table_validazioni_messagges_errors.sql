ALTER TABLE cdst.evento_calendario_data_obbligatoria ADD COLUMN messaggio_errore character varying(255);
ALTER TABLE cdst.evento_calendario_data_obbligatoria ADD COLUMN campo_errore character varying(255);

UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataTermine.null', campo_errore='definition.instance.expirationDate'
	WHERE id_evento_calendario_data_obbligatoria='1';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.termineDataPareri.null', campo_errore='definition.instance.endOpinionDate'
	WHERE id_evento_calendario_data_obbligatoria='2';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.termineDataIntegrazione.null', campo_errore='definition.instance.endIntegrationDate'
	WHERE id_evento_calendario_data_obbligatoria='3';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataPrimaSessione.null', campo_errore='definition.instance.firstSessionDate'
	WHERE id_evento_calendario_data_obbligatoria='4';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataTermine.null', campo_errore='definition.instance.expirationDate'
	WHERE id_evento_calendario_data_obbligatoria='5';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.termineDataIntegrazione.null', campo_errore='definition.instance.endIntegrationDate'
	WHERE id_evento_calendario_data_obbligatoria='7';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataPrimaSessione.null', campo_errore='definition.instance.firstSessionDate'
	WHERE id_evento_calendario_data_obbligatoria='8';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataTermine.null', campo_errore='definition.instance.expirationDate'
	WHERE id_evento_calendario_data_obbligatoria='9';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.termineDataIntegrazione.null', campo_errore='definition.instance.endIntegrationDate'
	WHERE id_evento_calendario_data_obbligatoria='11';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataPrimaSessione.null', campo_errore='definition.instance.firstSessionDate'
	WHERE id_evento_calendario_data_obbligatoria='12';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataTermine.null', campo_errore='definition.instance.expirationDate'
	WHERE id_evento_calendario_data_obbligatoria='13';
UPDATE cdst.evento_calendario_data_obbligatoria
	SET messaggio_errore='istanza.dataPrimaSessione.null', campo_errore='definition.instance.firstSessionDate'
	WHERE id_evento_calendario_data_obbligatoria='16';