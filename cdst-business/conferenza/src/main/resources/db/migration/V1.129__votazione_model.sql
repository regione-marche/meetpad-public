CREATE TABLE cdst.votazione_criterio
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT votazione_criterio_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

CREATE TABLE cdst.votazione_esito
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT votazione_esito_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

CREATE TABLE cdst.votazione_stato
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT votazione_stato_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

CREATE TABLE cdst.votazione_tipo
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT votazione_tipo_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

CREATE SEQUENCE cdst.votazione_id_votazione_seq;
CREATE TABLE cdst.votazione
(
    id_votazione integer NOT NULL DEFAULT nextval('cdst.votazione_id_votazione_seq'::regclass),
    audit_id_usr_crt character varying(255) COLLATE pg_catalog."default" NOT NULL,
    audit_crt_time timestamp without time zone NOT NULL,
    audit_mod_time timestamp without time zone,
    audit_id_usr_mod character varying(255) COLLATE pg_catalog."default",
    data_fine timestamp without time zone,	
    argomento character varying(2000) COLLATE pg_catalog."default" NOT NULL,
    data_scadenza timestamp without time zone,
    fk_conferenza integer NOT NULL,
    fk_tipo_votazione character varying(255) COLLATE pg_catalog."default",
    fk_criterio_votazione character varying(255) COLLATE pg_catalog."default",
    fk_stato_votazione character varying(255) COLLATE pg_catalog."default",
    fk_esito_votazione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT votazione_pkey PRIMARY KEY (id_votazione),
    CONSTRAINT votazione_fk_conferenza FOREIGN KEY (fk_conferenza)
        REFERENCES cdst.conferenza (id_conferenza) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT votazione_fk_criterio_votazione FOREIGN KEY (fk_criterio_votazione)
        REFERENCES cdst.votazione_criterio (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT votazione_fk_esito_votazione FOREIGN KEY (fk_esito_votazione)
        REFERENCES cdst.votazione_esito (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT votazione_fk_stato_votazione FOREIGN KEY (fk_stato_votazione)
        REFERENCES cdst.votazione_stato (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT votazione_fk_tipo_votazione FOREIGN KEY (fk_tipo_votazione)
        REFERENCES cdst.votazione_tipo (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

CREATE TABLE cdst.votazione_partecipante
(
    id_votazione integer NOT NULL,
    id_partecipante integer NOT NULL
)
WITH (
    OIDS = FALSE
);

CREATE SEQUENCE cdst.votazione_voto_id_votazione_voto_seq;
CREATE TABLE cdst.votazione_voto
(
    id_votazione_voto integer NOT NULL DEFAULT nextval('cdst.votazione_voto_id_votazione_voto_seq'::regclass),
    voto boolean,
    motivazione character varying(2000) COLLATE pg_catalog."default",
    data_voto timestamp without time zone,
    fk_votazione integer NOT NULL,
    fk_partecipante integer,
    CONSTRAINT votazione_voto_pkey PRIMARY KEY (id_votazione_voto),
    CONSTRAINT votazione_voto_fk_partecipante FOREIGN KEY (fk_partecipante)
        REFERENCES cdst.partecipante (id_partecipante) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT votazione_voto_fk_votazione FOREIGN KEY (fk_votazione)
        REFERENCES cdst.votazione (id_votazione) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);