ALTER TABLE cdst.registro_email_testata
ADD fk_tipo_evento character varying(255),
ADD CONSTRAINT fk_tipo_evento FOREIGN KEY (fk_tipo_evento)
        REFERENCES cdst.tipo_evento (codice);