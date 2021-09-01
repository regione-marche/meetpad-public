CREATE SEQUENCE  IF NOT EXISTS  oo_adapter_versioni_seq; 
 
CREATE TABLE  IF NOT EXISTS  oo_adapter_versioni
(
    id integer NOT NULL DEFAULT nextval('oo_adapter_versioni_seq'::regclass),
    TOKEN character varying(255),
    URL_OO character varying(255),
    ID_USER_OO character varying(255),
    CONSTRAINT oo_adapter_versioni_pkey PRIMARY KEY (id)
);
 
alter table oo_adapter_versioni add  IF NOT EXISTS  fk_adapter integer;
 
alter table oo_adapter add  IF NOT EXISTS  fk_documento integer;
 
insert into  Registro_Documento_Tipo (codice,descrizione)values('ONLYOFFICE','Documento OnlyOffice Community') ;
insert into  Registro_Documento_Fonte (codice,descrizione)values('ONLYOFFICE','Documento OnlyOffice Community') ;
