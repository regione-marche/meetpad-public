INSERT INTO cdst.votazione_criterio(codice, descrizione) VALUES ('EVENTO', 'Evento');
INSERT INTO cdst.votazione_criterio(codice, descrizione) VALUES ('MAGGIORANZA', 'Maggiornaza dei voti');
INSERT INTO cdst.votazione_criterio(codice, descrizione) VALUES ('UNANIMITA', 'Unanimita dei voti');
INSERT INTO cdst.votazione_criterio(codice, descrizione) VALUES ('VALUTAZIONE', 'Valutazione del Responsabile');

INSERT INTO cdst.votazione_esito(codice, descrizione) VALUES ('APPROVATA', 'Votazione approvata');
INSERT INTO cdst.votazione_esito(codice, descrizione) VALUES ('NON_APPROVATA', 'Votazione non approvata');

INSERT INTO cdst.votazione_stato(codice, descrizione) VALUES ('PREPARAZIONE', 'In preparazione');
INSERT INTO cdst.votazione_stato(codice, descrizione) VALUES ('IN_CORSO', 'Votazione in corso');
INSERT INTO cdst.votazione_stato(codice, descrizione) VALUES ('TERMINATA', 'Votazione terminata');
INSERT INTO cdst.votazione_stato(codice, descrizione) VALUES ('ESITO_IMPOSTATO', 'Esito impostato');

INSERT INTO cdst.votazione_tipo(codice, descrizione) VALUES ('VOTAZIONE', 'Votazione');
INSERT INTO cdst.votazione_tipo(codice, descrizione) VALUES ('CALENDARIZZAZIONE', 'Calendarizzazione');
INSERT INTO cdst.votazione_tipo(codice, descrizione) VALUES ('RILEVAZIONE_PRESENZE', 'Rilevazione presenze');