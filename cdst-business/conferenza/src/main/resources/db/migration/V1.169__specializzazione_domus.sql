--#==============================================================================================
--# Query per FLYWAY
--#==============================================================================================
insert into tipologia_conferenza_specializzazione
(codice,descrizione,fk_tipologia_conferenza,flag_autoaccreditamento,fk_azione)
select '6','tipologiaConferenzaSpec.domus','3',false,null
where NOT EXISTS (
  select codice from tipologia_conferenza_specializzazione where descrizione='tipologiaConferenzaSpec.domus'
);


--//=============================================================
insert into modello
(codice,descrizione,fk_tipologia_conferenza_specializzazione)
select '13','bozza_verbale' ,'6'
where NOT EXISTS (
  select codice from modello where fk_tipologia_conferenza_specializzazione='6' and descrizione ='bozza_verbale'
);
--//=============================================================
insert into modello
(codice,descrizione,fk_tipologia_conferenza_specializzazione)
select '17','template_ord19_verbale' ,'6'
where NOT EXISTS (
  select codice from modello where fk_tipologia_conferenza_specializzazione='6' and descrizione ='template_ord19_verbale'
);
--//=============================================================
insert into modello
(codice,descrizione,fk_tipologia_conferenza_specializzazione)
select '19','template_ord19_trasmissionedecreto' ,'6'
where NOT EXISTS (
  select codice from modello where fk_tipologia_conferenza_specializzazione='6' and descrizione ='template_ord19_trasmissionedecreto'
);
--//=============================================================
--//
--//=============================================================
--//select * from template where fk_tipologia_conferenza_specializzazione='3'
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_conferma_accreditamento','18',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='18'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_rifiuta_accreditamento','19',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='19'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_verbale','9','template_verbale_meetpad','6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='9'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_integrazione','3',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='3'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_espressione_parere','11',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='11'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_documentazione_condivisa','24',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='24'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_documentazione_firmata','25',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='25'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_modifica_data','26',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='26'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo','13',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='13'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_new_indizione_meetpad','3',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='3'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_indizione_meetpad','2',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='2'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_unica_integrazione_meetpad','2',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='2'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_accreditamento','17',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='17'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo','6',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='6'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo','7',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='7'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_corpo','12',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='12'
);
--#==============================================================================================
insert into template
(nome_template,fk_tipo_evento,nome_template_download,fk_tipologia_conferenza_specializzazione)
select 'template_richiesta_unica_integrazione_meetpad','5',null,'6'
where NOT EXISTS (
	select id_template from template where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='5'
);
--#==============================================================================================
--#
--#==============================================================================================
