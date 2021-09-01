DELETE FROM cdst.evento_partecipante where id_evento_partecipante in (6, 7, 8, 9);

INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('9', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('9', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('9', '4') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante) VALUES ('11', '2') ON CONFLICT DO NOTHING;

INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante) VALUES ('2', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante) VALUES ('2', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante) VALUES ('2', '4') ON CONFLICT DO NOTHING;

INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante) VALUES ('12', '1') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante) VALUES ('12', '3') ON CONFLICT DO NOTHING;
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante) VALUES ('12', '4') ON CONFLICT DO NOTHING;