/**
REPORT ADAPTER 


*/
CREATE SEQUENCE cdst.report_adapter_seq;
CREATE SEQUENCE cdst.report_parameter_seq;


CREATE TABLE cdst.report_adapter(
    id integer NOT NULL DEFAULT nextval('report_adapter_seq'::regclass),
	FK_REPORT integer,
	TOKEN character varying(255),
	REPORTPATH character varying(255),
	DELETED character varying(255),
	DT_INS timestamp,
	FK_EXTERN_REPORT integer,
	EXTERN_REPORT_TYPE character varying(255)
);


CREATE TABLE cdst.report_parameter (
    id integer NOT NULL DEFAULT nextval('report_parameter_seq'::regclass),
    FK_REPORT integer,
	PARAMETRONOME character varying(255),
	PARAMETROTIPO character varying(255),
	PARAMETRODEFAULT character varying(255)
);


