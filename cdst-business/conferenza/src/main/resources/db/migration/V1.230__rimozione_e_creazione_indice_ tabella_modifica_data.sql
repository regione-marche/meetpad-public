ALTER TABLE cdst.modifica_data DROP CONSTRAINT modifica_data_fk_tipo_data_fk_conferenza_key;
ALTER TABLE cdst.modifica_data ADD CONSTRAINT modifica_data_fk_tipo_data_fk_conferenza_ordine_key UNIQUE (fk_tipo_data, fk_conferenza, ordine)









