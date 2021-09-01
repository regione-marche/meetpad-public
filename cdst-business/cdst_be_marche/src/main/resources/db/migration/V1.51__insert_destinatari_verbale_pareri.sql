INSERT INTO cdst.evento_partecipante (id_evento_partecipante, fk_tipo_evento, fk_ruolo_partecipante) VALUES ('6', '9', '1');
INSERT INTO cdst.evento_partecipante (id_evento_partecipante, fk_tipo_evento, fk_ruolo_partecipante) VALUES ('7', '9', '3');
INSERT INTO cdst.evento_partecipante (id_evento_partecipante, fk_tipo_evento, fk_ruolo_partecipante) VALUES ('8', '9', '4');
INSERT INTO cdst.evento_partecipante (id_evento_partecipante, fk_tipo_evento, fk_ruolo_partecipante) VALUES ('9', '11', '2');

ALTER TABLE cdst.tipo_evento ADD COLUMN flag_invio_email boolean;

UPDATE cdst.tipo_evento
	SET flag_invio_email=true
	WHERE codice in ('2', '3', '4', '5', '6', '9', '11');

UPDATE cdst.tipo_evento
	SET flag_invio_email=false
	WHERE codice in ('1', '7', '8', '10');  