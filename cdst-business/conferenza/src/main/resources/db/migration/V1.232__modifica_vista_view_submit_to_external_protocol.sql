CREATE OR REPLACE VIEW cdst.view_submit_to_external_protocol
AS SELECT DISTINCT rd.id AS id_registro,
    dd.fk_conferenza AS id_conferenza,
    fc.id_pratica,
    fc.id_fascicolo,
    rd.rif_esterno AS id_protocollo,
        CASE
            WHEN ee.fk_tipo_evento::text = '26'::text THEN ( SELECT min(eee.id_evento) AS min
               FROM evento eee
              WHERE eee.fk_documento = dd.id_documento)
            ELSE ee.id_evento
        END AS id_evento,
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
     JOIN conferenza c ON c.id_conferenza = dd.fk_conferenza
     LEFT JOIN ( SELECT observer_registry.subrscribed_event_type,
            observer_registry.fk_conferenza_specializzazione::text AS fk_conferenza_specializzazione
           FROM observer_registry
          WHERE observer_registry.fk_conferenza_specializzazione IS NOT NULL) r1 ON r1.subrscribed_event_type::text = te.codice::text AND r1.fk_conferenza_specializzazione = c.fk_tipologia_conferenza_specializzazione::text
     JOIN observer_registry rr ON rr.subrscribed_event_type::text = te.codice::text AND (rr.fk_conferenza_specializzazione IS NULL AND r1.fk_conferenza_specializzazione IS NULL OR rr.fk_conferenza_specializzazione::text = r1.fk_conferenza_specializzazione) AND (r1.subrscribed_event_type::text IS NULL OR rr.subrscribed_event_type::text = r1.subrscribed_event_type::text)
     LEFT JOIN protocollo prot ON prot.fk_protocollo = rd.id AND prot.fk_stati_protocollo in(3,5)
  WHERE 1 = 1 AND prot.id IS NULL AND NOT (EXISTS ( SELECT 1
           FROM documento_firma_multipla fm
          WHERE fm.fk_stato::text <> 'SIGNED'::text AND fm.fk_documento = dd.id_documento AND fm.fk_registro = rd.id));