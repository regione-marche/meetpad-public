CREATE OR REPLACE VIEW cdst.ricerca_rubrica_richiedenti AS
	SELECT id_rubrica_richiedenti, 
		tc.codice AS codice_tipo_conf_rubrica_richiedenti, 
		tc.descrizione AS descrizione_tipo_conf_rubrica_richiedenti, 
		tcf.codice AS codice_tipo_conf_rubrica_imprese, 
		tcf.descrizione AS descrizione_tipo_conf_rubrica_imprese, 
		id_persona, 
		per.nome AS nome_richiedente, 
		per.cognome AS cognome_richiedente, 
		per.codice_fiscale AS codice_fiscale_richiedente, 
		id_impresa, 
		im.denominazione AS descrizione_impresa 
		FROM cdst.rubrica_richiedenti rr
		join cdst.tipologia_conferenza tc on tc.codice=rr.fk_tipologia_conferenza
		join cdst.persona per on fk_persona=id_persona
		left join cdst.rubrica_imprese ri on fk_rubrica_imprese=id_rubrica_imprese
		left join cdst.tipologia_conferenza tcf on tcf.codice=ri.fk_tipologia_conferenza
		left join cdst.impresa im on fk_impresa=id_impresa;