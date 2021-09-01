CREATE TABLE cdst.rubrica_preaccreditati (
	id_rubrica_preaccreditato serial NOT NULL,
	fk_persona int4 NULL,
	fk_tipologia_conferenza_specializzazione varchar(255) NULL,
	fk_ruolo_persona varchar(255) NULL,
	fk_ente int4 NULL,
	CONSTRAINT rubrica_preaccreditati_pkey PRIMARY KEY (id_rubrica_preaccreditato),
	CONSTRAINT fk_persona FOREIGN KEY (fk_persona) REFERENCES cdst.persona(id_persona),
	CONSTRAINT fk_tipologia_conferenza_specializzazione FOREIGN KEY (fk_tipologia_conferenza_specializzazione) REFERENCES cdst.tipologia_conferenza_specializzazione(codice),
	CONSTRAINT fk_ruolo_persona FOREIGN KEY (fk_ruolo_persona) REFERENCES cdst.ruolo_persona(codice),
	CONSTRAINT fk_ente FOREIGN KEY (fk_ente) REFERENCES ente(id_ente)
);