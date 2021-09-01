
CREATE SEQUENCE ooadapter_seq;

CREATE TABLE oo_adapter
(
    id integer NOT NULL DEFAULT nextval('ooadapter_seq'::regclass),
    fk_registro integer,
    id_doc_oo character varying(255),
    id_folder_oo character varying(255),
    id_user_oo character varying(255),
    CONSTRAINT ooadapter_pkey PRIMARY KEY (id),
    CONSTRAINT fk_registro_ FOREIGN KEY (fk_registro)
        REFERENCES registro_documento (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

	
	
	
	