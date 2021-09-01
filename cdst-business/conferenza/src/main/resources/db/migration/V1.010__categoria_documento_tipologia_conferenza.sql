DROP TABLE cdst.tipologia_documento_tipologia_conferenza;

CREATE TABLE cdst.categoria_documento_tipologia_conferenza
(
    codice_categoria_documento character varying(255) NOT NULL,
    codice_tipologia_conferenza character varying(255) NOT NULL,
    CONSTRAINT codice_categoria_documento FOREIGN KEY (codice_categoria_documento)
        REFERENCES cdst.categoria_documento (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT codice_tipologia_conferenza FOREIGN KEY (codice_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);