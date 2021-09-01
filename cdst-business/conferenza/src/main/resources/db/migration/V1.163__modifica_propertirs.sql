/*
Modifica de file properties 
in modo da passare parametri consistenti..


*/

update  cdst.paleo_tipoconferenza_properties set valore_properties = 'Regione Marche' where valore_properties like '%Reg%';
update  cdst.paleo_tipoconferenza_properties set valore_properties = '042' where valore_properties like '%058';
update  cdst.paleo_tipoconferenza_properties set valore_properties = '042002' where valore_properties like '058091';

update  cdst.domus_conferenza_properties set valore_properties = 'Regione Marche' where valore_properties like '%Reg%';
update  cdst.domus_conferenza_properties set valore_properties = '042' where valore_properties like '%058';
update  cdst.domus_conferenza_properties set valore_properties = '042002' where valore_properties like '058091';

