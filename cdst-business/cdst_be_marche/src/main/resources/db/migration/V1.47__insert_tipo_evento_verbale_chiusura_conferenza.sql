CREATE TABLE cdst.esito_chiusura_conferenza
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT esito_chiusura_conferenza_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.esito_chiusura_conferenza (codice, descrizione) VALUES ('1', 'Chiusa positivamente') ON CONFLICT DO NOTHING;
INSERT INTO cdst.esito_chiusura_conferenza (codice, descrizione) VALUES ('2', 'Chiusa positivamente con prescrizioni') ON CONFLICT DO NOTHING;
INSERT INTO cdst.esito_chiusura_conferenza (codice, descrizione) VALUES ('3', 'Chiusa negativamente') ON CONFLICT DO NOTHING;

INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('5', 'Verbale Conferenza') ON CONFLICT DO NOTHING;
INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('6', 'Chiusura Conferenza') ON CONFLICT DO NOTHING;

INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('9', 'Caricamento verbale riunione', '5') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('10', 'Chiusura Conferenza', '6') ON CONFLICT DO NOTHING;

INSERT INTO cdst.tipologia_documento (codice, descrizione) VALUES ('VERBALE_RIUNIONE', 'tipologiaDocumento.verbaleRiunione') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipologia_documento (codice, descrizione) VALUES ('DETERMINA', 'tipologiaDocumento.determina') ON CONFLICT DO NOTHING;

ALTER TABLE cdst.evento ADD COLUMN fk_esito_chiusura_conferenza character varying(255);

ALTER TABLE cdst.evento
ADD CONSTRAINT fk_esito_chiusura_conferenza FOREIGN KEY (fk_esito_chiusura_conferenza)
        REFERENCES cdst.esito_chiusura_conferenza (codice);