ALTER TABLE cdst.tipologia_conferenza_specializzazione ADD COLUMN flag_autoaccreditamento boolean;
UPDATE cdst.tipologia_conferenza_specializzazione SET flag_autoaccreditamento=false;

ALTER TABLE cdst.tipologia_conferenza_specializzazione ADD COLUMN fk_azione character varying(255);
ALTER TABLE cdst.tipologia_conferenza_specializzazione ADD CONSTRAINT fk_azione FOREIGN KEY (fk_azione)
        REFERENCES cdst.azione (codice);