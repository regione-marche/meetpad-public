ALTER TABLE cdst.registro_email_dettaglio
ADD CONSTRAINT fk_email_status FOREIGN KEY (stato_email)
        REFERENCES cdst.email_status (codice);