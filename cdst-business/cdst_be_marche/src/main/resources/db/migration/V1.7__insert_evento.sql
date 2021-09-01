CREATE TABLE cdst.oggetto_evento
(
    codice character varying(255),
    descrizione character varying(255),
    CONSTRAINT oggetto_evento_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('1', 'Richiesta Integrazioni') ON CONFLICT DO NOTHING;
INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('2', 'Comunicazione Generica') ON CONFLICT DO NOTHING;
INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('3', 'Trasmissione Parere') ON CONFLICT DO NOTHING;
INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('4', 'Altro') ON CONFLICT DO NOTHING;

CREATE TABLE cdst.tipo_evento
(
    codice character varying(255),
    descrizione character varying(255),
    fk_oggetto_evento character varying(255),
    CONSTRAINT tipo_evento_pkey PRIMARY KEY (codice),
    CONSTRAINT fk_oggetto_evento FOREIGN KEY (fk_oggetto_evento)
        REFERENCES cdst.oggetto_evento (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('1', 'Creazione Conferenza', null) ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('2', 'Convocazione Conferenza', null) ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('3', 'Richiesta Integrazioni', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('4', 'Chiusura Integrazioni', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('5', 'Richiesta unica di Integrazioni', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('6', 'Invia Integrazioni', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento) VALUES ('7', 'Comunicazione Generica', '2') ON CONFLICT DO NOTHING;


CREATE SEQUENCE cdst.evento_id_evento_seq;

CREATE TABLE cdst.evento
(
    id_evento integer NOT NULL DEFAULT nextval('cdst.evento_id_evento_seq'::regclass),
    audit_id_usr_crt character varying(255),
    audit_crt_time timestamp without time zone NOT NULL,
    audit_mod_time timestamp without time zone,
    audit_id_usr_mod character varying(255) COLLATE pg_catalog."default",
    data_fine timestamp without time zone,
    data_evento timestamp without time zone,
    protocollo character varying(255),
    corpo character varying(1000),
    fk_tipo_evento character varying(255),
	fk_oggetto_evento character varying(255),
	fk_conferenza integer,
	fk_partecipante integer,
	fk_documento integer,
	CONSTRAINT evento_pkey PRIMARY KEY (id_evento),
    CONSTRAINT fk_tipo_evento FOREIGN KEY (fk_tipo_evento)
        REFERENCES cdst.tipo_evento (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
	CONSTRAINT fk_oggetto_evento FOREIGN KEY (fk_oggetto_evento)
        REFERENCES cdst.oggetto_evento (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_conferenza FOREIGN KEY (fk_conferenza)
        REFERENCES cdst.conferenza (id_conferenza) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_partecipante FOREIGN KEY (fk_partecipante)
        REFERENCES cdst.partecipante (id_partecipante) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_documento FOREIGN KEY (fk_documento)
        REFERENCES cdst.documento (id_documento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)