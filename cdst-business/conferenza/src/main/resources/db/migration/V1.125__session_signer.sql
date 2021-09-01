CREATE SEQUENCE cdst.registro_firma_sessione_signer_seq;


CREATE TABLE cdst.registro_firma_sessione_signer (
    id integer NOT NULL DEFAULT nextval('registro_firma_sessione_signer_seq'::regclass),    
    fk_sessione character varying(255),    
	signer  integer,  
	owner integer ,  
	DT_INS timestamp
);