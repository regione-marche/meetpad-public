
CREATE TABLE cdst.permesso_azione
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT permesso_azione_pkey PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

INSERT INTO cdst.permesso_azione VALUES ('CONFERENZA_EDIT', 'modifica conferenza');
INSERT INTO cdst.permesso_azione VALUES ('CONFERENZA_CLONE', 'clonazione conferenza');
INSERT INTO cdst.permesso_azione VALUES ('CONFERENZA_DELETE', 'elimina conferenza');
INSERT INTO cdst.permesso_azione VALUES ('CONFERENZA_SKYPE', 'chiamata skype per conferenza');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_CHIUSURA_CONFERENZA', 'chiusura conferenza');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_CHIUSURA_INTEGRAZIONI', 'chiusura integrazioni');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_RICHIESTA_UNICA_INTEGRAZIONI', 'richiesta unica integrazioni');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_COMUNICAZIONE_GENERICA', 'comunicazione generica');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_TRASMISSIONE_INTEGRAZIONI_PROTOCOLLATE', 'trasmissione integrazioni protocollate');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_DETERMINAZIONE_FINALE', 'determinazione finale');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE', 'caricamento verbale riunione');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_RICHIESTA_INTEGRAZIONI', 'richiesta integrazioni');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_ESPRESSIONE_PARERI', 'espressione pareri');
INSERT INTO cdst.permesso_azione VALUES ('INSERT_EVENTO_INVIA_INTEGRAZIONI', 'invia integrazioni');


-------------------------------------------------------------------------------------------------------
CREATE TABLE cdst.permesso_ruolo
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT permesso_ruolo_pk PRIMARY KEY (codice)
)
WITH (
    OIDS = FALSE
);

INSERT INTO cdst.permesso_ruolo VALUES ('NON_DEFINITO', 'non definito');
INSERT INTO cdst.permesso_ruolo VALUES ('RICHIEDENTE', 'richiedente');
INSERT INTO cdst.permesso_ruolo VALUES ('RESPONSABILE', 'responsabile');
INSERT INTO cdst.permesso_ruolo VALUES ('CREATORE', 'creatore');
INSERT INTO cdst.permesso_ruolo VALUES ('PARTECIPANTE', 'partecipante');
INSERT INTO cdst.permesso_ruolo VALUES ('AMMINISTRATORE', 'amministratore');


-------------------------------------------------------------------------------------------------------
CREATE SEQUENCE cdst.permesso_id_permesso_seq;

CREATE TABLE cdst.permesso
(
    id_permesso integer NOT NULL DEFAULT nextval('cdst.permesso_id_permesso_seq'::regclass),
    fk_permesso_ruolo character varying(255) COLLATE pg_catalog."default" NOT NULL,
    fk_permesso_azione character varying(255) COLLATE pg_catalog."default" NOT NULL,
    permission_strategy character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT permesso_pkey PRIMARY KEY (id_permesso),
    CONSTRAINT permesso_fk_permesso_azione FOREIGN KEY (fk_permesso_azione)
        REFERENCES cdst.permesso_azione (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT permesso_fk_permesso_ruolo FOREIGN KEY (fk_permesso_ruolo)
        REFERENCES cdst.permesso_ruolo (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);


