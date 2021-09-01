INSERT INTO cdst.tipologia_documento
(codice, descrizione, fk_sezione_documentazione, flag_upload_consolle)
VALUES('APPUNTI', 'tipologiaDocumento.appunti', 'DOCUMENTI_CONDIVISI', false);

INSERT INTO cdst.categoria_documento
(codice, descrizione, fk_tipologia_documento)
VALUES('18', 'categoriaDocumento.appunti', 'APPUNTI');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('6', 'appunti', '1');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('7', 'appunti', '2');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('8', 'appunti', '3');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('9', 'appunti', '4');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('10', 'appunti', '5');
