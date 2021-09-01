CREATE TABLE cdst.evento_calendario_data_obbligatoria
(
    id_evento_calendario_data_obbligatoria integer,
    fk_tipologia_conferenza character varying(255) COLLATE pg_catalog."default",
    fk_eventi_calendario character varying(255) COLLATE pg_catalog."default",
    flag_obbligatorio boolean,
    CONSTRAINT evento_calendario_data_obbligatoria_pkey PRIMARY KEY (id_evento_calendario_data_obbligatoria),
    CONSTRAINT fk_tipologia_conferenza FOREIGN KEY (fk_tipologia_conferenza)
    	REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE,
    CONSTRAINT fk_eventi_calendario FOREIGN KEY (fk_eventi_calendario)
        REFERENCES cdst.eventi_calendario (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

DO
$do$
BEGIN

IF EXISTS (SELECT 1 from cdst.tipologia_conferenza where codice = '1') THEN
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('1', '1', '1', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('2', '1', '2', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('3', '1', '3', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('4', '1', '4', 'true') ON CONFLICT DO NOTHING;
END IF;

IF EXISTS (SELECT 1 from cdst.tipologia_conferenza where codice = '2') THEN	
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('5', '2', '1', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('6', '2', '2', 'false') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('7', '2', '3', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('8', '2', '4', 'true') ON CONFLICT DO NOTHING;
END IF;

IF EXISTS (SELECT 1 from cdst.tipologia_conferenza where codice = '3') THEN	
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('9', '3', '1', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('10', '3', '2', 'false') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('11', '3', '3', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('12', '3', '4', 'true') ON CONFLICT DO NOTHING;
END IF;

IF EXISTS (SELECT 1 from cdst.tipologia_conferenza where codice = '5') THEN	
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('13', '5', '1', 'true') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('14', '5', '2', 'false') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('15', '5', '3', 'false') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_calendario_data_obbligatoria (id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio) 
	VALUES ('16', '5', '4', 'true') ON CONFLICT DO NOTHING;
END IF;
 
END
$do$;