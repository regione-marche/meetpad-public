INSERT INTO cdst.tipologia_documento(codice, descrizione) VALUES ('COMUNICAZIONE_GENERICA', 'tipologiaDocumento.comunicazioneGenerica');

UPDATE cdst.tipo_evento SET fk_tipologia_documento='COMUNICAZIONE_GENERICA'	WHERE codice='7';
