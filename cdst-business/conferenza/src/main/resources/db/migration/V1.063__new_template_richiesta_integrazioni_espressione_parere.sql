UPDATE cdst.template
	SET nome_template='template_richiesta_integrazione' WHERE fk_tipo_evento='3';
	
UPDATE cdst.template
	SET nome_template='template_espressione_parere' WHERE fk_tipo_evento='11';
	
INSERT INTO cdst.evento_partecipante(
	fk_tipo_evento, fk_ruolo_partecipante, flag_cc)
	VALUES ('7', '2', false);