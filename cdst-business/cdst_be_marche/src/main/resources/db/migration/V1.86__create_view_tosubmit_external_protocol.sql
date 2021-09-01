CREATE OR REPLACE VIEW VIEW_SUBMIT_TO_EXTERNAL_PROTOCOL
as
select distinct
rd.id  as id_registro,
dd.fk_conferenza as id_conferenza,
fc.id_pratica,
ee.id_evento as id_evento,
ee.fk_tipo_evento as id_tipo_evento,
dd.id_documento as id_documento,
te.codice subrscribed_event_type,
te.fk_tipologia_documento as tipologia_documento,
RR.id id_observer_registry,
RR.codice
from documento dd
inner join registro_documento rd on rd.fk_documento=dd.id_documento
inner join integ_frontiera_conferenza  fc on fc.fk_conferenza=dd.fk_conferenza 
inner join evento ee on (ee.fk_conferenza=dd.fk_conferenza )
inner join tipo_evento te on ee.fk_tipo_evento=te.codice and te.fk_tipologia_documento=dd.fk_tipologia_documento
inner join observer_registry RR on RR.subrscribed_event_type= te.codice
left join observer_registry_Audit RRA on RRA.fk_Observer_Registry=RR.id 
where 1=1
AND RRA.id_evento IS NULL