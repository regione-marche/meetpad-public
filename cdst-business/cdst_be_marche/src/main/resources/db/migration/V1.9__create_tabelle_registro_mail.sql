CREATE SEQUENCE cdst.registro_email_testata_id_email_testata_seq;

ALTER SEQUENCE cdst.registro_email_testata_id_email_testata_seq
    OWNER TO cdst;

CREATE TABLE cdst.registro_email_testata
(
    id_email_testata integer NOT NULL DEFAULT nextval('cdst.registro_email_testata_id_email_testata_seq'::regclass),
    email character varying(255) COLLATE pg_catalog."default",
    fase_email character varying(255) COLLATE pg_catalog."default",
    fk_conferenza integer,
    id_messaggio character varying(255) COLLATE pg_catalog."default",
    subject character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT registro_email_testata_pkey PRIMARY KEY (id_email_testata)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE cdst.registro_email_testata
    OWNER to cdst;

CREATE SEQUENCE cdst.registro_email_dettaglio_id_email_dettaglio_seq;

ALTER SEQUENCE cdst.registro_email_dettaglio_id_email_dettaglio_seq
    OWNER TO cdst;

CREATE TABLE cdst.registro_email_dettaglio
(
    id_email_dettaglio integer NOT NULL DEFAULT nextval('cdst.registro_email_dettaglio_id_email_dettaglio_seq'::regclass),
    dt_sent_email timestamp without time zone,
    id_messaggio character varying(255) COLLATE pg_catalog."default",
    nota character varying(255) COLLATE pg_catalog."default",
    stato_email character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT registro_email_dettaglio_pkey PRIMARY KEY (id_email_dettaglio)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE cdst.registro_email_dettaglio
    OWNER to cdst;

CREATE UNIQUE INDEX indx_registro_email_dettaglio
    ON cdst.registro_email_dettaglio USING btree
    (id_messaggio COLLATE pg_catalog."default", stato_email COLLATE pg_catalog."default");

