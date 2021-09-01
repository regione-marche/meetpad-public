CREATE SEQUENCE cdst.evento_partecipante_id_evento_partecipante_seq;

ALTER SEQUENCE cdst.evento_partecipante_id_evento_partecipante_seq
    OWNER TO cdst;

CREATE TABLE cdst.evento_partecipante
(
    id_evento_partecipante integer NOT NULL DEFAULT nextval('cdst.evento_partecipante_id_evento_partecipante_seq'::regclass),
    fk_tipo_evento character varying(255),
    fk_ruolo_partecipante character varying(255),
    CONSTRAINT evento_partecipante_pkey PRIMARY KEY (id_evento_partecipante),
    CONSTRAINT fk_tipo_evento FOREIGN KEY (fk_tipo_evento)
        REFERENCES cdst.tipo_evento (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_ruolo_partecipante FOREIGN KEY (fk_ruolo_partecipante)
        REFERENCES cdst.ruolo_partecipante (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('3', '2') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('4', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('4', '4') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('5', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('6', '2') ON CONFLICT DO NOTHING;