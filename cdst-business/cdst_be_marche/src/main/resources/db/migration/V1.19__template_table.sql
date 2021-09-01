CREATE SEQUENCE cdst.template_id_template_seq;

CREATE TABLE cdst.template
(
	id_template integer NOT NULL DEFAULT nextval('cdst.template_id_template_seq'::regclass),
    nome_template character varying(255),
    fk_tipologia_conferenza character varying(255),
    fk_tipo_evento character varying(255),
    CONSTRAINT template_pkey PRIMARY KEY (id_template),
    CONSTRAINT fk_tipologia_conferenza FOREIGN KEY (fk_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_tipo_evento FOREIGN KEY (fk_tipo_evento)
    	REFERENCES cdst.tipo_evento (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_indizione', '1', '2') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_indizione', '2', '2') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_indizione', '3', '2') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_indizione', '4', '2') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_istruttoria', '5', '2') ON CONFLICT DO NOTHING;

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_integrazione', '1', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_integrazione', '2', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_integrazione', '3', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_integrazione', '4', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_integrazione', '5', '3') ON CONFLICT DO NOTHING;
