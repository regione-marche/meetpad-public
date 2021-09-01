DROP VIEW cdst.cruscotto_pec;
DROP FUNCTION cdst.registro_email_status(character varying);
DROP FUNCTION cdst.registro_email_status(integer);
DROP VIEW cdst.report_email_sample;
DROP VIEW cdst.registro_email_errorirecuperati;
DROP VIEW cdst.registro_email_errori;

alter table cdst.registro_email_dettaglio alter column nota type character varying(1000);

CREATE OR REPLACE FUNCTION cdst.registro_email_status(
	v_id_messaggio character varying)
    RETURNS record
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$

DECLARE 
 status RECORD;
 dettaglio RECORD;
BEGIN
 for dettaglio in (
 	(select *
	from cdst.registro_email_dettaglio dett join cdst.email_status st on st.codice = dett.stato_email
	where id_messaggio = v_id_messaggio
	and stato_email in ('INOLTRATO', 'ERRORETRASMISSIONE')
	order by dt_sent_email)
 )
 loop
   status := (dettaglio.stato_email, dettaglio.dt_sent_email, dettaglio.descrizione, dettaglio.nota);
 end loop;

for dettaglio in (
 	(select *
	from cdst.registro_email_dettaglio dett join cdst.email_status st on st.codice = dett.stato_email
	where id_messaggio = v_id_messaggio
	and stato_email not in ('INOLTRATO', 'ERRORETRASMISSIONE')
	order by dt_sent_email)
 )
 loop
   status := (dettaglio.stato_email, dettaglio.dt_sent_email, dettaglio.descrizione, dettaglio.nota);
 end loop;
																					   
 RETURN status;
END;

$BODY$;

CREATE OR REPLACE FUNCTION cdst.registro_email_status(
	v_id_testata integer)
    RETURNS record
    LANGUAGE 'plpgsql'

    COST 100
    VOLATILE 
AS $BODY$

DECLARE 
 status RECORD;
 dettaglio RECORD;
BEGIN
 for dettaglio in (
 	(select *
	from cdst.registro_email_dettaglio dett join cdst.email_status st on st.codice = dett.stato_email
	where fk_registro_email_testata = v_id_testata
	and stato_email in ('INOLTRATO', 'ERRORETRASMISSIONE')
	order by dt_sent_email)
 )
 loop
   status := (dettaglio.stato_email, dettaglio.dt_sent_email, dettaglio.descrizione, dettaglio.nota);
 end loop;

for dettaglio in (
 	(select *
	from cdst.registro_email_dettaglio dett join cdst.email_status st on st.codice = dett.stato_email
	where fk_registro_email_testata = v_id_testata
	and stato_email not in ('INOLTRATO', 'ERRORETRASMISSIONE')
	order by dt_sent_email)
 )
 loop
   status := (dettaglio.stato_email, dettaglio.dt_sent_email, dettaglio.descrizione, dettaglio.nota);
 end loop;
																					   
 RETURN status;
END;

$BODY$;

CREATE OR REPLACE VIEW cdst.cruscotto_pec AS
 SELECT testata.id_email_testata,
    testata.id_messaggio,
    testata.fk_conferenza AS id_conferenza,
    testata.mittente,
    testata.email AS email_destinatario,
    testata.destinatario,
    testata.fk_tipo_evento AS codice_tipo_evento,
    tipe.descrizione AS descr_tipo_evento,
    ( SELECT status.nota
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM cdst.registro_email_status(testata.id_email_testata) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS status_message,
    ( SELECT status.codice_stato
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM cdst.registro_email_status(testata.id_email_testata) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS codice_stato_pec,
    ( SELECT status.descrizione
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM cdst.registro_email_status(testata.id_email_testata) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS descr_stato_pec,
    ( SELECT status.data
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM cdst.registro_email_status(testata.id_email_testata) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS data_invio
   FROM cdst.registro_email_testata testata
     JOIN cdst.tipo_evento tipe ON tipe.codice::text = testata.fk_tipo_evento::text;
				 
CREATE OR REPLACE VIEW cdst.report_email_sample AS
 WITH registro_testata AS (
         SELECT ret.id_messaggio,
            max(det.dt_sent_email) AS dt_sent_email
           FROM cdst.registro_email_testata ret
             JOIN cdst.registro_email_dettaglio det ON det.id_messaggio::text = ret.id_messaggio::text
          WHERE NOT det.stato_email::text = 'INOLTRATO'::text
          GROUP BY ret.id_messaggio
        ), registro AS (
         SELECT 'meetpad@emarche.it'::text AS email_mittente,
            ret.email AS email_destinatario,
            ret.fase_email,
            ret.fk_conferenza AS id_conferenza,
            ret.id_messaggio,
            ret.subject,
            det.dt_sent_email,
            det.stato_email,
            det.nota
           FROM cdst.registro_email_testata ret
             JOIN cdst.registro_email_dettaglio det ON det.id_messaggio::text = ret.id_messaggio::text
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
     JOIN registro rr ON rr.id_messaggio::text = rt.id_messaggio::text AND rr.dt_sent_email = rt.dt_sent_email;

CREATE OR REPLACE VIEW cdst.registro_email_errori AS
 WITH registro AS (
         SELECT 'meetpad@emarche.it'::text AS email_mittente,
            ret.email AS email_destinatario,
            ret.fase_email,
            ret.fk_conferenza AS id_conferenza,
            det.id_messaggio,
            ret.subject,
            det.dt_sent_email,
            det.stato_email,
            det.nota
           FROM cdst.registro_email_dettaglio det
             LEFT JOIN cdst.registro_email_testata ret ON det.id_messaggio::text = ret.id_messaggio::text
          WHERE 1 = 1 AND det.stato_email::text = 'ERRORETRASMISSIONE'::text AND ret.email IS NOT NULL
        )
 SELECT count(*) AS tentativi,
    registro.id_messaggio,
    registro.id_conferenza,
    registro.email_destinatario
   FROM registro
  WHERE 1 = 1 AND registro.email_destinatario IS NOT NULL AND NOT btrim(registro.email_destinatario::text) = ''::text
  GROUP BY registro.id_messaggio, registro.id_conferenza, registro.email_destinatario;	
				 
CREATE OR REPLACE VIEW cdst.registro_email_errorirecuperati AS
 WITH registro AS (
         SELECT 'meetpad@emarche.it'::text AS email_mittente,
            ret.email AS email_destinatario,
            ret.fase_email,
            ret.fk_conferenza AS id_conferenza,
            det_1.id_messaggio,
            ret.subject,
            det_1.dt_sent_email,
            det_1.stato_email,
            det_1.nota
           FROM cdst.registro_email_dettaglio det_1
             LEFT JOIN cdst.registro_email_testata ret ON det_1.id_messaggio::text = ret.id_messaggio::text
          WHERE 1 = 1 AND det_1.stato_email::text = 'INOLTRATO'::text AND ret.email IS NOT NULL
        )
 SELECT count(*) AS tentativi,
    ret2.id_messaggio,
    ret2.id_conferenza,
    ret2.email_destinatario
   FROM registro ret2
     JOIN cdst.registro_email_errori det ON det.id_messaggio::text = ret2.id_messaggio::text
  WHERE 1 = 1
  GROUP BY ret2.id_messaggio, ret2.id_conferenza, ret2.email_destinatario;

			 