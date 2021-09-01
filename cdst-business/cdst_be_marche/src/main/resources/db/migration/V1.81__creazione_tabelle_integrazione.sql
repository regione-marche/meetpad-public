CREATE TABLE  integ_frontiera
(
    procedimento_id int,
    nome_procedimento character varying COLLATE pg_catalog."default",
    id_ente_suap int,
    id_responsabile_suap int,
    id_richiedente int,
    ente_id int,
    ente_nome character varying(100) COLLATE pg_catalog."default",
    ente_pec character varying(50) COLLATE pg_catalog."default",
    responsabile_nome character varying(255) COLLATE pg_catalog."default",
    responsabile_cognome character varying(255) COLLATE pg_catalog."default",
    responsabile_cf character varying(255) COLLATE pg_catalog."default",
    responsabile_pec character varying(255) COLLATE pg_catalog."default",
    responsabile_mail character varying(255) COLLATE pg_catalog."default",
    richiedente_nome character varying(255) COLLATE pg_catalog."default",
    richiedente_cognome character varying(255) COLLATE pg_catalog."default",
    richiedente_cf character varying(255) COLLATE pg_catalog."default",
    richiedente_pec character varying(255) COLLATE pg_catalog."default",
    richiedente_mail character varying(255) COLLATE pg_catalog."default",
	richiedente_comune_nome character varying(255) COLLATE pg_catalog."default",
	richiedente_comune_istat character varying(255) COLLATE pg_catalog."default",
	richiedente_provincia_nome character varying(255) COLLATE pg_catalog."default",
	richiedente_provincia_istat	character varying(255) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
);


CREATE TABLE integ_frontiera_document
(
    id_procedimento int,
    id int,
    id_pratica int,
    id_alfresco character varying COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
);

CREATE TABLE integ_enti
(
    id int,
	id_pratica int,
    ente_nome character varying(100) COLLATE pg_catalog."default",
    ente_pec character varying(50) COLLATE pg_catalog."default",
    ente_codice_comune character varying(6) COLLATE pg_catalog."default",
    ente_comune character varying(60) COLLATE pg_catalog."default",
    ente_codice_provincia character varying(3) COLLATE pg_catalog."default",
    ente_provincia character varying(60) COLLATE pg_catalog."default",
    ente_codice_regione character varying(2) COLLATE pg_catalog."default",
    ente_regione character varying(60) COLLATE pg_catalog."default"
)
WITH (
    OIDS = FALSE
);