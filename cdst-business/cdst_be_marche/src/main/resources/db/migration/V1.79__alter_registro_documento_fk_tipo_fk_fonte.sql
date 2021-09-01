alter table cdst.registro_documento add fonte character varying(255);

ALTER TABLE cdst.registro_documento ADD CONSTRAINT registro_documento_fk_fonte FOREIGN KEY (fonte)
        REFERENCES cdst.registro_documento_fonte (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;
		
ALTER TABLE cdst.registro_documento ADD CONSTRAINT registro_documento_fk_tipo FOREIGN KEY (tipo)
        REFERENCES cdst.registro_documento_tipo (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;