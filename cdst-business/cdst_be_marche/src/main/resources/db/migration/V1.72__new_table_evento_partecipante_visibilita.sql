CREATE TABLE cdst.evento_partecipante_visibilita
(
    id_evento integer NOT NULL,
    id_partecipante integer NOT NULL,
    CONSTRAINT id_evento FOREIGN KEY (id_evento)
        REFERENCES cdst.evento (id_evento) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_partecipante FOREIGN KEY (id_partecipante)
        REFERENCES cdst.partecipante (id_partecipante) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);