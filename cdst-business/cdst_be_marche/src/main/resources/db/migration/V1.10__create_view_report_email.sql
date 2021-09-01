create or replace view report_email_sample
as
with registro_testata as (
	select ret.id_messaggio, max(det.dt_sent_email) dt_sent_email
	from REGISTRO_EMAIL_TESTATA ret 
	inner  join REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio 	
	group by ret.id_messaggio	
),registro as (
	select 'meetpad@emarche.it' as email_mittente, ret.email as email_destinatario,ret.fase_email,ret.fk_conferenza id_conferenza,ret.id_messaggio,ret.subject, 
	det.dt_sent_email,det.stato_email,det.nota 
	from REGISTRO_EMAIL_TESTATA ret 
	inner  join REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio 	
)
select rr.* 
from registro_testata rt
inner join registro rr on rr.id_messaggio=rt.id_messaggio and rr.dt_sent_email=rt.dt_sent_email;



create or replace view  report_mail_sample_bean
as
with pesonafisica as (
	select distinct pc.fk_conferenza id_conferenza, pp.id_persona,codice_fiscale,
	'meetpad@emarche.it' as email_mittente,
	case  when pp.email IS NULL then 
   (CASE WHEN ep.email is null then pc.pec_ente_competente else ep.email end)
    else pp.email END as email_destinatario
	from persona pp
	left join accreditamento acc on acc.fk_persona=pp.id_persona
	left join partecipante pc on pc.id_partecipante=acc.fk_partecipante	
	left join email_partecipante ep on ep.fk_partecipante=pc.id_partecipante
	WHERE pc.fk_conferenza is NOT NULL
)
select pf.id_conferenza, ret.id_messaggio,pf.email_destinatario,ret.fase_email
from pesonafisica pf
inner join conferenza cc on cc.id_conferenza=pf.id_conferenza
left join REGISTRO_EMAIL_TESTATA ret on ret.fk_conferenza=cc.id_conferenza
left join REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio 
WHERE 1=1
AND not coalesce( trim(pf.email_destinatario),'')='' 
AND not coalesce( trim(ret.id_messaggio),'')='' 
order by 1,2;


create or replace VIEW report_email
as
with pesonafisica as (
	select distinct pc.fk_conferenza id_conferenza, pp.id_persona,codice_fiscale,
	'meetpad@emarche.it' as email_mittente,
	case  when pp.email IS NULL then 
   (CASE WHEN ep.email is null then pc.pec_ente_competente else ep.email end)
    else pp.email END as email_destinatario
	from persona pp
	left join accreditamento acc on acc.fk_persona=pp.id_persona
	left join partecipante pc on pc.id_partecipante=acc.fk_partecipante	
	left join email_partecipante ep on ep.fk_partecipante=pc.id_partecipante
	WHERE pc.fk_conferenza is NOT NULL
)
select pf.*,cc.riferimento_istanza,ret.id_messaggio, ret.subject,ret.fase_email, det.id_email_dettaglio as id_record
,det.dt_sent_email,det.stato_email
from pesonafisica pf
inner join conferenza cc on cc.id_conferenza=pf.id_conferenza
left join REGISTRO_EMAIL_TESTATA ret on ret.fk_conferenza=cc.id_conferenza
left join REGISTRO_EMAIL_DETTAGLIO det on det.id_messaggio=ret.id_messaggio 
WHERE 1=1
AND not coalesce( trim(pf.email_destinatario),'')='' 
order by 1,2;