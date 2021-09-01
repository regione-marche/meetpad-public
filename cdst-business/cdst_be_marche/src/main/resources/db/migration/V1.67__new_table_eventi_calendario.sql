CREATE TABLE cdst.eventi_calendario
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT eventi_calendario_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.eventi_calendario(codice, descrizione) VALUES ('1', 'data termine');
INSERT INTO cdst.eventi_calendario(codice, descrizione) VALUES ('2', 'espressione pareri');
INSERT INTO cdst.eventi_calendario(codice, descrizione) VALUES ('3', 'termine richiesta integrazioni');
INSERT INTO cdst.eventi_calendario(codice, descrizione) VALUES ('4', 'prima sessione simultanea');