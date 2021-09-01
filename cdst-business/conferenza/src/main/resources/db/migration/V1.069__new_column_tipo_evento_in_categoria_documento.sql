ALTER TABLE cdst.categoria_documento ADD COLUMN fk_tipo_evento character varying(255);

INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipo_evento)
	VALUES ('8', 'categoriaDocumento.richiestaIntegrazioni', '3');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipo_evento)
	VALUES ('9', 'categoriaDocumento.parere', '11');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipo_evento)
	VALUES ('10', 'categoriaDocumento.richiestaUnicaIntegrazioni', '5');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipo_evento)
	VALUES ('11', 'categoriaDocumento.documentazioneIntegrativa', '6');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipo_evento)
	VALUES ('12', 'categoriaDocumento.verbaleConferenza', '9');
	
INSERT INTO cdst.categoria_documento(
	codice, descrizione, fk_tipo_evento)
	VALUES ('13', 'categoriaDocumento.determinazioneFinale', '12');