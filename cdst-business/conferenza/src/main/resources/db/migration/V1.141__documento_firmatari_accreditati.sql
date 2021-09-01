DROP TABLE cdst.documento_firmatario;

CREATE TABLE cdst.documento_firmatario
(
    id_documento integer NOT NULL,
    id_accreditamento integer NOT NULL,
    CONSTRAINT id_documento_firmatario FOREIGN KEY (id_documento)
        REFERENCES cdst.documento (id_documento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_accreditamento_firmatario FOREIGN KEY (id_accreditamento)
        REFERENCES cdst.accreditamento (id_accreditamento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)