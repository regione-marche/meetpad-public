CREATE TABLE cdst.sezione_documentazione
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT sezione_documentazione_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_AGGIUNTIVI', 'sezioneDocumentazione.documentiAggiuntivi');
INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_INTERAZIONE', 'sezioneDocumentazione.documentiInterazione');
INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_INDIZIONE', 'sezioneDocumentazione.documentiIndizione');
INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_ACCREDITAMENTO', 'sezioneDocumentazione.documentiAccreditamento');
INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_PREISTRUTTORIA', 'sezioneDocumentazione.documentiPreistruttoria');
INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_CONDIVISI', 'sezioneDocumentazione.documentiCondivisi');
INSERT INTO cdst.sezione_documentazione(codice, descrizione) VALUES ('DOCUMENTI_FIRMA', 'sezioneDocumentazione.documentiFirma');

ALTER TABLE cdst.tipologia_documento ADD COLUMN fk_sezione_documentazione character varying(255);

INSERT INTO cdst.tipologia_documento(codice, descrizione, fk_sezione_documentazione) 
	VALUES ('DOCUMENTI_CONDIVISI', 'tipologiaDocumento.documentiCondivisi', 'DOCUMENTI_CONDIVISI');