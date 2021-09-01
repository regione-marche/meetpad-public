

ALTER TABLE cdst.report ADD COLUMN visibilita character varying(30) default  'PRIVATE';
ALTER TABLE cdst.report ADD COLUMN note character varying(600);
ALTER TABLE cdst.report DROP COLUMN FK_PARAMETRO;