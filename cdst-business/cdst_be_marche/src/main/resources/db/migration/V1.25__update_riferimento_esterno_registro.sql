UPDATE cdst.registro_documento reg
	SET rif_esterno='.\' 
			|| (select codice_fiscale_ente || '\' || id_conferenza || '\' from cdst.info_registro where id_registro = reg.id) 
			|| rif_esterno;