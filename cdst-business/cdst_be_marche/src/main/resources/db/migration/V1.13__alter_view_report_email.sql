CREATE OR REPLACE VIEW cdst.report_email_sample AS
 WITH registro_testata AS (
         SELECT ret.id_messaggio,
            max(det.dt_sent_email) AS dt_sent_email
           FROM registro_email_testata ret
             JOIN registro_email_dettaglio det ON det.id_messaggio::text = ret.id_messaggio::text
	      WHERE NOT det.STATO_EMAIL ='INOLTRATO' 
          GROUP BY ret.id_messaggio
        ), registro AS (
         SELECT 'meetpad@emarche.it' AS email_mittente,
            ret.email AS email_destinatario,
            ret.fase_email,
            ret.fk_conferenza AS id_conferenza,
            ret.id_messaggio,
            ret.subject,
            det.dt_sent_email,
            det.stato_email,
            det.nota
           FROM registro_email_testata ret
             JOIN registro_email_dettaglio det ON det.id_messaggio::text = ret.id_messaggio::text	
        )
 SELECT rr.email_mittente,
    rr.email_destinatario,
    rr.fase_email,
    rr.id_conferenza,
    rr.id_messaggio,
    rr.subject,
    rr.dt_sent_email,
    rr.stato_email,
    rr.nota
   FROM registro_testata rt
   inner JOIN registro rr ON rr.id_messaggio::text = rt.id_messaggio::text AND rr.dt_sent_email = rt.dt_sent_email;