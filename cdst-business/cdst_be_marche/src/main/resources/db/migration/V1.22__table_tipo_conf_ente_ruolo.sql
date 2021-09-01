CREATE SEQUENCE cdst.rubrica_partecipanti_id_rubrica_partecipanti_seq;

ALTER SEQUENCE cdst.rubrica_partecipanti_id_rubrica_partecipanti_seq
    OWNER TO cdst;

CREATE TABLE cdst.rubrica_partecipanti
(
    id_rubrica_partecipanti integer NOT NULL DEFAULT nextval('cdst.rubrica_partecipanti_id_rubrica_partecipanti_seq'::regclass),
    fk_tipologia_conferenza character varying(255),
    fk_ente integer,
    fk_ruolo_partecipante character varying(255),
    CONSTRAINT rubrica_partecipanti_pkey PRIMARY KEY (id_rubrica_partecipanti),
    CONSTRAINT fk_tipologia_conferenza FOREIGN KEY (fk_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_ente FOREIGN KEY (fk_ente)
    	REFERENCES cdst.ente (id_ente) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_ruolo_partecipante FOREIGN KEY (fk_ruolo_partecipante)
        REFERENCES cdst.ruolo_partecipante (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE cdst.rubrica_richiedenti_id_rubrica_richiedenti_seq;

ALTER SEQUENCE cdst.rubrica_richiedenti_id_rubrica_richiedenti_seq
    OWNER TO cdst;

CREATE TABLE cdst.rubrica_richiedenti
(
    id_rubrica_richiedenti integer NOT NULL DEFAULT nextval('cdst.rubrica_richiedenti_id_rubrica_richiedenti_seq'::regclass),
    fk_tipologia_conferenza character varying(255),
    fk_persona integer,
    CONSTRAINT rubrica_richiedenti_pkey PRIMARY KEY (id_rubrica_richiedenti),
    CONSTRAINT fk_tipologia_conferenza FOREIGN KEY (fk_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_persona FOREIGN KEY (fk_persona)
    	REFERENCES cdst.persona (id_persona) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);