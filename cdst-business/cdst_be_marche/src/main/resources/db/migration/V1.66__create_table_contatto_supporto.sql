CREATE SEQUENCE cdst.contatto_supporto_id_contatto_supporto_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE cdst.contatto_supporto
(
    id_contatto_supporto integer NOT NULL DEFAULT nextval('cdst.contatto_supporto_id_contatto_supporto_seq'::regclass),
    nome character varying(255) COLLATE pg_catalog."default",
    cognome character varying(255) COLLATE pg_catalog."default",
    email character varying(255) COLLATE pg_catalog."default",
    telefono character varying(255) COLLATE pg_catalog."default",
    fk_conferenza integer,
    CONSTRAINT contatto_supporto_pkey PRIMARY KEY (id_contatto_supporto),
    CONSTRAINT fk_conferenza FOREIGN KEY (fk_conferenza)
        REFERENCES cdst.conferenza (id_conferenza) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);