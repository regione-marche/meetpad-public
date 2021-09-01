CREATE SEQUENCE cdst.impresa_id_impresa_seq;

ALTER SEQUENCE cdst.impresa_id_impresa_seq
    OWNER TO cdst;

CREATE TABLE cdst.impresa
(
    id_impresa integer NOT NULL DEFAULT nextval('cdst.impresa_id_impresa_seq'::regclass),
    denominazione character varying(255),
    codice_fiscale character varying(255),
    partita_iva character varying(255),
    indirizzo character varying(255),
    fk_forma_giuridica character varying(255),    
    fk_regione character varying(255),
    fk_provincia character varying(255),
    fk_comune character varying(255),
    CONSTRAINT impresa_pkey PRIMARY KEY (id_impresa),
    CONSTRAINT fk_forma_giuridica FOREIGN KEY (fk_forma_giuridica)
        REFERENCES cdst.forma_giuridica (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_regione FOREIGN KEY (fk_regione)
        REFERENCES cdst.regione (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_provincia FOREIGN KEY (fk_provincia)
        REFERENCES cdst.provincia (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_comune FOREIGN KEY (fk_comune)
        REFERENCES cdst.comune (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE cdst.conferenza
ADD fk_impresa integer,
ADD CONSTRAINT fk_impresa FOREIGN KEY (fk_impresa)
        REFERENCES cdst.impresa (id_impresa);