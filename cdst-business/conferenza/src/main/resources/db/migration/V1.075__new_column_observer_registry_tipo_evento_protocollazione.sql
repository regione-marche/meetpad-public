ALTER TABLE cdst.observer_registry ADD COLUMN protocol_event_type character varying(255);

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
    rr.codice,
    rr.protocol_event_type
   FROM cdst.registro_documento rd
     JOIN cdst.documento dd ON rd.fk_documento = dd.id_documento
     JOIN cdst.integ_frontiera_conferenza fc ON fc.fk_conferenza = dd.fk_conferenza
     JOIN cdst.evento ee ON ee.fk_documento = dd.id_documento
     JOIN cdst.tipo_evento te ON te.codice::text = ee.fk_tipo_evento::text
     JOIN cdst.observer_registry rr ON rr.subrscribed_event_type::text = te.codice::text
     LEFT JOIN cdst.protocollo prot ON prot.fk_protocollo = rd.id AND prot.fk_stati_protocollo = 3
  WHERE 1 = 1 AND prot.id IS NULL;
  
UPDATE cdst.observer_registry SET protocol_event_type='20'	WHERE subrscribed_event_type = '2';
UPDATE cdst.observer_registry SET protocol_event_type='22'	WHERE subrscribed_event_type = '5';
UPDATE cdst.observer_registry SET protocol_event_type='21'	WHERE subrscribed_event_type = '6';
UPDATE cdst.observer_registry SET protocol_event_type='23'	WHERE subrscribed_event_type = '9';