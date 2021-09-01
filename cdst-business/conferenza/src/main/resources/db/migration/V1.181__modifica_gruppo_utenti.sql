

DROP TABLE IF EXISTS cdst.gruppo_utenti;

CREATE TABLE cdst.gruppo_utenti (
	id_gruppo serial NOT NULL,
	fk_utente int4 NULL,
	fk_utente_gruppo int4 NULL,
	CONSTRAINT id_gruppoutenti_pkey PRIMARY KEY (id_gruppo),
	CONSTRAINT fk8jg5rc9g9mao6t5g1cpb42te1 FOREIGN KEY (fk_utente) REFERENCES utente(id_utente),
	CONSTRAINT fkeq519d7c9efdf4dmloktym6yt FOREIGN KEY (fk_utente_gruppo) REFERENCES utente(id_utente)
);