/*
Inserimento della tabella modifica_data che mappa le modifiche delle date associate alla conferenza
*/

CREATE TABLE tipo_data (
	codice varchar(125) NOT null unique,
	descrizione varchar(255) NOT null
);

insert into tipo_data (codice, descrizione) values ('DATA_TERMINE' ,'Data termine conferenza');
insert into tipo_data (codice, descrizione) values ('DATA_PRIMA_SESSIONE_SIMULATA' ,'Data prima sessione simulata');
insert into tipo_data (codice, descrizione) values ('DATA_TERMINE_ESPRESSIONE_PARERI' ,'Data termine espressione pareri');
insert into tipo_data (codice, descrizione) values ('DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA' ,'Data termine richiesta integrazioni conferenza');

CREATE TABLE modifica_data (
	id_modifica_data serial NOT null primary key,
	data timestamp not NULL,
	fk_tipo_data varchar(125) NOT null,
	fk_conferenza int4 NOT null,
	unique(fk_tipo_data, fk_conferenza),
	CONSTRAINT fk_tipo_data FOREIGN KEY (fk_tipo_data) REFERENCES tipo_data(codice),
	CONSTRAINT id_modifica_data FOREIGN KEY (id_modifica_data) references conferenza(id_conferenza)
);

alter table evento add column fk_modifica_data int4 default null references modifica_data(id_modifica_data);

/*
Inserimento nuovo evento modifica_data
*/
insert into oggetto_evento (codice, descrizione) values ('16', 'Modifica Data');
insert into tipo_evento (codice, descrizione, fk_oggetto_evento, fk_tipologia_documento, flag_invio_email, flag_allegato)
values ('26', 'Modifica Data', '16', 'INTERAZIONI', true, true);
insert into permesso_azione (codice, descrizione, fk_tipo_evento) values ('INSERT_EVENTO_MODIFICA_DATA', 'modifica data', '26');
insert into permesso (id_permesso, fk_permesso_ruolo, fk_permesso_azione, permission_strategy) values 
(32, 'CREATORE', 'INSERT_EVENTO_MODIFICA_DATA', 'default_permission_strategy');
insert into permesso (id_permesso, fk_permesso_ruolo, fk_permesso_azione, permission_strategy) values 
(33, 'RESPONSABILE', 'INSERT_EVENTO_MODIFICA_DATA', 'default_permission_strategy');

/*
configurazione invio dell'email ai partecipanti
*/
insert into evento_partecipante (fk_tipo_evento, fk_ruolo_partecipante, flag_cc) values ('26', '1', false), ('26', '2', false),
('26', '3', false), ('26', '4', false), ('26', '5', false), ('26', '6', false);
/*
configurazione dei template
*/

insert into "template" (nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione) 
values ('template_modifica_data', '26', null, '1'), ('template_modifica_data', '26', null, '2'), ('template_modifica_data', '26', null, '3'),
('template_modifica_data', '26', null, '4'), ('template_modifica_data', '26', null, '5');