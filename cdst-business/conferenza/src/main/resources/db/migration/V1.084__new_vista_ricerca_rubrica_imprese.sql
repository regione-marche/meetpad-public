CREATE OR REPLACE VIEW cdst.ricerca_rubrica_imprese AS
	SELECT id_rubrica_imprese,
		imp.id_impresa,
		imp.denominazione AS nome,
		imp.codice_fiscale,
		imp.partita_iva,
		tc.codice AS codice_tipologia_conferenza,
		tc.descrizione AS descrizione_tipologia_conferenza
			FROM cdst.rubrica_imprese ri
			join cdst.tipologia_conferenza tc on tc.codice=ri.fk_tipologia_conferenza
			join cdst.impresa imp on imp.id_impresa=ri.fk_impresa;