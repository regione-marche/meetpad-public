CREATE SEQUENCE IF NOT EXISTS cdst.votazione_dati_seq;

CREATE TABLE cdst.votazione_dati (
    id_votazione_dati integer DEFAULT nextval('cdst.votazione_dati_seq'::regclass) NOT null primary key,
    data timestamp without time zone
);

alter table cdst.votazione add column fk_votazione_dati INTEGER REFERENCES votazione_dati (id_votazione_dati)
