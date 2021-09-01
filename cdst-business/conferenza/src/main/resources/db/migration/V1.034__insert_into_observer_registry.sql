insert into observer_registry (id,codice,nome,subrscribed_event_type)values(1,'SCRITTURA-PROTOCOLLO-PALEO-USCITA','Protocollazione Paleo in Uscita','6');
update cdst.observer_registry set class='PaleoObserverExitListnerInterface' where id in (1) ;


insert into observer_registry (id,codice,nome,subrscribed_event_type)values(2,'SCRITTURA-PROTOCOLLO-PALEO-INGRESSO','Protocollazione Paleo in ingresso','12');
update cdst.observer_registry set class='PaleoObserverIncomingListnerInterface' where id in (2) ;
