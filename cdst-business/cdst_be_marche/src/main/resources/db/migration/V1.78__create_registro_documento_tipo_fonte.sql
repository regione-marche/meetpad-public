CREATE TABLE cdst.registro_documento_fonte
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT registro_documento_fonte_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE cdst.registro_documento_fonte
    OWNER to cdst;
    
INSERT INTO cdst.registro_documento_fonte(codice, descrizione) VALUES ('MEETPAD', 'da applicativo MeetPAD');
INSERT INTO cdst.registro_documento_fonte(codice, descrizione) VALUES ('SUAP', 'da applicativo SUAP');

CREATE TABLE cdst.registro_documento_tipo
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT registro_documento_tipo_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE cdst.registro_documento_tipo
    OWNER to cdst;
    
INSERT INTO cdst.registro_documento_tipo(codice, descrizione) VALUES ('FS', 'Filesystem');
INSERT INTO cdst.registro_documento_tipo(codice, descrizione) VALUES ('ALFRESCO', 'documentale Alfresco');        