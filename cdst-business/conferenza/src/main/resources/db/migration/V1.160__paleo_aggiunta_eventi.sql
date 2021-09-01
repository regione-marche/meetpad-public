insert into tipo_protocollo (id,nome)
select 3,'PALEO'
where NOT EXISTS (
  SELECT id FROM tipo_protocollo WHERE id=3
);


INSERT INTO observer_registry (id,codice,nome,subrscribed_event_type,"class",protocol_event_type) 
select NULL,'SCRITTURA-PROTOCOLLO-PALEO-INGRESSO','Protocollazione Paleo in ingresso','7','PaleoObserverIncomingListnerInterface','3'
where NOT EXISTS (
  SELECT codice FROM observer_registry WHERE subrscribed_event_type='7' and  codice = 'SCRITTURA-PROTOCOLLO-PALEO-INGRESSO'
);



INSERT INTO observer_registry (id,codice,nome,subrscribed_event_type,"class",protocol_event_type) 
SELECT NULL,'SCRITTURA-PROTOCOLLO-PALEO-INGRESSO','Protocollazione Paleo in ingresso','2','PaleoObserverIncomingListnerInterface','3'
where NOT EXISTS (
  SELECT codice FROM observer_registry WHERE subrscribed_event_type='2' and  codice = 'SCRITTURA-PROTOCOLLO-PALEO-INGRESSO'
);