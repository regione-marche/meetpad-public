CREATE SEQUENCE cdst.registro_firma_adapter_sessione_seq;

CREATE TABLE cdst.registro_firma_adapter_sessione (
    id integer NOT NULL DEFAULT nextval('registro_firma_adapter_sessione_seq'::regclass),
    fk_documento integer,
    token character varying(255),    
	dt_sessione_begin timestamp not null default CURRENT_DATE,
	dt_sessione_end timestamp ,
    CONSTRAINT registro_firma_adapter_sessione_pkey PRIMARY KEY (id),
    CONSTRAINT fk_documento FOREIGN KEY (fk_documento) REFERENCES cdst.documento (id_documento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

