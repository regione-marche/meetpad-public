UPDATE cdst.registro_documento registro
	SET 
		data=(select audit_crt_time from cdst.documento where id_documento = registro.fk_documento), 
		tipo='FS', 
		fonte='MEETPAD'
	WHERE tipo is null;