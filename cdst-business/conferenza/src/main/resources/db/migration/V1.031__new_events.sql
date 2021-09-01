INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('9', 'Abilita Modifica');
INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('10', 'Revoca Modifica');
INSERT INTO cdst.oggetto_evento(
	codice, descrizione)
	VALUES ('11', 'Termina Modifica');

INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, flag_invio_email, flag_allegato)
	VALUES ('14', 'Abilita Modifica Richiedente', '9', 'true', 'false');
	
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, flag_invio_email, flag_allegato)
	VALUES ('15', 'Revoca Modifica Richiedente', '10', 'true', 'false');
	
INSERT INTO cdst.tipo_evento(
	codice, descrizione, fk_oggetto_evento, flag_invio_email, flag_allegato)
	VALUES ('16', 'Termina Modifica Richiedente', '11', 'true', 'false');
	
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc)
	VALUES ('14', '1', 'false');
	
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc)
	VALUES ('15', '1', 'false');
	
INSERT INTO cdst.evento_partecipante(fk_tipo_evento, fk_ruolo_partecipante, flag_cc)
	VALUES ('16', '2', 'false');
	
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_abilita_modifica_richiedente', '4', '14');
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_revoca_modifica_richiedente', '4', '15');
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_termina_modifica_richiedente', '4', '16');
	
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_abilita_modifica_richiedente', '5', '14');
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_revoca_modifica_richiedente', '5', '15');
INSERT INTO cdst.template(nome_template, fk_tipologia_conferenza, fk_tipo_evento)
	VALUES ('template_termina_modifica_richiedente', '5', '16');