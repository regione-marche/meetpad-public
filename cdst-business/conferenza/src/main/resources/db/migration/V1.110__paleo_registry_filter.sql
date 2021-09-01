CREATE SEQUENCE IF NOT EXISTS cdst.paleo_registry_filter_seq;


CREATE TABLE IF NOT EXISTS cdst.paleo_registry_filter(
	id integer NOT NULL DEFAULT nextval('paleo_registry_filter_seq'::regclass),
	condizione character varying(255), 
	fk_tipologia_conferenza integer, 
	tipofiltro character varying(255) default 'TIPOCONFERENZA', 	
	codiceriferimento  character varying(255), 
	descrizione character varying(255), 
	CONSTRAINT paleo_registry_filter_pkey PRIMARY KEY (id)
);

