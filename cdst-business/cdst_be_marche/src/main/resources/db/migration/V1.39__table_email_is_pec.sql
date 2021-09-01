CREATE TABLE cdst.email_pec
(
    email character varying(255) NOT NULL,
    pec BOOLEAN NOT NULL,
    CONSTRAINT email_pec_pkey PRIMARY KEY (email)
)