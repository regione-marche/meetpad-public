ALTER TABLE cdst.tipo_evento ADD COLUMN fk_tipologia_documento character varying(255);

ALTER TABLE cdst.tipo_evento
ADD CONSTRAINT fk_tipologia_documento FOREIGN KEY (fk_tipologia_documento)
        REFERENCES cdst.tipologia_documento (codice);
        
UPDATE cdst.tipo_evento
	SET fk_tipologia_documento = 'INTERAZIONI'
	WHERE codice in ('3','4','5','6');
	
UPDATE cdst.tipo_evento
	SET fk_tipologia_documento = 'VERBALE_RIUNIONE'
	WHERE codice = '9';
	
UPDATE cdst.tipo_evento
	SET fk_tipologia_documento = 'DOCUMENTO_INDIZIONE'
	WHERE codice = '2';