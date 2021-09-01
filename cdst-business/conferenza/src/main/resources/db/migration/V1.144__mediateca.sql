/**
Creazione di una tabella 
ad unico scopo di mappare gli oggetti
*/

drop table if exists view_mediateca;

create table if not exists view_mediateca
as
select cc.id_conferenza,
CAST (cc.oggetto_determinazione AS character varying(255)) as oggetto,
dd.id_documento, dd.nome as nome_documento,
rr.id as id_registro,rr.rif_esterno as path,
CAST (' ' AS character varying(255)) as token
from documento dd 
inner join tipologia_documento tt on tt.fk_sezione_documentazione=tt.codice
inner join conferenza cc on cc.id_conferenza=dd.fk_conferenza
inner join registro_documento rr on rr.fk_documento=dd.id_documento 
where 1=1
--and tt.codice in ('VIDEO')
--and (upper(dd.nome) like upper(concat('%','VINCA','%')) or upper(cc.oggetto_determinazione) like upper(concat('%','OGGETTO','%')))		
and rr.tipo='FS';

truncate table view_mediateca; 




