CREATE TABLE cdst.tipologia_conferenza_specializzazione
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
	fk_tipologia_conferenza character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tipo_conf_specializ_pkey PRIMARY KEY (codice),
	CONSTRAINT tipo_conf_specializ_fk_tipo_conferenza FOREIGN KEY (fk_tipologia_conferenza)
	REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);