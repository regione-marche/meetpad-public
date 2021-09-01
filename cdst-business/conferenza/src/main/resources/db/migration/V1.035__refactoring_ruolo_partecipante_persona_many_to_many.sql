ALTER TABLE cdst.ruolo_persona DROP COLUMN fk_ruolo_partecipante;

CREATE TABLE cdst.ruolo_partecipante_ruolo_persona
(
    codice_ruolo_partecipante character varying(255) NOT NULL,
    codice_ruolo_persona character varying(255) NOT NULL,
    CONSTRAINT codice_ruolo_partecipante FOREIGN KEY (codice_ruolo_partecipante)
        REFERENCES cdst.ruolo_partecipante (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT codice_ruolo_persona FOREIGN KEY (codice_ruolo_persona)
        REFERENCES cdst.ruolo_persona (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('1', '1');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('2', '2');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('3', '3');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('3', '4');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('3', '5');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('4', '3');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('4', '4');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('4', '5');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('5', '3');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('5', '4');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('5', '5');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('6', '3');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('6', '4');
INSERT INTO cdst.ruolo_partecipante_ruolo_persona(
	codice_ruolo_partecipante, codice_ruolo_persona)
	VALUES ('6', '5');