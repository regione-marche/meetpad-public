CREATE SEQUENCE cdst.registro_firma_adapter_seq;

CREATE SEQUENCE cdst.registro_fsfirma_adapter_seq;

CREATE TABLE cdst.tipo_firma(
	id integer NOT NULL,
	tipofirma character varying(255), 
	descrizione character varying(255), 
	marshalling character varying(255),
	CONSTRAINT tipo_firma_pkey PRIMARY KEY (id)
);


insert into cdst.tipo_firma (id,tipofirma,descrizione,marshalling)values
(1,'FIRMAFS','Upload File Firmati','conferenza.firma.adapter.FSFirmaAdapter');


CREATE TABLE cdst.stato_firma(
	id integer NOT NULL,
	stato character varying(255), 
	descrizione character varying(255),
	CONSTRAINT stato_firma_pkey PRIMARY KEY (stato)
);


insert into cdst.stato_firma (id,stato,descrizione)values
(1,'LOCKED','Il file è stato bloccato ad altre modifiche');


insert into cdst.stato_firma (id,stato,descrizione)values
(3,'UNLOCKED','Il file è stato sbloccato per permettere le  modifiche');

insert into cdst.stato_firma (id,stato,descrizione)values
(5,'DELETED','Il processo di firma è stato annullato');



CREATE TABLE cdst.registro_firma_adapter (
    id integer NOT NULL DEFAULT nextval('registro_firma_adapter_seq'::regclass),
    fk_registro integer,
    id_documento character varying(255),    
	stato character varying(255) DEFAULT 'LOCKED',    
    id_user character varying(255),
	token character varying(255),
	fase character varying(255),
	crc character varying(500),
	dt_firma_ins date not null default CURRENT_DATE,
	dt_firma_var date,
	fk_tipo_firma integer,
    CONSTRAINT registro_firma_adapter_pkey PRIMARY KEY (id),
    CONSTRAINT fk_tipo_firma FOREIGN KEY (fk_tipo_firma) REFERENCES cdst.tipo_firma (id) MATCH SIMPLE,
	CONSTRAINT fk_stato FOREIGN KEY (stato) REFERENCES cdst.stato_firma (stato) MATCH SIMPLE	
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE cdst.registro_fsfirma_adapter (
    id integer NOT NULL DEFAULT nextval('registro_fsfirma_adapter_seq'::regclass),
	fk_rfa integer,
	pathfirma character varying(255)   ,
	CONSTRAINT registro_fsfirma_adapter_pkey PRIMARY KEY (id),
	CONSTRAINT fk_registro_firma FOREIGN KEY (fk_rfa) REFERENCES cdst.registro_firma_adapter (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION	
);	


