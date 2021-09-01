UPDATE cdst.template
	SET nome_template='template_new_indizione_meetpad_preistruttoria_BUL'
	WHERE fk_tipologia_conferenza='4' AND fk_tipo_evento='2';
	
UPDATE cdst.template
	SET nome_template='template_new_indizione_meetpad'
	WHERE fk_tipologia_conferenza='5' AND fk_tipo_evento='2';