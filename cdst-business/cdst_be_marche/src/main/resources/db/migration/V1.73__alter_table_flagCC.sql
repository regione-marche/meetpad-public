INSERT INTO cdst.ruolo_partecipante(codice, descrizione) VALUES ('5', 'ruoloPartecipante.amministrazionePrincipale');

ALTER TABLE cdst.evento_partecipante ADD COLUMN flag_cc boolean;

INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc) VALUES ('5', '3', 'true');
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc) VALUES ('5', '4', 'true');
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc) VALUES ('6', '5', 'true');

UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '1';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '2';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '3';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '4';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '5';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '6';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '7';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '8';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '9';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '10';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '11';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '12';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '13';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '14';
UPDATE cdst.evento_partecipante SET flag_cc = 'false' WHERE id_evento_partecipante = '15';

