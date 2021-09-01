ALTER TABLE cdst.rubrica_imprese ADD COLUMN STEP_MODIFICABILI boolean;
ALTER TABLE cdst.rubrica_imprese ADD COLUMN LISTA_STEP_MODIFICABILI character varying(255);

ALTER TABLE cdst.rubrica_richiedenti ADD COLUMN PRINCIPALE boolean;
ALTER TABLE cdst.rubrica_richiedenti ADD COLUMN FK_RUBRICA_IMPRESE integer;
ALTER TABLE cdst.rubrica_richiedenti ADD CONSTRAINT fk_rubrica_imprese FOREIGN KEY (FK_RUBRICA_IMPRESE)
        REFERENCES cdst.rubrica_imprese (id_rubrica_imprese);