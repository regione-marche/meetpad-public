CREATE TABLE cdst.tipo_parere
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tipo_parere_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.tipo_parere(codice, descrizione)	VALUES ('1', 'positivo');
INSERT INTO cdst.tipo_parere(codice, descrizione)	VALUES ('2', 'negativo');
INSERT INTO cdst.tipo_parere(codice, descrizione)	VALUES ('3', 'con prescrizioni');

ALTER TABLE cdst.evento ADD COLUMN fk_tipo_parere character varying(255);
ALTER TABLE cdst.evento ADD CONSTRAINT fk_tipo_parere FOREIGN KEY (fk_tipo_parere)
        REFERENCES cdst.tipo_parere (codice);