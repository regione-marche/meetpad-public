CREATE TABLE cdst.modalita
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT modalita_pkey PRIMARY KEY (codice)
);

ALTER TABLE cdst.documento
ADD numero_protocollo varchar(255), 
ADD data_protocollo timestamp without time zone, 
ADD numero_inventario varchar(255);

ALTER TABLE cdst.conferenza
ADD fk_modalita_sessione_simultanea varchar(255),
ADD 	CONSTRAINT fk_conferenza_modalita FOREIGN KEY (fk_modalita_sessione_simultanea)
        REFERENCES cdst.modalita (codice);

