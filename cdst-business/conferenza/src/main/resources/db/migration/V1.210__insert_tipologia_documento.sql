CREATE SEQUENCE IF NOT EXISTS cdst.documento_attachment_seq;

-- DROP TABLE cdst.documento_attachment;

CREATE TABLE cdst.documento_attachment (
	id_documento integer DEFAULT nextval('cdst.documento_attachment_seq'::regclass) NOT null primary key,
	audit_id_usr_crt varchar(255) NOT NULL,
	audit_crt_time timestamp NOT NULL,
	audit_mod_time timestamp NULL,
	audit_id_usr_mod varchar(255) NULL,
	data_fine timestamp NULL,
	nome varchar(255) NULL,
	visibilita_ristretta bool NULL,
	fk_categoria_documento varchar(255) NULL,
	fk_accreditamento int4 NULL,
	fk_tipologia_documento varchar(255) NULL,
	fk_conferenza int4 NULL,
	numero_protocollo varchar(255) NULL,
	data_protocollo timestamp NULL,
	numero_inventario varchar(255) NULL,
	competenza_territoriale varchar(255) NULL,
	data_riunione timestamp NULL,
	file_conforme bool NULL DEFAULT false,
	md5 varchar(100) NULL,
	fk_documento int NULL
);

-- Permissions

ALTER TABLE cdst.documento_attachment OWNER TO cdst;
GRANT ALL ON TABLE cdst.documento_attachment TO cdst;


INSERT INTO cdst.tipologia_documento
(codice, descrizione, fk_sezione_documentazione, flag_upload_consolle)
VALUES('DOCUMENT_ATTACHED', 'tipologiaDocumento.documentazioneAllegata', 'DOCUMENTS_ATTACHED', false);
