create or replace view viewfirma
as (
with
maxsessionid as(select max(rfa.id) as id,rfa.token,rfa.id_documento from registro_firma_adapter rfa where 1=1 group by rfa.token,rfa.id_documento )  
,registro_firma as (select rfa.* from registro_firma_adapter rfa inner join maxsessionid mm on mm.id=rfa.id where 1=1 and not rfa.stato = 'DELETED'	)	
select distinct 
rd.fk_documento as id_documento,
2 as fk_tipo_firma,
rfa.token as sessione_firma ,
rfa.shot, 
rfa.id_user id_user,
rfa.crc,
CASE  WHEN rfa.stato is NULL THEN 'UNLOCKED' ELSE rfa.stato END stato
,dd.fk_conferenza as id_conferenza
from token_registro_documento trd 
inner join registro_documento rd on rd.id=trd.fk_registro
inner join documento dd on dd.id_documento=rd.fk_documento
left join  registro_firma rfa on rfa.id_documento=rd.fk_documento
where 1=1
order by dd.fk_conferenza desc
);