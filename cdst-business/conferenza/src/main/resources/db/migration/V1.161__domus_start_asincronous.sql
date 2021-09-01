--//=============================================================================
--//PARARAMETRI ATTIVAZIONE INTEGRAZIONE
--//=============================================================================
insert into tipo_protocollo (id,nome)
select 4,'DOMUS'
where NOT EXISTS (
  SELECT id FROM tipo_protocollo WHERE id=4
);

insert into domus_comune (id,codice_comune)
select 1,'043007'
where NOT EXISTS (
  SELECT id FROM domus_comune WHERE id=1
);


INSERT INTO observer_registry (id,codice,nome,subrscribed_event_type,"class",protocol_event_type) 
select NULL,'DOMUS','Creazione Conferenze da Domus',null,'DomusListnerInterface','4'
where NOT EXISTS (
  SELECT codice FROM observer_registry WHERE codice = 'DOMUS'
);