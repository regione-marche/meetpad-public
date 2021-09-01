DROP TABLE cdst.integ_frontiera_conferenza;

CREATE TABLE cdst.integ_frontiera_conferenza
(
    id_integ_frontiera_conferenza integer NOT NULL DEFAULT nextval('cdst.integ_frontiera_conferenza_id_integ_frontiera_conferenza_seq'::regclass),
    id_pratica integer,
    fk_conferenza integer,
    CONSTRAINT integ_frontiera_conferenza_pkey PRIMARY KEY (id_integ_frontiera_conferenza),
    CONSTRAINT fk_conferenza FOREIGN KEY (fk_conferenza)
        REFERENCES cdst.conferenza (id_conferenza) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);