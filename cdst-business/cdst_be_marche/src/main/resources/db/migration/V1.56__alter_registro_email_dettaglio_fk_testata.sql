ALTER TABLE cdst.registro_email_dettaglio
    ADD COLUMN fk_registro_email_testata integer;
    
ALTER TABLE cdst.registro_email_dettaglio ADD CONSTRAINT dettaglio_fk_registro_email_testata FOREIGN KEY (fk_registro_email_testata)
        REFERENCES cdst.registro_email_testata (id_email_testata) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;