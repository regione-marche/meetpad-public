UPDATE cdst.tipo_evento
	SET flag_invio_email='false'
	WHERE codice='4';
	
UPDATE cdst.template
	SET nome_template='template_corpo'
	WHERE fk_tipo_evento='9';
	
UPDATE cdst.template
	SET nome_template='template_richiesta_unica_integrazione'
	WHERE fk_tipo_evento='5';