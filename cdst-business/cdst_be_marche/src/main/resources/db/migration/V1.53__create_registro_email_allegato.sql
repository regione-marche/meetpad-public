CREATE SEQUENCE cdst.registro_email_allegato_id_registro_email_allegato_seq;

ALTER SEQUENCE cdst.registro_email_allegato_id_registro_email_allegato_seq
    OWNER TO cdst;

CREATE TABLE cdst.registro_email_allegato
(
    id_registro_email_allegato integer NOT NULL DEFAULT nextval('cdst.registro_email_allegato_id_registro_email_allegato_seq'::regclass),
    fk_registro_email_testata integer,
	fk_documento integer,
    CONSTRAINT registro_email_allegato_pkey PRIMARY KEY (id_registro_email_allegato),
    CONSTRAINT fk_registro_email_testata FOREIGN KEY (fk_registro_email_testata) REFERENCES cdst.registro_email_testata (id_email_testata) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_documento FOREIGN KEY (fk_documento) REFERENCES cdst.documento (id_documento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

ALTER TABLE cdst.registro_email_allegato
    OWNER to cdst;