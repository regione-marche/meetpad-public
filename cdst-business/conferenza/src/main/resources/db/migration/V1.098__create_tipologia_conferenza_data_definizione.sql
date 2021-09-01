CREATE TABLE cdst.tipologia_conferenza_data_definizione
(
    id_tipologia_conferenza_date_definizione integer NOT NULL,
	fk_tipologia_conferenza character varying(255) NOT NULL,
    campo_data_calcolato character varying(255) NOT NULL,
    flag_obbligatorio boolean,
	offset_gg_lavorativi integer,
	offset_gg_solari integer,
	campo_data_base character varying(255) NOT NULL,
    CONSTRAINT tipo_conf_date_definiz_pkey PRIMARY KEY (id_tipologia_conferenza_date_definizione),
    CONSTRAINT tipo_conf_date_definiz_fk_tipo_conferenza FOREIGN KEY (fk_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);