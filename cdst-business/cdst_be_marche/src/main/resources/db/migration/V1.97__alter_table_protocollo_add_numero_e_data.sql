ALTER TABLE cdst.protocollo
    ADD COLUMN numero_protocollo character varying(255);
ALTER TABLE cdst.protocollo 
	ADD COLUMN data_protocollo timestamp without time zone;