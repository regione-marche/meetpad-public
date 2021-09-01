CREATE TABLE cdst.modello
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    fk_tipologia_conferenza_specializzazione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT modello_pkey PRIMARY KEY (codice),
    CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione)
        REFERENCES cdst.tipologia_conferenza_specializzazione (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO cdst.modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) VALUES ('1', 'bozza_verbale', '1');
INSERT INTO cdst.modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) VALUES ('2', 'bozza_verbale', '2');
INSERT INTO cdst.modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) VALUES ('3', 'bozza_verbale', '3');
INSERT INTO cdst.modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) VALUES ('4', 'bozza_verbale', '4');
INSERT INTO cdst.modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) VALUES ('5', 'bozza_verbale', '5');
