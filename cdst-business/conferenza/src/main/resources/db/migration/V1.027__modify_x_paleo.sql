
insert into registro_documento_fonte (codice,descrizione)values('PALEO','da applicativo PALEO');
insert into registro_documento_tipo (codice,descrizione)values('PALEO','Documentale Regione Marche');


insert into registro_documento_fonte (codice,descrizione)values('ALLEGATIPALEO','da applicativo PALEO');
insert into registro_documento_tipo (codice,descrizione)values('ALLEGATIPALEO','Documentale Regione Marche');



CREATE SEQUENCE paleo_registry_allegati_adapter_seq;

CREATE table paleo_registry_allegati_adapter (

    id integer NOT NULL DEFAULT nextval('paleo_registry_allegati_adapter_seq'::regclass),
	FK_PALEO_ADAPTER  integer,    
    ID_ALLEGATO integer,    
    FK_PALEO_DOC integer,
	NOME_ALLEGATO  character varying(255),
	CONSTRAINT paleo_registry_allegati_adapter_pkey PRIMARY KEY (id)
);

alter table integ_frontiera_conferenza add column id_fascicolo character varying(255);

DROP VIEW cdst.view_submit_to_external_protocol;
CREATE OR REPLACE VIEW cdst.view_submit_to_external_protocol AS
 SELECT DISTINCT rd.id AS id_registro,
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
    rr.codice
   FROM registro_documento rd
     JOIN documento dd ON rd.fk_documento = dd.id_documento
     JOIN integ_frontiera_conferenza fc ON fc.fk_conferenza = dd.fk_conferenza
     JOIN evento ee ON ee.fk_documento = dd.id_documento
     JOIN tipo_evento te ON te.codice::text = ee.fk_tipo_evento::text
     JOIN observer_registry rr ON rr.subrscribed_event_type::text = te.codice::text
     LEFT JOIN observer_registry_audit rra ON rra.id_evento = ee.id_evento AND rra.codice_errore IS NULL
  WHERE 1 = 1 AND rra.id_evento IS NULL;
  
  