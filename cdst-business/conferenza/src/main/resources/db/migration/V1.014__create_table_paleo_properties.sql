/**
drop TABLE paleo_tipoconferenza_properties;
drop TABLE paleo_fascicolo_tipoconferenza;
drop TABLE paleo_registry_adapter;

DROP SEQUENCE paleo_registry_adapter_seq;
DROP SEQUENCE paleo_tipoconferenza_properties_seq;
DROP SEQUENCE paleo_fascicolo_tipoconferenza_seq;

delete from flyway_schema_history where installed_rank>4;
*/

CREATE SEQUENCE paleo_tipoconferenza_properties_seq;

CREATE TABLE paleo_tipoconferenza_properties
(
    id integer NOT NULL DEFAULT nextval('paleo_tipoconferenza_properties_seq'::regclass),
    ID_TIPO_CONFERENZA character varying(255),
    AMBIENTE character varying(255),
    NOME_PROPERTIES character varying(255),
    VALORE_PROPERTIES character varying(255),
    TIPO_VALORE_PROPERTIES character varying(255),
	TIPO_PROPERTIES character varying(255),
	ISDEFAULT character varying(255),
	CONSTRAINT paleo_tipoconferenza_properties_pkey PRIMARY KEY (id)
);


CREATE SEQUENCE paleo_registry_adapter_seq;

CREATE TABLE paleo_registry_adapter
(
    id integer NOT NULL DEFAULT nextval('paleo_registry_adapter_seq'::regclass),
    FK_REGISTRO_DOCUMENTI integer,
    ID_PALEO_DOC integer,
	ID_PALEO_NUMERODOC  integer,
    ID_PALEO_PROTOCOLLO integer,
	PALEO_PROTOCOLLO_DATA character varying(255),
    FK_PALEO_FASCICOLO character varying(255),
    ID_PALEO_REGISTRO character varying(255),
	PALEO_OGGETTO character varying(255),
	PALEO_TIPO_PROTOCOLLO character varying(255),
	PALEO_INSTALLAZIONE character varying(255),
	PALEO_SEGNATURA_PROTOCOLLO character varying(255),
	CONSTRAINT paleo_registry_adapter_pkey PRIMARY KEY (id)
);

	

CREATE SEQUENCE paleo_fascicolo_tipoconferenza_seq;

CREATE TABLE paleo_fascicolo_tipoconferenza
(
    id integer NOT NULL DEFAULT nextval('paleo_fascicolo_tipoconferenza_seq'::regclass),
    ID_TIPO_CONFERENZA  character varying(255),
    ID_FASCICOLO character varying(255),
	NOME_FASCICOLO  character varying(255),
	CONSTRAINT paleo_fascicolo_tipoconferenza_pkey PRIMARY KEY (id)
);


insert into paleo_tipoconferenza_properties (
    ID_TIPO_CONFERENZA,
	AMBIENTE,
    NOME_PROPERTIES,
    VALORE_PROPERTIES,
    TIPO_VALORE_PROPERTIES,
	TIPO_PROPERTIES,
	ISDEFAULT
)
values(
NULL,
'DEV',
'paleo.url',
'https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc',
'String',
'paleo.url',
'S'
);

--//=====================================================================================
--//OPERATORE - DEV
--//=====================================================================================
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','paleo.url','paleo.url','https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc');

insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','paleo.operatore.codiceuo','paleo.operatore.codiceuo','INF');
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','paleo.operatore.nome','paleo.operatore.nome','POINT');
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','paleo.operatore.cognome','paleo.operatore.cognome','INF');
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','paleo.operatore.ruolo','paleo.operatore.ruolo','PROTOCOLLISTA');


--//=====================================================================================
--//DEFAULT CREAZIONE CONFERENZA
--//=====================================================================================
--//==============================	
--//[RESPONSABILE]:   2	"MGGMLR62M63G479H"	"MAGGIULLI"	"guido.deluca@postaraffaello.it"	"MARIALAURA"	
--id_responsabile	    		2					
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','id_responsabile','id_responsabile','2');
--responsabile_cf      		MGGMLR62M63G479H
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','responsabile_cf','responsabile_cf','MGGMLR62M63G479H');
--responsabile_cognome		MAGGIULLI
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','responsabile_cognome','responsabile_cognome','MAGGIULLI');
--responsabile_mail			guido.deluca@postaraffaello.it
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','responsabile_mail','responsabile_mail','guido.deluca@postaraffaello.it');
--responsabile_nome			MARIALAURA
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','responsabile_nome','responsabile_nome','MARIALAURA');
--responsabile_pec			null
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','responsabile_pec','responsabile_pec','guido.deluca@postaraffaello.it');
--//==============================
--//[RICHIEDENTE]:  43	"RSSGNN80A01H501N"	"Rossi"	"guido.deluca@postaraffaello.it"	"Giovanni"
--id_richiedented				43	
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','id_richiedented','id_richiedented','43');
--richiedente_cf				RSSGNN80A01H501N
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_cf','richiedente_cf','RSSGNN80A01H501N');
--richiedente_cognome			Rossi
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_cognome','richiedente_cognome','Rossi');
--richiedente_comune_istat	058091
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_comune_istat','richiedente_comune_istat','058091');
--richiedente_comune_nome		null
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_comune_nome','richiedente_comune_nome',null);
--richiedente_mail			guido.deluca@postaraffaello.it
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_mail','richiedente_mail','guido.deluca@postaraffaello.it');
--richiedente_nome			Giovanni
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_nome','richiedente_nome','Giovanni');
--richiedente_pec				null
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_pec','richiedente_pec','richiedente.pec@pec.it');
--richiedente_provincia_istat	058
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_provincia_istat','richiedente_provincia_istat','058');
--richiedente_provincia_nome	null
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','richiedente_provincia_nome','richiedente_provincia_nome',null);
--//==============================
--ente
--//==============================
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_nome','ente_nome','ente procedente');
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_pec','ente_pec','ente.prcedente@pec.it');

--//==============================
--enteProcedente
--//==============================
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_id','enteProcedente',null);


--		ente_codice_comune		string	
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_codice_comune','enteProcedente','string');
--	ente_codice_provincia	string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_codice_provincia','enteProcedente','string');
--	ente_codice_regione		string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_codice_regione','enteProcedente','string');
--	ente_comune				string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_comune','enteProcedente','string');
--	ente_nome				Regione Abruzzo
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_nome','enteProcedente','Regione Abruzzo');
--	ente_pec				regione.abruzzo@prova.it
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_pec','enteProcedente','regione.abruzzo@prova.it');
--	ente_provincia			string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_provincia','enteProcedente','string');
--	ente_regione			string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_comune','enteProcedente','string');
--//==============================
--entiPartecipanti
--//==============================
--		ente_codice_comune		string	
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_id','entiPartecipanti','80003170661');


insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_codice_comune','entiPartecipanti','string');
--	ente_codice_provincia	string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_codice_provincia','entiPartecipanti','string');
--	ente_codice_regione		string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_codice_regione','entiPartecipanti','string');
--	ente_comune				string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_comune','entiPartecipanti','string');
--	ente_nome				Regione Abruzzo
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_nome','entiPartecipanti','Ente Partecipante prova');
--	ente_pec				regione.abruzzo@prova.it
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_pec','entiPartecipanti','ente.partecipante@prova.it');
--	ente_provincia			string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_provincia','entiPartecipanti','string');
--	ente_regione			string
insert into paleo_tipoconferenza_properties (ID_TIPO_CONFERENZA,AMBIENTE,ISDEFAULT,TIPO_VALORE_PROPERTIES,NOME_PROPERTIES,TIPO_PROPERTIES,VALORE_PROPERTIES)values(
'4','DEV','N','String','ente_comune','entiPartecipanti','string');



--//==============================
--VALORE DI TEST
--//==============================
insert into paleo_fascicolo_tipoconferenza (id_tipo_conferenza,id_fascicolo,nome_fascicolo)values('4','150.30.130/2011/INF/55','Fascicolo AMBIENTE PALEO TEST');

