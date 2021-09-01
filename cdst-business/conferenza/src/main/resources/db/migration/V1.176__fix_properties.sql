/*
fix properties varie
*/


/*
fix data simultanea
*/
insert into tipo_data values ('DATA_PRIMA_SESSIONE_SIMULTANEA', 'Data prima sessione simultanea');
update modifica_data set fk_tipo_data = 'DATA_PRIMA_SESSIONE_SIMULTANEA' where fk_tipo_data = 'DATA_PRIMA_SESSIONE_SIMULATA';
delete from tipo_data where codice = 'DATA_PRIMA_SESSIONE_SIMULATA';

/*
rimozione entei partecipanti delle conferenze ambiente
*/
delete from paleo_tipoconferenza_properties where tipo_properties = 'entiPartecipanti' and id_tipo_conferenza in ('8','9','10','11');

/*
aggiornmento dati responsabile conferenze ambiente
*/
update paleo_tipoconferenza_properties set valore_properties = 'Granci' where tipo_properties = 'responsabile_cognome' and id_tipo_conferenza in ('8','9','10','11'); 
update paleo_tipoconferenza_properties set valore_properties = 'katjuscia.granci@regione.marche.it' where tipo_properties = 'responsabile_mail' and id_tipo_conferenza in ('8','9','10','11'); 
update paleo_tipoconferenza_properties set valore_properties = 'Katjuscia' where tipo_properties = 'responsabile_nome' and id_tipo_conferenza in ('8','9','10','11'); 
update paleo_tipoconferenza_properties set valore_properties = 'katjuscia.granci@regione.marche.it' where tipo_properties = 'responsabile_pec' and id_tipo_conferenza in ('8','9','10','11'); 

