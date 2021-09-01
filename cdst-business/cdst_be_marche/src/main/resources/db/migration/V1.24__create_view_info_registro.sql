CREATE OR REPLACE VIEW cdst.info_registro AS
 SELECT reg.id AS id_registro,
    ente.codice_fiscale_ente,
    conf.id_conferenza
   FROM cdst.registro_documento reg
     JOIN cdst.documento doc ON doc.id_documento = reg.fk_documento
     JOIN cdst.conferenza conf ON conf.id_conferenza = doc.fk_conferenza
     JOIN cdst.ente ente ON ente.id_ente = conf.fk_ente_procedente;

ALTER TABLE cdst.info_registro
    OWNER TO cdst;

