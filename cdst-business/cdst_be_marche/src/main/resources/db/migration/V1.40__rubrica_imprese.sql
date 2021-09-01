CREATE SEQUENCE cdst.rubrica_imprese_id_rubrica_imprese_seq;

ALTER SEQUENCE cdst.rubrica_imprese_id_rubrica_imprese_seq
    OWNER TO cdst;

CREATE TABLE cdst.rubrica_imprese
(
    id_rubrica_imprese integer NOT NULL DEFAULT nextval('cdst.rubrica_imprese_id_rubrica_imprese_seq'::regclass),
    fk_tipologia_conferenza character varying(255),
    fk_impresa integer,
    CONSTRAINT rubrica_imprese_pkey PRIMARY KEY (id_rubrica_imprese),
    CONSTRAINT fk_tipologia_conferenza FOREIGN KEY (fk_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_impresa FOREIGN KEY (fk_impresa)
    	REFERENCES cdst.impresa (id_impresa) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);