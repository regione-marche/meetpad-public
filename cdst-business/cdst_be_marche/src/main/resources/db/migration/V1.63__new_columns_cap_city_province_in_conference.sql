ALTER TABLE cdst.conferenza ADD COLUMN cap_sessione_simultanea character varying(255);
ALTER TABLE cdst.conferenza ADD COLUMN fk_comune_sessione_simultanea character varying(255);
ALTER TABLE cdst.conferenza ADD COLUMN fk_provincia_sessione_simultanea character varying(255);

ALTER TABLE cdst.conferenza
ADD CONSTRAINT fk_comune_sessione_simultanea FOREIGN KEY (fk_comune_sessione_simultanea)
        REFERENCES cdst.comune (codice);
        
ALTER TABLE cdst.conferenza
ADD CONSTRAINT fk_provincia_sessione_simultanea FOREIGN KEY (fk_provincia_sessione_simultanea)
        REFERENCES cdst.provincia (codice);