CREATE SEQUENCE cdst.integ_frontiera_conferenza_id_integ_frontiera_conferenza_seq;

CREATE TABLE integ_frontiera_conferenza
(
    id_integ_frontiera_conferenza integer,
    id_pratica integer,
    fk_conferenza integer,
    CONSTRAINT integ_frontiera_conferenza_pkey PRIMARY KEY (id_integ_frontiera_conferenza),
    CONSTRAINT fk_conferenza FOREIGN KEY (fk_conferenza)
        REFERENCES cdst.conferenza (id_conferenza) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
