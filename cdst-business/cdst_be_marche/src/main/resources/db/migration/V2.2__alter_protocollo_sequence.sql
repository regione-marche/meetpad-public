CREATE SEQUENCE cdst.protocollo_id_protocollo_seq;

ALTER SEQUENCE cdst.protocollo_id_protocollo_seq
    OWNER TO cdst;
    
alter table cdst.protocollo alter column id set DEFAULT nextval('cdst.protocollo_id_protocollo_seq'::regclass);