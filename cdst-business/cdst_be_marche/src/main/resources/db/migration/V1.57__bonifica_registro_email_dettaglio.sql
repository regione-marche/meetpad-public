DELETE FROM cdst.registro_email_dettaglio
	WHERE id_messaggio is null;
	
UPDATE cdst.registro_email_dettaglio dett
	SET fk_registro_email_testata=(select max(id_email_testata) from cdst.registro_email_testata test where test.id_messaggio = dett.id_messaggio);