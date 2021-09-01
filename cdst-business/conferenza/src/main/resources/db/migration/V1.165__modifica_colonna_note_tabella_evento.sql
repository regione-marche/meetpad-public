/*
Modifica tabella evento per aumento dimensione colonna note
*/

ALTER TABLE cdst.evento ALTER COLUMN note TYPE character varying(1000);