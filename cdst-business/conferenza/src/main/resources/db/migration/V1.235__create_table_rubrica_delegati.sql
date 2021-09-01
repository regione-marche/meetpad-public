CREATE TABLE cdst.rubrica_delegati (
	id_rubrica_delegato serial NOT NULL,
	fk_persona int4 NULL,
	fk_tipologia_conferenza_specializzazione varchar(255) NULL,
	CONSTRAINT rubrica_delegati_pkey PRIMARY KEY (id_rubrica_delegato),
	CONSTRAINT fk_persona FOREIGN KEY (fk_persona) REFERENCES persona(id_persona),
	CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione) REFERENCES tipologia_conferenza_specializzazione(codice)
);

ALTER TABLE cdst.rubrica_delegati ADD rif_esterno varchar(255) NULL;
ALTER TABLE cdst.rubrica_delegati ADD nome_documento varchar(255) NULL;