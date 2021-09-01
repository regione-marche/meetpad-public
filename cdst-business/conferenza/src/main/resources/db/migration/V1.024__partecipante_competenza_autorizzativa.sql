CREATE TABLE cdst.partecipante_competenza_autorizzativa
(
	id_partecipante integer NOT NULL,
    codice_competenza_autorizzativa character varying(255) NOT NULL,
    CONSTRAINT codice_competenza_autorizzativa FOREIGN KEY (codice_competenza_autorizzativa)
        REFERENCES cdst.competenza_autorizzativa (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_partecipante FOREIGN KEY (id_partecipante)
        REFERENCES cdst.partecipante (id_partecipante) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);