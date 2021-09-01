-- View: cdst.visibilita_documento

-- DROP VIEW cdst.visibilita_documento;

CREATE OR REPLACE VIEW cdst.visibilita_documento AS
 SELECT (dp.id_documento || '-'::text) || dp.id_partecipante AS id,
 		dp.id_documento,
    	dp.id_partecipante
    
    FROM cdst.documento_partecipante dp;

ALTER TABLE cdst.visibilita_documento
    OWNER TO cdst;