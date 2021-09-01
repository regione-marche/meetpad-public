

INSERT INTO modello
    ( codice,descrizione,fk_tipologia_conferenza_specializzazione)
SELECT  '7','template_ord19_verbale',3 
where NOT EXISTS (
  SELECT codice FROM modello WHERE descrizione = 'template_ord19_verbale'
);

INSERT INTO modello
    ( codice,descrizione,fk_tipologia_conferenza_specializzazione)
SELECT  '8','template_ord19_indizione',3 
where NOT EXISTS (
  SELECT codice FROM modello WHERE descrizione = 'template_ord19_verbale'
);


INSERT INTO modello
    ( codice,descrizione,fk_tipologia_conferenza_specializzazione)
SELECT  '9','template_ord19_trasmissionedecreto',3 
where NOT EXISTS (
  SELECT codice FROM modello WHERE descrizione = 'template_ord19_trasmissionedecreto'
);

