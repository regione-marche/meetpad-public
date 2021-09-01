ALTER TABLE cdst.tipologia_conferenza_data_definizione ADD COLUMN ordine integer;

UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=1 WHERE fk_tipologia_conferenza = '1' and campo_data_calcolato = 'endIntegrationDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=2 WHERE fk_tipologia_conferenza = '1' and campo_data_calcolato = 'endOpinionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=3 WHERE fk_tipologia_conferenza = '1' and campo_data_calcolato = 'firstSessionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=4 WHERE fk_tipologia_conferenza = '1' and campo_data_calcolato = 'expirationDate';

UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=1 WHERE fk_tipologia_conferenza = '2' and campo_data_calcolato = 'endIntegrationDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=2 WHERE fk_tipologia_conferenza = '2' and campo_data_calcolato = 'endOpinionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=3 WHERE fk_tipologia_conferenza = '2' and campo_data_calcolato = 'firstSessionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=4 WHERE fk_tipologia_conferenza = '2' and campo_data_calcolato = 'expirationDate';

UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=1 WHERE fk_tipologia_conferenza = '3' and campo_data_calcolato = 'endIntegrationDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=2 WHERE fk_tipologia_conferenza = '3' and campo_data_calcolato = 'endOpinionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=3 WHERE fk_tipologia_conferenza = '3' and campo_data_calcolato = 'firstSessionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=4 WHERE fk_tipologia_conferenza = '3' and campo_data_calcolato = 'expirationDate';

UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=1 WHERE fk_tipologia_conferenza = '4' and campo_data_calcolato = 'endIntegrationDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=2 WHERE fk_tipologia_conferenza = '4' and campo_data_calcolato = 'endOpinionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=3 WHERE fk_tipologia_conferenza = '4' and campo_data_calcolato = 'firstSessionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=4 WHERE fk_tipologia_conferenza = '4' and campo_data_calcolato = 'expirationDate';

UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=1 WHERE fk_tipologia_conferenza = '5' and campo_data_calcolato = 'endIntegrationDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=2 WHERE fk_tipologia_conferenza = '5' and campo_data_calcolato = 'endOpinionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=3 WHERE fk_tipologia_conferenza = '5' and campo_data_calcolato = 'firstSessionDate';
UPDATE cdst.tipologia_conferenza_data_definizione SET ordine=4 WHERE fk_tipologia_conferenza = '5' and campo_data_calcolato = 'expirationDate';