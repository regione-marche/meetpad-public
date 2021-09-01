


CREATE TABLE Tipo_Protocollo
(
    id  integer,
    nome character varying(100) COLLATE pg_catalog."default"
);

insert into tipo_protocollo (id,nome)values(1,'CONFERENZA-SUAP');
insert into tipo_protocollo (id,nome)values(2,'SUAP');
commit;



CREATE TABLE stati_protocollo
(
    id  integer,
    nome character varying(100) COLLATE pg_catalog."default"
);

insert into Stati_Protocollo (id,nome) values(-1,'protocollo solo MeetPAD');
insert into Stati_Protocollo(id,nome) values(0,'Da protocollare esternamente');
insert into Stati_Protocollo(id,nome) values(1,'Protocollazione in corso,');
insert into Stati_Protocollo(id,nome) values(3,'Protocollata');
insert into Stati_Protocollo(id,nome) values(5,'In Errore');
commit;

CREATE TABLE Protocollo
(
    id  integer,
	id_documento  integer,
	fk_protocollo_meetpad integer,
	id_protocollo_esterno integer,
	fk_tipo_protocollo  integer,
	fk_Stati_Protocollo  integer,
	error  character varying(255) COLLATE pg_catalog."default"
);

CREATE TABLE observer_registry
(
	id  integer,
	codice character varying(100) COLLATE pg_catalog."default",	
	nome character varying(255) COLLATE pg_catalog."default",
	subrscribed_event_type character varying(255) COLLATE pg_catalog."default", 
	class character varying(255) COLLATE pg_catalog."default"
);

insert into observer_registry (id,codice,nome,subrscribed_event_type)values(1,'SCRITTURA-PROTOCOLLO-SUAP','Protocollo Saup','6');
insert into observer_registry (id,codice,nome,subrscribed_event_type)values(2,'SCRITTURA-PROTOCOLLO-SUAP','Protocollo Saup','12');

CREATE SEQUENCE cdst.ObserverRegistryAudita_seq;

CREATE TABLE observer_registry_audit(
	id integer NOT NULL DEFAULT nextval('cdst.ObserverRegistryAudita_seq'::regclass),
	fk_observer_registry integer,
	id_evento  integer
);


