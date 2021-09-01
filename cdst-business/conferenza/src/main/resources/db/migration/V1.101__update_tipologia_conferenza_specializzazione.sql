UPDATE cdst.tipologia_conferenza_specializzazione SET descrizione='tipologiaConferenzaSpec.BULDecisoriaSimultanea' WHERE codice = '4';
UPDATE cdst.tipologia_conferenza_specializzazione SET descrizione='tipologiaConferenzaSpec.BUListruttoriaSimultanea' WHERE codice = '5';
UPDATE cdst.tipologia_conferenza_specializzazione SET flag_autoaccreditamento=true	WHERE codice in ('5');
UPDATE cdst.tipologia_conferenza_specializzazione SET fk_azione = '179' WHERE codice in ('4', '5');