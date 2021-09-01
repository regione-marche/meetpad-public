/*
MOVED FROM V1.179__rimozione_info_richiedenti_ambiente
fix proprieta paleo
*/
delete from paleo_tipoconferenza_properties where tipo_properties in ('id_richiedented', 'richiedente_cf','richiedente_cognome','richiedente_comune_istat','richiedente_comune_nome','richiedente_mail','richiedente_nome','richiedente_pec','richiedente_provincia_istat','richiedente_provincia_nome') and id_tipo_conferenza in ('8','9','10','11');

update paleo_tipoconferenza_properties set valore_properties = 'regione.marche.informatica@emarche.it' where tipo_properties = 'enteProcedente' and nome_properties = 'ente_pec' and id_tipo_conferenza in ('8','9','10','11');


/*
fix invio mail indizione domus
*/
update template set nome_template_download='template_ord19_indizione_regionale' 
	where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='2';

update template set nome_template_download='verbale_ord19_regionale' 
	where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='9';



--#==============================================================================================
-- Drop table

CREATE TABLE cdst.gruppo_utenti (
	id_gruppo_utenti serial NOT NULL,
	nome varchar(255) NULL,
	fk_utente int4 NULL,
	CONSTRAINT gruppoutenti_pkey PRIMARY KEY (id_gruppo_utenti),
	CONSTRAINT fktbjja2bgn74m6xsvawv9yqsrn FOREIGN KEY (fk_utente) REFERENCES utente(id_utente)
);


