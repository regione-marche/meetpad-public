-- istruttoriaAmbienteVIA
insert into tipologia_conferenza_specializzazione values ('8', 'tipologiaConferenzaSpec.istruttoriaAmbienteVIA', '3', false);

insert into "template" (nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione) 
select nome_template, fk_tipo_evento, nome_template_download, '8' as fk_tipologia_conferenza_specializzazione from "template" where fk_tipologia_conferenza_specializzazione = '3';

insert into modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) values ('21', 'bozza_verbale', '8');

insert into paleo_tipoconferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) 
select '8', ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault from paleo_tipoconferenza_properties where id_tipo_conferenza = '3';

-- istruttoriaAmbienteVIA
insert into tipologia_conferenza_specializzazione values ('9', 'tipologiaConferenzaSpec.decisoriaAmbienteVIA', '3', false);

insert into "template" (nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione) 
select nome_template, fk_tipo_evento, nome_template_download, '9' as fk_tipologia_conferenza_specializzazione from "template" where fk_tipologia_conferenza_specializzazione = '3';

insert into modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) values ('22', 'bozza_verbale', '9');

insert into paleo_tipoconferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) 
select '9', ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault from paleo_tipoconferenza_properties where id_tipo_conferenza = '3';

-- istruttoriaAmbienteVIA
insert into tipologia_conferenza_specializzazione values ('10', 'tipologiaConferenzaSpec.istruttoriaAmbienteAIA', '3', false);

insert into "template" (nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione) 
select nome_template, fk_tipo_evento, nome_template_download, '10' as fk_tipologia_conferenza_specializzazione from "template" where fk_tipologia_conferenza_specializzazione = '3';

insert into modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) values ('23', 'bozza_verbale', '10');

insert into paleo_tipoconferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) 
select '10', ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault from paleo_tipoconferenza_properties where id_tipo_conferenza = '3';

-- decisoriaAmbienteAIA
insert into tipologia_conferenza_specializzazione values ('11', 'tipologiaConferenzaSpec.decisoriaAmbienteAIA', '3', false);

insert into "template" (nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione) 
select nome_template, fk_tipo_evento, nome_template_download, '11' as fk_tipologia_conferenza_specializzazione from "template" where fk_tipologia_conferenza_specializzazione = '3';

insert into modello (codice, descrizione, fk_tipologia_conferenza_specializzazione) values ('24', 'bozza_verbale', '11');

insert into paleo_tipoconferenza_properties (id_tipo_conferenza, ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault) 
select '11', ambiente, nome_properties, valore_properties, tipo_valore_properties, tipo_properties, isdefault from paleo_tipoconferenza_properties where id_tipo_conferenza = '3';
