alter table cdst.tipologia_conferenza_data_definizione alter column campo_data_base DROP NOT NULL;

UPDATE cdst.tipologia_conferenza_data_definizione
	SET offset_gg_lavorativi=null, offset_gg_solari=null, campo_data_base=null
	WHERE fk_tipologia_conferenza='5';
	
UPDATE cdst.tipologia_conferenza_data_definizione
	SET offset_gg_lavorativi=null, offset_gg_solari=null, campo_data_base=null
	WHERE fk_tipologia_conferenza='3' and campo_data_calcolato = 'endOpinionDate';