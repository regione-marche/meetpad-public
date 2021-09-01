ALTER TABLE cdst.tipologia_documento ADD COLUMN flag_upload_consolle boolean DEFAULT false;

UPDATE cdst.tipologia_documento
	SET flag_upload_consolle=true
	WHERE codice='VIDEO';