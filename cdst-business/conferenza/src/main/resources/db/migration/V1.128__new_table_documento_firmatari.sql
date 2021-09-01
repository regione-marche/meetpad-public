CREATE TABLE cdst.documento_firmatario
(
    id_documento integer NOT NULL,
    id_partecipante integer NOT NULL,
    CONSTRAINT id_documento_firmatario FOREIGN KEY (id_documento)
        REFERENCES cdst.documento (id_documento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_partecipante_firmatario FOREIGN KEY (id_partecipante)
        REFERENCES cdst.partecipante (id_partecipante) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);