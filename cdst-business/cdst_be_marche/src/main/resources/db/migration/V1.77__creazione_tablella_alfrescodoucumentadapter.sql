CREATE SEQUENCE cdst.alfresco_document_adapter_seq;

ALTER SEQUENCE cdst.alfresco_document_adapter_seq
    OWNER TO cdst;

CREATE TABLE cdst.alfresco_document_adapter
(
	ID integer NOT NULL DEFAULT nextval('cdst.alfresco_document_adapter_seq'::regclass),
    DOCUMENTID character varying(255) COLLATE pg_catalog."default",
    NAME character varying(255) COLLATE pg_catalog."default",
    ALFRESCOID character varying(255) COLLATE pg_catalog."default",
	ALFRESCOPATHID character varying(255) COLLATE pg_catalog."default",
    ALFRESCOPATH character varying(255) COLLATE pg_catalog."default",
    NOTE character varying(255) COLLATE pg_catalog."default",
	ID_PRATICA_EXT character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT alfresco_document_adapter_pkey PRIMARY KEY (ID)
)
WITH (
    OIDS = FALSE
);