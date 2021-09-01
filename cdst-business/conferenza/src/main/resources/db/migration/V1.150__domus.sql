CREATE TABLE if not exists cdst.domus_conferenza_properties (
	id serial NOT NULL,
	id_tipo_conferenza varchar(255) NULL,
	ambiente varchar(255) NULL,
	nome_properties varchar(255) NULL,
	valore_properties varchar(255) NULL,
	tipo_valore_properties varchar(255) NULL,
	tipo_properties varchar(255) NULL,
	isdefault varchar(255) NULL,
	CONSTRAINT domus_conferenza_properties_pkey PRIMARY KEY (id)
);



INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
(NULL,'DEV','paleo.url','https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc','String','paleo.url','S')
,('3','DEV','paleo.url','https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc','String','paleo.url','N')
,('3','DEV','paleo.operatore.codiceuo','INF','String','paleo.operatore.codiceuo','N')
,('3','DEV','paleo.operatore.nome','POINT','String','paleo.operatore.nome','N')
,('3','DEV','paleo.operatore.cognome','INF','String','paleo.operatore.cognome','N')
,('3','DEV','paleo.operatore.ruolo','PROTOCOLLISTA','String','paleo.operatore.ruolo','N')
,('3','DEV','id_responsabile','36','String','id_responsabile','N')
,('3','DEV','responsabile_mail','ivana.atzori@eng.it','String','responsabile_mail','N')
,('3','DEV','responsabile_pec','ivana.atzori@eng.it','String','responsabile_pec','N')
,('3','DEV','id_richiedented','54','String','id_richiedented','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('3','DEV','richiedente_cf','RSSGNN80A01H501N','String','richiedente_cf','N')
,('3','DEV','richiedente_cognome','Rossi','String','richiedente_cognome','N')
,('3','DEV','richiedente_comune_istat','058091','String','richiedente_comune_istat','N')
,('3','DEV','richiedente_comune_nome',NULL,'String','richiedente_comune_nome','N')
,('3','DEV','richiedente_mail','guido.deluca@postaraffaello.it','String','richiedente_mail','N')
,('3','DEV','richiedente_nome','Giovanni','String','richiedente_nome','N')
,('3','DEV','richiedente_pec','richiedente.pec@pec.it','String','richiedente_pec','N')
,('3','DEV','richiedente_provincia_istat','058','String','richiedente_provincia_istat','N')
,('3','DEV','richiedente_provincia_nome',NULL,'String','richiedente_provincia_nome','N')
,('3','DEV','ente_nome','ente procedente','String','ente_nome','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('3','DEV','ente_pec','ente.prcedente@pec.it','String','ente_pec','N')
,('3','DEV','ente_codice_comune','string','String','enteProcedente','N')
,('3','DEV','ente_codice_provincia','string','String','enteProcedente','N')
,('3','DEV','ente_codice_regione','string','String','enteProcedente','N')
,('3','DEV','ente_comune','string','String','enteProcedente','N')
,('3','DEV','ente_provincia','string','String','enteProcedente','N')
,('3','DEV','ente_comune','string','String','enteProcedente','N')
,('3','DEV','ente_id','80003170661','String','entiPartecipanti','N')
,('3','DEV','ente_codice_comune','string','String','entiPartecipanti','N')
,('3','DEV','ente_codice_provincia','string','String','entiPartecipanti','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('3','DEV','ente_codice_regione','string','String','entiPartecipanti','N')
,('3','DEV','ente_comune','string','String','entiPartecipanti','N')
,('3','DEV','ente_nome','Ente Partecipante prova','String','entiPartecipanti','N')
,('3','DEV','ente_pec','ente.partecipante@prova.it','String','entiPartecipanti','N')
,('3','DEV','ente_provincia','string','String','entiPartecipanti','N')
,('3','DEV','ente_comune','string','String','entiPartecipanti','N')
,('3','DEV','responsabile_cognome','Atzori','String','responsabile_cognome','N')
,('3','DEV','responsabile_cf','TZRVNI79P58G113H','String','responsabile_cf','N')
,('3','DEV','responsabile_nome','Ivana','String','responsabile_nome','N')
,('3','DEV','ente_id','80008630420','String','enteProcedente','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('3','DEV','ente_pec','richiedente@prova.it','String','enteProcedente','N')
,('3','DEV','ente_nome','Regione Marche','String','enteProcedente','N')
,('1','DEV','paleo.url','https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc','String','paleo.url','N')
,('1','DEV','paleo.operatore.codiceuo','INF','String','paleo.operatore.codiceuo','N')
,('1','DEV','paleo.operatore.nome','POINT','String','paleo.operatore.nome','N')
,('1','DEV','paleo.operatore.cognome','INF','String','paleo.operatore.cognome','N')
,('1','DEV','paleo.operatore.ruolo','PROTOCOLLISTA','String','paleo.operatore.ruolo','N')
,('1','DEV','id_responsabile','2','String','id_responsabile','N')
,('1','DEV','responsabile_cf','MGGMLR62M63G479H','String','responsabile_cf','N')
,('1','DEV','responsabile_cognome','MAGGIULLI','String','responsabile_cognome','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('1','DEV','responsabile_mail','guido.deluca@postaraffaello.it','String','responsabile_mail','N')
,('1','DEV','responsabile_nome','MARIALAURA','String','responsabile_nome','N')
,('1','DEV','responsabile_pec','guido.deluca@postaraffaello.it','String','responsabile_pec','N')
,('1','DEV','id_richiedented','43','String','id_richiedented','N')
,('1','DEV','richiedente_cf','RSSGNN80A01H501N','String','richiedente_cf','N')
,('1','DEV','richiedente_cognome','Rossi','String','richiedente_cognome','N')
,('1','DEV','richiedente_comune_istat','058091','String','richiedente_comune_istat','N')
,('1','DEV','richiedente_comune_nome',NULL,'String','richiedente_comune_nome','N')
,('1','DEV','richiedente_mail','guido.deluca@postaraffaello.it','String','richiedente_mail','N')
,('1','DEV','richiedente_nome','Giovanni','String','richiedente_nome','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('1','DEV','richiedente_pec','richiedente.pec@pec.it','String','richiedente_pec','N')
,('1','DEV','richiedente_provincia_istat','058','String','richiedente_provincia_istat','N')
,('1','DEV','richiedente_provincia_nome',NULL,'String','richiedente_provincia_nome','N')
,('1','DEV','ente_nome','ente procedente','String','ente_nome','N')
,('1','DEV','ente_pec','ente.prcedente@pec.it','String','ente_pec','N')
,('1','DEV','ente_id',NULL,'String','enteProcedente','N')
,('1','DEV','ente_codice_comune','string','String','enteProcedente','N')
,('1','DEV','ente_codice_provincia','string','String','enteProcedente','N')
,('1','DEV','ente_codice_regione','string','String','enteProcedente','N')
,('1','DEV','ente_comune','string','String','enteProcedente','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('1','DEV','ente_nome','Regione Abruzzo','String','enteProcedente','N')
,('1','DEV','ente_pec','regione.abruzzo@prova.it','String','enteProcedente','N')
,('1','DEV','ente_provincia','string','String','enteProcedente','N')
,('1','DEV','ente_comune','string','String','enteProcedente','N')
,('1','DEV','ente_id','80003170661','String','entiPartecipanti','N')
,('1','DEV','ente_codice_comune','string','String','entiPartecipanti','N')
,('1','DEV','ente_codice_provincia','string','String','entiPartecipanti','N')
,('1','DEV','ente_codice_regione','string','String','entiPartecipanti','N')
,('1','DEV','ente_comune','string','String','entiPartecipanti','N')
,('1','DEV','ente_nome','Ente Partecipante prova','String','entiPartecipanti','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('1','DEV','ente_pec','ente.partecipante@prova.it','String','entiPartecipanti','N')
,('1','DEV','ente_provincia','string','String','entiPartecipanti','N')
,('1','DEV','ente_comune','string','String','entiPartecipanti','N')
,('2','DEV','paleo.url','https://paleotest.regionemarche.intra/PaleoWebServicesR_MARCHE/PaleoWebService.svc','String','paleo.url','N')
,('2','DEV','paleo.operatore.codiceuo','INF','String','paleo.operatore.codiceuo','N')
,('2','DEV','paleo.operatore.nome','POINT','String','paleo.operatore.nome','N')
,('2','DEV','paleo.operatore.cognome','INF','String','paleo.operatore.cognome','N')
,('2','DEV','paleo.operatore.ruolo','PROTOCOLLISTA','String','paleo.operatore.ruolo','N')
,('2','DEV','id_responsabile','30','String','id_responsabile','N')
,('2','DEV','responsabile_mail','andrea.rosina@eng.it','String','responsabile_mail','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('2','DEV','responsabile_pec','andrea.rosina@eng.it','String','responsabile_pec','N')
,('2','DEV','id_richiedented','43','String','id_richiedented','N')
,('2','DEV','richiedente_cf','RSSGNN80A01H501N','String','richiedente_cf','N')
,('2','DEV','richiedente_cognome','Rossi','String','richiedente_cognome','N')
,('2','DEV','richiedente_comune_istat','058091','String','richiedente_comune_istat','N')
,('2','DEV','richiedente_comune_nome',NULL,'String','richiedente_comune_nome','N')
,('2','DEV','richiedente_mail','guido.deluca@postaraffaello.it','String','richiedente_mail','N')
,('2','DEV','richiedente_nome','Giovanni','String','richiedente_nome','N')
,('2','DEV','richiedente_pec','richiedente.pec@pec.it','String','richiedente_pec','N')
,('2','DEV','richiedente_provincia_istat','058','String','richiedente_provincia_istat','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('2','DEV','richiedente_provincia_nome',NULL,'String','richiedente_provincia_nome','N')
,('2','DEV','ente_nome','ente procedente','String','ente_nome','N')
,('2','DEV','ente_pec','ente.prcedente@pec.it','String','ente_pec','N')
,('2','DEV','ente_codice_comune','string','String','enteProcedente','N')
,('2','DEV','ente_codice_provincia','string','String','enteProcedente','N')
,('2','DEV','ente_codice_regione','string','String','enteProcedente','N')
,('2','DEV','ente_comune','string','String','enteProcedente','N')
,('2','DEV','ente_provincia','string','String','enteProcedente','N')
,('2','DEV','ente_comune','string','String','enteProcedente','N')
,('2','DEV','ente_id','80003170661','String','entiPartecipanti','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('2','DEV','ente_codice_comune','string','String','entiPartecipanti','N')
,('2','DEV','ente_codice_provincia','string','String','entiPartecipanti','N')
,('2','DEV','ente_codice_regione','string','String','entiPartecipanti','N')
,('2','DEV','ente_comune','string','String','entiPartecipanti','N')
,('2','DEV','ente_nome','Ente Partecipante prova','String','entiPartecipanti','N')
,('2','DEV','ente_pec','ente.partecipante@prova.it','String','entiPartecipanti','N')
,('2','DEV','ente_provincia','string','String','entiPartecipanti','N')
,('2','DEV','ente_comune','string','String','entiPartecipanti','N')
,('2','DEV','responsabile_cognome','Rosina','String','responsabile_cognome','N')
,('2','DEV','responsabile_cf','RSNNDR74E11L719I','String','responsabile_cf','N')
;
INSERT INTO domus_conferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
('2','DEV','responsabile_nome','Andrea','String','responsabile_nome','N')
,('2','DEV','ente_id','80008630420','String','enteProcedente','N')
,('2','DEV','ente_pec','richiedente@prova.it','String','enteProcedente','N')
,('2','DEV','ente_nome','Regione Marche','String','enteProcedente','N')
;