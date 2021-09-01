CREATE OR REPLACE VIEW cdst.view_submit_to_external_protocol AS
 SELECT DISTINCT rd.id AS id_registro,
    dd.fk_conferenza AS id_conferenza,
    fc.id_pratica,
    ee.id_evento,
    ee.fk_tipo_evento AS id_tipo_evento,
    dd.id_documento,
    te.codice AS subrscribed_event_type,
    te.fk_tipologia_documento AS tipologia_documento,
    rr.id AS id_observer_registry,
    rr.codice
   FROM cdst.registro_documento rd
     JOIN cdst.documento dd ON rd.fk_documento = dd.id_documento
     JOIN cdst.integ_frontiera_conferenza fc ON fc.fk_conferenza = dd.fk_conferenza
     JOIN cdst.evento ee ON ee.fk_documento = dd.id_documento
     JOIN cdst.tipo_evento te ON te.codice::text = ee.fk_tipo_evento::text
     JOIN cdst.observer_registry rr ON rr.subrscribed_event_type::text = te.codice::text
     LEFT JOIN cdst.observer_registry_audit rra ON rra.id_evento = ee.id_evento AND rra.codice_errore IS NULL
  WHERE 1 = 1 AND rra.id_evento IS NULL;

ALTER TABLE cdst.view_submit_to_external_protocol
    OWNER TO cdst;