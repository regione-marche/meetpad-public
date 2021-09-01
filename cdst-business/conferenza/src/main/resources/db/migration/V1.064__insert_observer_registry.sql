delete from cdst.observer_registry;

-- eventi da protocollare -> 5 - Richiesta unica di Integrazioni (uscita), 6 - Invia Integrazioni (ingresso), 9 - Caricamento verbale riunione (uscita)

INSERT INTO cdst.observer_registry(codice, nome, subrscribed_event_type, class)	VALUES ('SCRITTURA-PROTOCOLLO-PALEO-USCITA', 'Protocollazione Paleo in Uscita', '5', 'PaleoObserverExitListnerInterface');
INSERT INTO cdst.observer_registry(codice, nome, subrscribed_event_type, class)	VALUES ('SCRITTURA-PROTOCOLLO-PALEO-INGRESSO', 'Protocollazione Paleo in ingresso', '6', 'PaleoObserverIncomingListnerInterface');
INSERT INTO cdst.observer_registry(codice, nome, subrscribed_event_type, class)	VALUES ('SCRITTURA-PROTOCOLLO-PALEO-USCITA', 'Protocollazione Paleo in Uscita', '9', 'PaleoObserverExitListnerInterface');