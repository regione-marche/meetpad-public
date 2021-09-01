create or replace view registro_email_errori
as
with registro as (
	select 'meetpad@emarche.it' as email_mittente, ret.email as email_destinatario,ret.fase_email,ret.fk_conferenza id_conferenza,det.id_messaggio,ret.subject, 
	det.dt_sent_email,det.stato_email,det.nota  
	from REGISTRO_EMAIL_DETTAGLIO det
	left  join  REGISTRO_EMAIL_TESTATA ret   on det.id_messaggio=ret.id_messaggio 
	WHERE 1=1
	and det.stato_email ='ERRORETRASMISSIONE'
	and ret.email is not null
)
select count(*) as tentativi,id_messaggio ,id_conferenza,email_destinatario
from registro
where 1=1
and registro.email_destinatario is not null and not trim(registro.email_destinatario)=''
group by id_messaggio,id_conferenza,email_destinatario;



create or replace view registro_email_errorirecuperati
as
with registro as (
	select 'meetpad@emarche.it' as email_mittente, ret.email as email_destinatario,ret.fase_email,ret.fk_conferenza id_conferenza,det.id_messaggio,ret.subject, 
	det.dt_sent_email,det.stato_email,det.nota  
	from REGISTRO_EMAIL_DETTAGLIO det
	left  join  REGISTRO_EMAIL_TESTATA ret   on det.id_messaggio=ret.id_messaggio 
	WHERE 1=1
	and det.stato_email ='INOLTRATO'
	and ret.email is not null
)
select count(*) as tentativi,ret2.id_messaggio ,ret2.id_conferenza,ret2.email_destinatario
from registro ret2
inner  join  registro_email_errori det   on det.id_messaggio=ret2.id_messaggio 
where 1=1
group by ret2.id_messaggio,ret2.id_conferenza,ret2.email_destinatario;