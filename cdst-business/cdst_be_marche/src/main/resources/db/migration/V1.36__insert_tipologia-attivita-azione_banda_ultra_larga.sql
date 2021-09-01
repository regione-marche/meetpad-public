INSERT INTO cdst.tipologia_pratica (codice, descrizione) VALUES ('4', 'Trasformazione edilizia ed urbanistica del territorio');

INSERT INTO cdst.attivita (codice, descrizione, fk_tipologia_pratica, descrizione_lunga) VALUES ('30', 'Infrastrutture ed impianti anche per pubblici servizi', '4', 'Infrastrutture ed impianti anche per pubblici servizi');

INSERT INTO cdst.azione (codice, descrizione, fk_attivita, descrizione_lunga) VALUES ('179', 'Autorizzazione per la costruzione di infrastruttura passiva a Banda Ultra Lar...', '30', 'Autorizzazione per la costruzione di infrastruttura passiva a Banda Ultra Larga D.Lgs n. 259 del 01/08/2003 (Codice delle comunicazioni elettroniche)');