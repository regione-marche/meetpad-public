
--delete from cdst.paleo_tipoconferenza_properties where id_tipo_conferenza='6';
--delete from cdst.domus_conferenza_properties where id_tipo_conferenza='6';
--delete from cdst.observer_registry where fk_conferenza_specializzazione='6';
--drop view view_submit_to_external_protocol;
--ALTER TABLE cdst.observer_registry DROP COLUMN fk_conferenza_specializzazione;

INSERT INTO cdst.domus_conferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) VALUES
('6', 'DEV', 'id_responsabile', '36', 'String', 'id_responsabile', 'N'),
('6', 'DEV', 'responsabile_mail', 'ivana.atzori@eng.it', 'String', 'responsabile_mail', 'N'),
('6', 'DEV', 'responsabile_pec', 'ivana.atzori@eng.it', 'String', 'responsabile_pec', 'N'),
('6', 'DEV', 'id_richiedented', '54', 'String', 'id_richiedented', 'N'),
('6', 'DEV', 'richiedente_cf', 'RSSGNN80A01H501N', 'String', 'richiedente_cf', 'N'),
('6', 'DEV', 'richiedente_cognome', 'Rossi', 'String', 'richiedente_cognome', 'N'),
('6', 'DEV', 'richiedente_comune_nome', NULL, 'String', 'richiedente_comune_nome', 'N'),
('6', 'DEV', 'richiedente_mail', 'guido.deluca@postaraffaello.it', 'String', 'richiedente_mail', 'N'),
('6', 'DEV', 'richiedente_nome', 'Giovanni', 'String', 'richiedente_nome', 'N'),
('6', 'DEV', 'richiedente_pec', 'richiedente.pec@pec.it', 'String', 'richiedente_pec', 'N'),
('6', 'DEV', 'richiedente_provincia_nome', NULL, 'String', 'richiedente_provincia_nome', 'N'),
('6', 'DEV', 'ente_nome', 'ente procedente', 'String', 'ente_nome', 'N'),
('6', 'DEV', 'ente_pec', 'ente.prcedente@pec.it', 'String', 'ente_pec', 'N'),
('6', 'DEV', 'ente_codice_comune', 'string', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_codice_provincia', 'string', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_codice_regione', 'string', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_comune', 'string', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_provincia', 'string', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_comune', 'string', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_id', '80003170661', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_codice_comune', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_codice_provincia', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_codice_regione', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_comune', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_nome', 'Ente Partecipante prova', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_pec', 'ente.partecipante@prova.it', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_provincia', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'ente_comune', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'DEV', 'responsabile_cognome', 'Atzori', 'String', 'responsabile_cognome', 'N'),
('6', 'DEV', 'responsabile_cf', 'TZRVNI79P58G113H', 'String', 'responsabile_cf', 'N'),
('6', 'DEV', 'responsabile_nome', 'Ivana', 'String', 'responsabile_nome', 'N'),
('6', 'DEV', 'ente_id', '80008630420', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_pec', 'richiedente@prova.it', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'ente_nome', 'Regione Marche', 'String', 'enteProcedente', 'N'),
('6', 'DEV', 'richiedente_provincia_istat', '042', 'String', 'richiedente_provincia_istat', 'N'),
('6', 'DEV', 'richiedente_comune_istat', '042002', 'String', 'richiedente_comune_istat', 'N');

INSERT INTO cdst.domus_conferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) VALUES
('6', 'PROD', 'responsabile_cf', 'DLBDNL67T65A271P', 'String', 'responsabile_cf', 'N'),
('6', 'PROD', 'responsabile_mail', 'daniela.delbello@regione.marche.it', 'String', 'responsabile_mail', 'N'),
('6', 'PROD', 'responsabile_pec', 'daniela.delbello@regione.marche.it', 'String', 'responsabile_pec', 'N'),
('6', 'PROD', 'responsabile_cognome', 'DEL BELLO', 'String', 'responsabile_cognome', 'N'),
('6', 'PROD', 'responsabile_nome', 'DANIELA', 'String', 'responsabile_nome', 'N'),
('6', 'PROD', 'richiedente_mail', 'richiedente.pec@pec.it', 'String', 'richiedente_mail', 'N'),
('6', 'PROD', 'richiedente_cf', 'RSSGNN80A01H501N', 'String', 'richiedente_cf', 'N'),

('6', 'PROD', 'id_responsabile', '36', 'String', 'id_responsabile', 'N'),
('6', 'PROD', 'id_richiedented', '54', 'String', 'id_richiedented', 'N'),
('6', 'PROD', 'richiedente_cognome', 'Rossi', 'String', 'richiedente_cognome', 'N'),
('6', 'PROD', 'richiedente_comune_nome', NULL, 'String', 'richiedente_comune_nome', 'N'),
('6', 'PROD', 'richiedente_nome', 'Giovanni', 'String', 'richiedente_nome', 'N'),
('6', 'PROD', 'richiedente_pec', 'richiedente.pec@pec.it', 'String', 'richiedente_pec', 'N'),
('6', 'PROD', 'richiedente_provincia_nome', NULL, 'String', 'richiedente_provincia_nome', 'N'),
('6', 'PROD', 'ente_nome', 'ente procedente', 'String', 'ente_nome', 'N'),
('6', 'PROD', 'ente_pec', 'ente.prcedente@pec.it', 'String', 'ente_pec', 'N'),
('6', 'PROD', 'ente_codice_comune', 'string', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_codice_provincia', 'string', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_codice_regione', 'string', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_comune', 'string', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_provincia', 'string', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_comune', 'string', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_id', '80003170661', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_codice_comune', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_codice_provincia', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_codice_regione', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_comune', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_nome', 'Ente Partecipante prova', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_pec', 'ente.partecipante@prova.it', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_provincia', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_comune', 'string', 'String', 'entiPartecipanti', 'N'),
('6', 'PROD', 'ente_id', '80008630420', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_pec', 'richiedente@prova.it', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'ente_nome', 'Regione Marche', 'String', 'enteProcedente', 'N'),
('6', 'PROD', 'richiedente_provincia_istat', '042', 'String', 'richiedente_provincia_istat', 'N'),
('6', 'PROD', 'richiedente_comune_istat', '042002', 'String', 'richiedente_comune_istat', 'N');


INSERT INTO cdst.paleo_tipoconferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
 ('6','DEV','paleo.url','https://paleo.regionemarche.intra/PaleoWebServicesUSRTest/PaleoWebService.svc','String','paleo.url','N')

--<add key="Cognome.Paleo" value="USR"/>
,('6','DEV','paleo.operatore.cognome','USR','String','paleo.operatore.cognome','N')
--<add key="Nome.Paleo" value="PROTVIRT"/>
,('6','DEV','paleo.operatore.nome','PROTVIRT','String','paleo.operatore.nome','N')
--<add key="Ruolo.Paleo" value="Protocollista"/>
,('6','DEV','paleo.operatore.ruolo','Protocollista','String','paleo.operatore.ruolo','N')
--<add key="UO.Paleo" value="USR"/>
,('6','DEV','paleo.operatore.codiceuo','USR','String','paleo.operatore.codiceuo','N')
--<add key="CodRegistro.Paleo" value="MARCHEUSR"/>
,('6','DEV','codice.registro','MARCHEUSR','String','paleo.protocollo','N')

,('6','DEV','id_responsabile','36','String','id_responsabile','N')
,('6','DEV','responsabile_mail','katjuscia.granci@regione.marche.it','String','responsabile_mail','N')
,('6','DEV','responsabile_pec','katjuscia.granci@regione.marche.it','String','responsabile_pec','N')
,('6','DEV','ente_nome','ente procedente','String','ente_nome','N')
,('6','DEV','ente_pec','ente.prcedente@pec.it','String','ente_pec','N')
,('6','DEV','ente_codice_comune','string','String','enteProcedente','N')
,('6','DEV','ente_codice_provincia','string','String','enteProcedente','N')
,('6','DEV','ente_codice_regione','string','String','enteProcedente','N')
,('6','DEV','ente_comune','string','String','enteProcedente','N')
,('6','DEV','ente_provincia','string','String','enteProcedente','N')
,('6','DEV','ente_comune','string','String','enteProcedente','N')
,('6','DEV','responsabile_cognome','Granci','String','responsabile_cognome','N')
,('6','DEV','responsabile_cf','GRNKJS73D56G479J','String','responsabile_cf','N')
,('6','DEV','responsabile_nome','Katjuscia','String','responsabile_nome','N')
,('6','DEV','ente_id','80008630420','String','enteProcedente','N')
,('6','DEV','ente_pec','regione.marche.informatica@emarche.it','String','enteProcedente','N')
,('6','DEV','ente_nome','Regione Marche','String','enteProcedente','N')
,('6','DEV','codice.rubrica.destinatario','GRMA','String','paleo.protocollo','N')
,('6','DEV','richiedente_attivita','22','String','richiedente_attivita','N')
,('6','DEV','richiedente_tipologia','2','String','richiedente_tipologia','N')
,('6','DEV','richiedente_azione','69','String','richiedente_azione','N');

INSERT INTO cdst.paleo_tipoconferenza_properties (id_tipo_conferenza,ambiente,nome_properties,valore_properties,tipo_valore_properties,tipo_properties,isdefault) VALUES 
 ('6','PROD','paleo.url','https://paleo.regionemarche.intra/PaleoWebServicesUSR/PALEOWEBSERvice.svc','String','paleo.url','N')

--<add key="Cognome.Paleo" value="USR"/>
,('6','PROD','paleo.operatore.cognome','USR','String','paleo.operatore.cognome','N')
--<add key="Nome.Paleo" value="PROTVIRT"/>
,('6','PROD','paleo.operatore.nome','PROTVIRT','String','paleo.operatore.nome','N')
--<add key="Ruolo.Paleo" value="Protocollista"/>
,('6','PROD','paleo.operatore.ruolo','Protocollista','String','paleo.operatore.ruolo','N')
--<add key="UO.Paleo" value="USR"/>
,('6','PROD','paleo.operatore.codiceuo','USR','String','paleo.operatore.codiceuo','N')
--<add key="CodRegistro.Paleo" value="MARCHEUSR"/>
,('6','PROD','codice.registro','MARCHEUSR','String','paleo.protocollo','N')

,('6','PROD','responsabile_mail','daniela.delbello@regione.marche.it','String','responsabile_mail','N')
,('6','PROD','responsabile_pec','daniela.delbello@regione.marche.it','String','responsabile_pec','N')
,('6','PROD','responsabile_cognome','DEL BELLO','String','responsabile_cognome','N')
,('6','PROD','responsabile_cf','DLBDNL67T65A271P','String','responsabile_cf','N')
,('6','PROD','responsabile_nome','DANIELA','String','responsabile_nome','N')

,('6','PROD','ente_id','80008630420','String','enteProcedente','N')
,('6','PROD','ente_pec','regione.marche.informatica@emarche.it','String','enteProcedente','N')
,('6','PROD','ente_nome','Regione Marche','String','enteProcedente','N')
,('6','PROD','codice.rubrica.destinatario','GRMA','String','paleo.protocollo','N')
,('6','PROD','richiedente_attivita','22','String','richiedente_attivita','N')
,('6','PROD','richiedente_tipologia','2','String','richiedente_tipologia','N')
,('6','PROD','richiedente_azione','69','String','richiedente_azione','N')
,('6','PROD','ente_nome','ente procedente','String','ente_nome','N')
,('6','PROD','ente_pec','ente.prcedente@pec.it','String','ente_pec','N')
,('6','PROD','id_responsabile','36','String','id_responsabile','N')

,('6','PROD','ente_codice_comune','string','String','enteProcedente','N')
,('6','PROD','ente_codice_provincia','string','String','enteProcedente','N')
,('6','PROD','ente_codice_regione','string','String','enteProcedente','N')
,('6','PROD','ente_comune','string','String','enteProcedente','N')
,('6','PROD','ente_provincia','string','String','enteProcedente','N')
,('6','PROD','ente_comune','string','String','enteProcedente','N');

--<add key="CodAmministrazione.Paleo" value="USRTEST"/>
--<add key="UserId.Paleo" value="USR.PROTVIRT"/>
--<add key="Password.Paleo" value="pippo"/>


--<add key="CodClassifica.Paleo" value="430.16"/>
--<add key="FascicoloUnicoIngresso.Paleo" value="490.40.20/2017/USR/4" />

ALTER TABLE cdst.observer_registry ADD COLUMN fk_conferenza_specializzazione character varying(255);

INSERT INTO cdst.observer_registry
(id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione)
VALUES(NULL, 'SCRITTURA-PROTOCOLLO-DOMUS-PALEO-USCITA', 'Protocollazione DOMUS-Paleo in Uscita', '2', 'PaleoObserverDomusExitListnerInterface', '20', '6');
INSERT INTO cdst.observer_registry
(id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione)
VALUES(NULL, 'SCRITTURA-PROTOCOLLO-DOMUS-PALEO-USCITA', 'Protocollazione DOMUS-Paleo in Uscita', '5', 'PaleoObserverDomusExitListnerInterface', '22', '6');
INSERT INTO cdst.observer_registry
(id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione)
VALUES(NULL, 'SCRITTURA-PROTOCOLLO-DOMUS-PALEO-USCITA', 'Protocollazione DOMUS-Paleo in Uscita', '9', 'PaleoObserverDomusExitListnerInterface', '23', '6');
INSERT INTO cdst.observer_registry
(id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione)
VALUES(NULL, 'SCRITTURA-PROTOCOLLO-DOMUS-PALEO-USCITA', 'Protocollazione DOMUS-Paleo in Uscita', '12', 'PaleoObserverDomusExitListnerInterface', '27', '6');
INSERT INTO cdst.observer_registry
(id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione)
VALUES(NULL, 'SCRITTURA-PROTOCOLLO-DOMUS-PALEO-INOUT', 'Protocollazione DOMUS-Paleo in InOut', '7', 'PaleoDomusObserverInOutListnerInterface', '29', '6');



CREATE OR REPLACE VIEW cdst.view_submit_to_external_protocol
AS SELECT DISTINCT rd.id AS id_registro,
    dd.fk_conferenza AS id_conferenza,
    fc.id_pratica,
    fc.id_fascicolo,
    rd.rif_esterno AS id_protocollo,
    ee.id_evento,
    ee.fk_tipo_evento AS id_tipo_evento,
    dd.id_documento,
    te.codice AS subrscribed_event_type,
    te.fk_tipologia_documento AS tipologia_documento,
    rr.id AS id_observer_registry,
    rr.codice,
    rr.protocol_event_type,
    rr.fk_conferenza_specializzazione
   FROM registro_documento rd
     JOIN documento dd ON rd.fk_documento = dd.id_documento
     JOIN integ_frontiera_conferenza fc ON fc.fk_conferenza = dd.fk_conferenza
     JOIN evento ee ON ee.fk_documento = dd.id_documento
     JOIN tipo_evento te ON te.codice::text = ee.fk_tipo_evento::text
     JOIN (select subrscribed_event_type, max(fk_conferenza_specializzazione) as fk_conferenza_specializzazione from observer_registry group by subrscribed_event_type) r1 ON r1.subrscribed_event_type::text = te.codice::text
     JOIN observer_registry rr ON (rr.subrscribed_event_type::text=r1.subrscribed_event_type::text and (rr.fk_conferenza_specializzazione=r1.fk_conferenza_specializzazione or r1.fk_conferenza_specializzazione is null) )
     LEFT JOIN protocollo prot ON prot.fk_protocollo = rd.id AND prot.fk_stati_protocollo = 3
  WHERE 1 = 1 AND prot.id IS NULL;
 
 
-- Permissions

ALTER TABLE cdst.view_submit_to_external_protocol OWNER TO cdst;
GRANT ALL ON TABLE cdst.view_submit_to_external_protocol TO cdst;


