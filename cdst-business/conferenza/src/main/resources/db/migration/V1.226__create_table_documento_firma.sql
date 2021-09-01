CREATE TABLE cdst.documento_firma_multipla (
	id serial NOT NULL,
	fk_documento int4 NOT NULL,
	fk_stato varchar(255) NULL DEFAULT 'DRAFT'::character varying,
	fk_responsabile_firma int4 NULL,
	fk_registro int4 NULL,
	dt_firma_ins timestamp NOT NULL DEFAULT 'now'::text::date,
	dt_firma_var timestamp NULL,
	fk_utente_creatore int4 NULL,
	CONSTRAINT documento_firma_multipla_pkey PRIMARY KEY (id),
	CONSTRAINT documento_firma_multipla_fk_documento FOREIGN KEY (fk_documento) REFERENCES documento(id_documento),
	CONSTRAINT documento_firma_multipla_fk_stato FOREIGN KEY (fk_stato) REFERENCES stato_firma(stato),
	CONSTRAINT documento_firma_multipla_fk_responsabile_firma FOREIGN KEY (fk_responsabile_firma) REFERENCES utente(id_utente),
	CONSTRAINT documento_firma_multipla_utente_creatore FOREIGN KEY (fk_utente_creatore) REFERENCES utente(id_utente)
);