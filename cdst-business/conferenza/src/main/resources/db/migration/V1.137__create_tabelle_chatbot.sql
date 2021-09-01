CREATE SEQUENCE IF NOT EXISTS cdst.domande_seq;
CREATE SEQUENCE IF NOT EXISTS cdst.richieste_seq;

CREATE TABLE IF NOT EXISTS domande (
  id integer NOT NULL DEFAULT nextval('domande_seq'::regclass),
  domanda  character varying(255),
  risposta  character varying(2500)
);

CREATE TABLE IF NOT EXISTS richieste (
  Id integer NOT NULL DEFAULT nextval('richieste_seq'::regclass),
  DOMANDA  character varying(255) ,
  EMAIL  character varying(255) ,
  DATA Timestamp 
);