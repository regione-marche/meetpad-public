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
