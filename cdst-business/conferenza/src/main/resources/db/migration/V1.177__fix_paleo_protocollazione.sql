/*
fix properties paleo protocollazione
*/

insert into tipo_evento values 
('27', 'Protocollazione Determina Finale', 13, 'DETERMINA', false, false), 
('28', 'Protocollazione Espressione Pareri', 13, 'ESPRESSIONE_PARERI', false, false),
('29', 'Protocollazione Comunicazione Generica', 13, 'COMUNICAZIONE_GENERICA', false, false);

delete from observer_registry where codice like '%PALEO%';

insert into observer_registry (codice, nome, subrscribed_event_type, class, protocol_event_type) values 
('SCRITTURA-PROTOCOLLO-PALEO-USCITA','Protocollazione Paleo in Uscita', 2, 'PaleoObserverExitListnerInterface', 20),
('SCRITTURA-PROTOCOLLO-PALEO-USCITA','Protocollazione Paleo in Uscita', 5, 'PaleoObserverExitListnerInterface', 22),
('SCRITTURA-PROTOCOLLO-PALEO-USCITA', 'Protocollazione Paleo in Uscita', 9, 'PaleoObserverExitListnerInterface', 23),
('SCRITTURA-PROTOCOLLO-PALEO-USCITA', 'Protocollazione Paleo in Uscita', 12, 'PaleoObserverExitListnerInterface', 27),
('SCRITTURA-PROTOCOLLO-PALEO-INGRESSO','Protocollazione Paleo in ingresso', 3, 'PaleoObserverIncomingListnerInterface', 21),
('SCRITTURA-PROTOCOLLO-PALEO-INGRESSO','Protocollazione Paleo in ingresso', 13, 'PaleoObserverIncomingListnerInterface', 21),
('SCRITTURA-PROTOCOLLO-PALEO-INGRESSO', 'Protocollazione Paleo in ingresso', 11, 'PaleoObserverIncomingListnerInterface', 28),
('SCRITTURA-PROTOCOLLO-PALEO-INOUT', 'Protocollazione Paleo in InOut', 7, 'PaleoObserverInOutListnerInterface', 29);

/*
inserimento attività, tipo protocollo e azione di default
*/

insert into paleo_tipoconferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) values 
('8', 'DEV', 'richiedente_attivita', '22', 'String', 'richiedente_attivita', 'N'), 
('8', 'DEV', 'richiedente_tipologia', '2', 'String', 'richiedente_tipologia', 'N'),
('8', 'DEV', 'richiedente_azione', '69', 'String', 'richiedente_azione', 'N'),
('9', 'DEV', 'richiedente_attivita', '22', 'String', 'richiedente_attivita', 'N'), 
('9', 'DEV', 'richiedente_tipologia', '2', 'String', 'richiedente_tipologia', 'N'),
('9', 'DEV', 'richiedente_azione', '69', 'String', 'richiedente_azione', 'N'),
('10', 'DEV', 'richiedente_attivita', '22', 'String', 'richiedente_attivita', 'N'), 
('10', 'DEV', 'richiedente_tipologia', '2', 'String', 'richiedente_tipologia', 'N'),
('10', 'DEV', 'richiedente_azione', '69', 'String', 'richiedente_azione', 'N'),
('11', 'DEV', 'richiedente_attivita', '22', 'String', 'richiedente_attivita', 'N'), 
('11', 'DEV', 'richiedente_tipologia', '2', 'String', 'richiedente_tipologia', 'N'),
('11', 'DEV', 'richiedente_azione', '69', 'String', 'richiedente_azione', 'N');

/*
modifica tipo base per conferenze ambiente
*/
update tipologia_conferenza_specializzazione set fk_tipologia_conferenza = 2 where codice in ('8','9','10','11');

