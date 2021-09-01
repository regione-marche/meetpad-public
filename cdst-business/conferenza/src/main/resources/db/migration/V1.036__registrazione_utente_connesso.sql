CREATE SEQUENCE cdst.registro_accesso_id_registro_accesso_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE cdst.registro_accesso
(
    id_registro_accesso integer NOT NULL DEFAULT nextval('cdst.registro_accesso_id_registro_accesso_seq'::regclass),
    codice_fiscale character varying(255) COLLATE pg_catalog."default",
    flag_utente_esistente boolean,
    descrizione_tipo_profilo character varying(255) COLLATE pg_catalog."default",
    data_accesso timestamp without time zone,
    CONSTRAINT id_registro_accesso_pkey PRIMARY KEY (id_registro_accesso)
);