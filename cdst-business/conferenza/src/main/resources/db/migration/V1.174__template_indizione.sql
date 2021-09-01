--#==============================================================================================
--# Query per FLYWAY


--#==============================================================================================
update template set nome_template_download='template_ord19_indizione_regionale' 
	where fk_tipologia_conferenza_specializzazione='3' and fk_tipo_evento='2';

update template set nome_template_download='verbale_ord19_regionale' 
	where fk_tipologia_conferenza_specializzazione='3' and fk_tipo_evento='9';


--#==============================================================================================
-- Drop table

-- DROP TABLE cdst.selezione_amministrazione_procedente;

CREATE TABLE cdst.selezione_amministrazione_procedente (
	id_selezione_amministrazione_procedente serial NOT NULL,
	fk_ente int4 NULL,
	fk_utente int4 NULL,
	CONSTRAINT selezione_amministrazione_procedente_pkey PRIMARY KEY (id_selezione_amministrazione_procedente),
	CONSTRAINT fk8jg5rc9g3mao6t5g1cpb42te1 FOREIGN KEY (fk_utente) REFERENCES utente(id_utente),
	CONSTRAINT fkeq519d7c3efdf4dmloktym6yt FOREIGN KEY (fk_ente) REFERENCES ente(id_ente)
);


-- Permissions

ALTER TABLE cdst.selezione_amministrazione_procedente OWNER TO cdst;
GRANT ALL ON TABLE cdst.selezione_amministrazione_procedente TO cdst;

-- Drop table

-- DROP TABLE cdst.selezione_tipologia_conferenza;

CREATE TABLE cdst.selezione_tipologia_conferenza (
	id_selezione_tipologia_conferenza serial NOT NULL,
	fk_codice varchar(255) NULL,
	fk_utente int4 NULL,
	CONSTRAINT selezione_tipologia_conferenza_pkey PRIMARY KEY (id_selezione_tipologia_conferenza),
	CONSTRAINT fk8jg5rc9g4mao6t5g1cpb42te1 FOREIGN KEY (fk_utente) REFERENCES utente(id_utente),
	CONSTRAINT fkeq519d7c4efdf4dmloktym6yt FOREIGN KEY (fk_codice) REFERENCES tipologia_conferenza_specializzazione(codice)
);


-- Permissions

ALTER TABLE cdst.selezione_tipologia_conferenza OWNER TO cdst;
GRANT ALL ON TABLE cdst.selezione_tipologia_conferenza TO cdst;

--#==============================================================================================
--#
--#==============================================================================================
