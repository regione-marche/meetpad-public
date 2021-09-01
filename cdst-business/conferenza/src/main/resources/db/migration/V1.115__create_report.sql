/**

TIPOREPORT={REPORT,OPENDATA}
PARAMETRODEFAULT = PARAMETRO/LISTA DI PARAMETRI  CON CUI SI INTENDE PARAMETRIZZARE IL REPORT
PER DATO CODICE MENEMONICO..

*/
CREATE SEQUENCE cdst.report_seq;

CREATE TABLE cdst.report (
    id integer NOT NULL DEFAULT nextval('report_seq'::regclass),    
    CODICE character varying(255),    
	DESCRIZIONE character varying(255),    
	TIPOREPORT character varying(255) DEFAULT 'REPORT',    
	MODELLO character varying(255),    
	FK_PARAMETRO integer,    
	PARAMETRODEFAULT character varying(255)
);
	
	
