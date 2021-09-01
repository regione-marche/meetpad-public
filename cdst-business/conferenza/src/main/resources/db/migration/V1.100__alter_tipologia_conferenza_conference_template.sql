ALTER TABLE cdst.tipologia_conferenza ADD COLUMN flag_orario_visibile boolean;
ALTER TABLE cdst.tipologia_conferenza ADD COLUMN flag_modalita_incontro_modificabile boolean;
ALTER TABLE cdst.tipologia_conferenza ADD COLUMN fk_modalita character varying(255);
ALTER TABLE cdst.tipologia_conferenza ADD CONSTRAINT fk_modalita FOREIGN KEY (fk_modalita) REFERENCES cdst.modalita (codice);

UPDATE cdst.tipologia_conferenza SET flag_orario_visibile=false, flag_modalita_incontro_modificabile=false, fk_modalita='1' WHERE codice = '1';
UPDATE cdst.tipologia_conferenza SET flag_orario_visibile=true, flag_modalita_incontro_modificabile=true, fk_modalita=null WHERE codice = '2';
UPDATE cdst.tipologia_conferenza SET flag_orario_visibile=true, flag_modalita_incontro_modificabile=true, fk_modalita=null WHERE codice = '3';
UPDATE cdst.tipologia_conferenza SET flag_orario_visibile=true, flag_modalita_incontro_modificabile=true, fk_modalita=null WHERE codice = '4';
UPDATE cdst.tipologia_conferenza SET flag_orario_visibile=true, flag_modalita_incontro_modificabile=true, fk_modalita=null WHERE codice = '5';