ALTER TABLE cdst.persona ADD CONSTRAINT codice_fiscale_persona_unique UNIQUE (codice_fiscale);
ALTER TABLE cdst.utente ADD CONSTRAINT codice_fiscale_utente_unique UNIQUE (codice_fiscale);