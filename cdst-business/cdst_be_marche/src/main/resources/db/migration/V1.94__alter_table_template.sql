ALTER TABLE cdst.template ADD COLUMN nome_template_download character varying(255);

UPDATE cdst.template
	SET nome_template_download='template_indizione'
	WHERE fk_tipo_evento ='2';
	
UPDATE cdst.template
	SET nome_template_download='template_verbale'
	WHERE fk_tipo_evento ='9';