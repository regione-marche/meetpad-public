--#==============================================================================================
--# Query per FLYWAY


--#==============================================================================================
insert into tipologia_conferenza
(codice, descrizione, flag_orario_visibile, flag_modalita_incontro_modificabile, fk_modalita)
select '7', 'tipologiaConferenza.incontroOperativo', true, true, NULL
where NOT EXISTS (
	select codice from tipologia_conferenza where codice='7'
);



--#==============================================================================================
insert into evento_calendario_data_obbligatoria
(id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore)
select 25, '7', '1', true, 'istanza.dataTermine.null', 'definition.instance.expirationDate'
where NOT EXISTS (
	select id_evento_calendario_data_obbligatoria from evento_calendario_data_obbligatoria where fk_tipologia_conferenza='7' and campo_errore='definition.instance.expirationDate'
);
--#==============================================================================================
insert into evento_calendario_data_obbligatoria
(id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore)
select 26, '7', '3', true, 'istanza.termineDataIntegrazione.null', 'definition.instance.endIntegrationDate'
where NOT EXISTS (
	select id_evento_calendario_data_obbligatoria from evento_calendario_data_obbligatoria where fk_tipologia_conferenza='7' and campo_errore='definition.instance.endIntegrationDate'
);
--#==============================================================================================
insert into evento_calendario_data_obbligatoria
(id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore)
select 27, '7', '4', true, 'istanza.dataPrimaSessione.null', 'definition.instance.firstSessionDate'
where NOT EXISTS (
	select id_evento_calendario_data_obbligatoria from evento_calendario_data_obbligatoria where fk_tipologia_conferenza='7' and campo_errore='definition.instance.firstSessionDate'
);
--#==============================================================================================
insert into evento_calendario_data_obbligatoria
(id_evento_calendario_data_obbligatoria, fk_tipologia_conferenza, fk_eventi_calendario, flag_obbligatorio, messaggio_errore, campo_errore)
select 28, '7', '2', false, 'istanza.termineDataPareri.null', 'definition.instance.endOpinionDate'
where NOT EXISTS (
	select id_evento_calendario_data_obbligatoria from evento_calendario_data_obbligatoria where fk_tipologia_conferenza='7' and campo_errore='definition.instance.endOpinionDate'
);



--#==============================================================================================
insert into tipologia_conferenza_data_definizione
(id_tipologia_conferenza_date_definizione, fk_tipologia_conferenza, campo_data_calcolato, flag_obbligatorio, offset_gg_lavorativi, offset_gg_solari, campo_data_base, ordine)
select 25, '7', 'endIntegrationDate', true, 5, 15, 'creationDate', 1
where NOT EXISTS (
	select id_tipologia_conferenza_date_definizione from tipologia_conferenza_data_definizione where fk_tipologia_conferenza='7' and campo_data_calcolato='endIntegrationDate'
);
--#==============================================================================================
insert into tipologia_conferenza_data_definizione
(id_tipologia_conferenza_date_definizione, fk_tipologia_conferenza, campo_data_calcolato, flag_obbligatorio, offset_gg_lavorativi, offset_gg_solari, campo_data_base, ordine)
select 26, '7', 'endOpinionDate', false, 5, 45, 'creationDate', 2
where NOT EXISTS (
	select id_tipologia_conferenza_date_definizione from tipologia_conferenza_data_definizione where fk_tipologia_conferenza='7' and campo_data_calcolato='endOpinionDate'
);
--#==============================================================================================
insert into tipologia_conferenza_data_definizione
(id_tipologia_conferenza_date_definizione, fk_tipologia_conferenza, campo_data_calcolato, flag_obbligatorio, offset_gg_lavorativi, offset_gg_solari, campo_data_base, ordine)
select 27, '7', 'firstSessionDate', true, 5, 45, 'creationDate', 3
where NOT EXISTS (
	select id_tipologia_conferenza_date_definizione from tipologia_conferenza_data_definizione where fk_tipologia_conferenza='7' and campo_data_calcolato='firstSessionDate'
);
--#==============================================================================================
insert into tipologia_conferenza_data_definizione
(id_tipologia_conferenza_date_definizione, fk_tipologia_conferenza, campo_data_calcolato, flag_obbligatorio, offset_gg_lavorativi, offset_gg_solari, campo_data_base, ordine)
select 28, '7', 'expirationDate', true, 0, 45, 'firstSessionDate', 4
where NOT EXISTS (
	select id_tipologia_conferenza_date_definizione from tipologia_conferenza_data_definizione where fk_tipologia_conferenza='7' and campo_data_calcolato='expirationDate'
);



--#==============================================================================================
INSERT INTO tipologia_conferenza_specializzazione
(codice, descrizione, fk_tipologia_conferenza, flag_autoaccreditamento, fk_azione)
select '7', 'tipologiaConferenzaSpec.incontroOperativo', '7', true, NULL
where NOT EXISTS (
  select codice from tipologia_conferenza_specializzazione where descrizione='tipologiaConferenzaSpec.incontroOperativo'
);


--//=============================================================
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '1', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='1'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '6', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='6'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '3', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='3'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '4', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='4'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '5', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='5'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '15', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='15'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '16', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='16'
);
--#==============================================================================================
insert into categoria_documento_tipologia_conferenza
(codice_categoria_documento, codice_tipologia_conferenza)
select '17', '7'
where NOT EXISTS (
	select codice_categoria_documento from categoria_documento_tipologia_conferenza where codice_tipologia_conferenza='7' and codice_categoria_documento='17'
);


--//=============================================================
insert into modello
(codice,descrizione,fk_tipologia_conferenza_specializzazione)
select '20', 'bozza_verbale', '7'
where NOT EXISTS (
  select codice from modello where fk_tipologia_conferenza_specializzazione='7' and descrizione ='bozza_verbale'
);


--//=============================================================
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_new_indizione_meetpad_operational_meeting', '2', 'template_indizione_meetpad', '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='2'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_integrazione', '3', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='3'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_verbale', '9', 'template_verbale_meetpad', '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='9'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_unica_integrazione_meetpad', '5', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='5'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo', '6', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='6'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo', '7', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='7'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_espressione_parere', '11', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='11'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo', '12', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='12'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo', '13', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='13'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_accreditamento', '17', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='17'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_conferma_accreditamento', '18', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='18'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_rifiuta_accreditamento', '19', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='19'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_documentazione_condivisa', '24', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='24'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_documentazione_firmata', '25', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='25'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_modifica_data', '26', NULL, '7'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='7' and fk_tipo_evento='26'
);
--#==============================================================================================
--#
--#==============================================================================================
