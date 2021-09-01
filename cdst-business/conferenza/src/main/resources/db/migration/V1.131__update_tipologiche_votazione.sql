UPDATE cdst.votazione_criterio SET DESCRIZIONE = 'votazionecriterio.evento' WHERE codice = 'EVENTO';
UPDATE cdst.votazione_criterio SET DESCRIZIONE = 'votazionecriterio.maggioranza' WHERE codice = 'MAGGIORANZA';
UPDATE cdst.votazione_criterio SET DESCRIZIONE = 'votazionecriterio.unanimita' WHERE codice = 'UNANIMITA';
UPDATE cdst.votazione_criterio SET DESCRIZIONE = 'votazionecriterio.valutazione' WHERE codice = 'VALUTAZIONE';

UPDATE cdst.votazione_esito SET DESCRIZIONE = 'votazioneesito.approvata' WHERE codice = 'APPROVATA';
UPDATE cdst.votazione_esito SET DESCRIZIONE = 'votazioneesito.non_approvata' WHERE codice = 'NON_APPROVATA';

UPDATE cdst.votazione_stato SET DESCRIZIONE = 'votazionestato.preparazione' WHERE codice = 'PREPARAZIONE';
UPDATE cdst.votazione_stato SET DESCRIZIONE = 'votazionestato.in_corso' WHERE codice = 'IN_CORSO';
UPDATE cdst.votazione_stato SET DESCRIZIONE = 'votazionestato.terminata' WHERE codice = 'TERMINATA';
UPDATE cdst.votazione_stato SET DESCRIZIONE = 'votazionestato.esito_impostato' WHERE codice = 'ESITO_IMPOSTATO';

UPDATE cdst.votazione_tipo SET DESCRIZIONE = 'votazionetipo.votazione' WHERE codice = 'VOTAZIONE';
UPDATE cdst.votazione_tipo SET DESCRIZIONE = 'votazionetipo.calendarizzazione' WHERE codice = 'CALENDARIZZAZIONE';
UPDATE cdst.votazione_tipo SET DESCRIZIONE = 'votazionetipo.rilevazione_presenze' WHERE codice = 'RILEVAZIONE_PRESENZE';