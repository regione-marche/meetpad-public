DELETE FROM cdst.template where id_template in (11, 12, 13, 14, 15);

INSERT INTO cdst.template (nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_verbale_riunione', '1', '9') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template (nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_verbale_riunione', '2', '9') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template (nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_verbale_riunione', '3', '9') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template (nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_verbale_riunione', '4', '9') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template (nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_verbale_riunione', '5', '9') ON CONFLICT DO NOTHING;

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '1', '5') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '2', '5') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '3', '5') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '4', '5') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '5', '5') ON CONFLICT DO NOTHING;

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '1', '6') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '2', '6') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '3', '6') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '4', '6') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '5', '6') ON CONFLICT DO NOTHING;

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '1', '7') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '2', '7') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '3', '7') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '4', '7') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '5', '7') ON CONFLICT DO NOTHING;

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '1', '11') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '2', '11') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '3', '11') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '4', '11') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '5', '11') ON CONFLICT DO NOTHING;

INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '1', '12') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '2', '12') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '3', '12') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '4', '12') ON CONFLICT DO NOTHING;
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento) VALUES ('template_corpo', '5', '12') ON CONFLICT DO NOTHING;

UPDATE cdst.template SET nome_template='template_corpo' WHERE fk_tipo_evento = '3';