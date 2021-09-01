UPDATE cdst.ente SET descrizione_ente = 'Regione Marche - P.F. Tutela del Territorio di Ancona e Gestione del patrimonio' WHERE codice_ufficio='HZRSSP';
UPDATE cdst.ente SET descrizione_ente = 'Regione Marche - P.F. Tutela del territorio di Ascoli Piceno' WHERE codice_ufficio='M9MI1Y';
UPDATE cdst.ente SET descrizione_ente = 'Regione Marche - P.F. Tutela del territorio di Fermo' WHERE codice_ufficio='GBEMB4';
UPDATE cdst.ente SET descrizione_ente = 'Regione Marche - P.F. Tutela del territorio di Macerata' WHERE codice_ufficio='I1KMIV';
UPDATE cdst.ente SET descrizione_ente = 'Regione Marche - P.F. Tutela del territorio di Pesaro-Urbino' WHERE codice_ufficio='WLHGL8';

INSERT INTO cdst.ruolo_partecipante(codice, descrizione) VALUES ('5', 'ruoloPartecipante.perPareriInterni');
INSERT INTO cdst.ruolo_partecipante(codice, descrizione) VALUES ('6', 'ruoloPartecipante.perConoscenza');

CREATE TABLE cdst.rubrica_partecipante_competenza_autorizzativa
(
	id_rubrica_partecipanti integer NOT NULL,
    codice_competenza_autorizzativa character varying(255) NOT NULL,
    CONSTRAINT codice_competenza_autorizzativa FOREIGN KEY (codice_competenza_autorizzativa)
        REFERENCES cdst.competenza_autorizzativa (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT id_rubrica_partecipanti FOREIGN KEY (id_rubrica_partecipanti)
        REFERENCES cdst.rubrica_partecipanti (id_rubrica_partecipanti) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

UPDATE cdst.rubrica_partecipanti SET fk_ruolo_partecipante='4' WHERE fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='07516911000');
UPDATE cdst.rubrica_partecipanti SET fk_ruolo_partecipante='6' WHERE fk_ente=(SELECT id_ente FROM cdst.ente WHERE codice_fiscale_ente='80230390587');
