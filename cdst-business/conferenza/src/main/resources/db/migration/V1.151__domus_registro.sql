CREATE SEQUENCE IF NOT EXISTS cdst.domus_comune_seq;

CREATE TABLE  IF NOT EXISTS  cdst.domus_comune(
    id integer NOT NULL DEFAULT nextval('domus_comune_seq'::regclass),
    codice_comune character varying(255)
);


CREATE SEQUENCE  IF NOT EXISTS  cdst.domus_registry_adapter_testa_seq;

CREATE TABLE  IF NOT EXISTS  cdst.domus_registry_adapter_testa(
    id integer NOT NULL DEFAULT nextval('domus_registry_adapter_testa_seq'::regclass),    
	id_conferenza integer,
	id_pratica integer,
	fk_codice_comune character varying(255),
	dt_ins  timestamp       
);

CREATE SEQUENCE  IF NOT EXISTS  cdst.domus_registry_adapter_protocolli_seq;

CREATE TABLE  IF NOT EXISTS  cdst.domus_registry_adapter_protocolli(
	id integer NOT NULL DEFAULT nextval('domus_registry_adapter_protocolli_seq'::regclass),    
	fk_testata integer,
	id_documento character varying(255),
	signature  character varying(255),
	dt_ins timestamp   
);

CREATE SEQUENCE  IF NOT EXISTS  cdst.domus_registry_adapter_allegati_seq;
CREATE TABLE  IF NOT EXISTS cdst.domus_registry_adapter_allegati(
		id integer NOT NULL DEFAULT nextval('domus_registry_adapter_allegati_seq'::regclass),    
		fk_protocollo integer,
		is_principale  character varying(1),
		id_documento  character varying(255),
		signature character varying(255),
		nome_file character varying(255),
		fk_registro_documento integer,
		dt_ins timestamp 
);
