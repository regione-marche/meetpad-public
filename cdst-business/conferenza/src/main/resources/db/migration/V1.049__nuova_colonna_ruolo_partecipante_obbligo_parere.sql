ALTER TABLE cdst.ruolo_partecipante ADD COLUMN obbligo_espressione_parere boolean;

UPDATE cdst.ruolo_partecipante SET obbligo_espressione_parere='false' where codice='1';
UPDATE cdst.ruolo_partecipante SET obbligo_espressione_parere='false' where codice='2';
UPDATE cdst.ruolo_partecipante SET obbligo_espressione_parere='true' where codice='3';
UPDATE cdst.ruolo_partecipante SET obbligo_espressione_parere='false' where codice='4';
UPDATE cdst.ruolo_partecipante SET obbligo_espressione_parere='true' where codice='5';
UPDATE cdst.ruolo_partecipante SET obbligo_espressione_parere='false' where codice='6';