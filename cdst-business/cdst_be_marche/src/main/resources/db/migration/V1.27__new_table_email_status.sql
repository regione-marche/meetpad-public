CREATE TABLE cdst.email_status
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT email_status_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.email_status (codice, descrizione) VALUES ('INOLTRATO', 'stato.email.inoltrato') ON CONFLICT DO NOTHING;
INSERT INTO cdst.email_status (codice, descrizione) VALUES ('ACCETTAZIONE', 'stato.email.accettazione') ON CONFLICT DO NOTHING;
INSERT INTO cdst.email_status (codice, descrizione) VALUES ('CONSEGNA', 'stato.email.consegna') ON CONFLICT DO NOTHING;
INSERT INTO cdst.email_status (codice, descrizione) VALUES ('ERRORETRASMISSIONE', 'stato.email.errroreTrasmissione') ON CONFLICT DO NOTHING;
INSERT INTO cdst.email_status (codice, descrizione) VALUES ('AVVISODINONACCETTAZIONE', 'stato.email.avvisoDiNonAccettazione') ON CONFLICT DO NOTHING;