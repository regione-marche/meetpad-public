ALTER TABLE cdst.ente
ADD codice_ipa character varying(255);

ALTER TABLE cdst.ente
ADD fk_regione character varying(255);

ALTER TABLE cdst.ente
ADD fk_provincia character varying(255);

ALTER TABLE cdst.ente
ADD fk_comune character varying(255);

ALTER TABLE cdst.ente
ADD fk_tipologia_istat character varying(255);

ALTER TABLE cdst.ente
ADD fk_tipologia_amministrativa character varying(255);

Update cdst.ente set codice_fiscale_ente = LPAD(codice_fiscale_ente, '11', '0' ) 
where length(codice_fiscale_ente)<'11';

Update cdst.partecipante set codice_fiscale_ente_competente = LPAD(codice_fiscale_ente_competente, '11', '0' ) 
where length(codice_fiscale_ente_competente)<'11';