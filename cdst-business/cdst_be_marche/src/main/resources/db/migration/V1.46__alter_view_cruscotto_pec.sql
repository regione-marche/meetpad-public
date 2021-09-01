DROP VIEW cdst.cruscotto_pec;

CREATE OR REPLACE VIEW cdst.cruscotto_pec AS
 SELECT testata.id_messaggio,
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
                   FROM registro_email_status(testata.id_messaggio) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS status_message,
    ( SELECT status.codice_stato
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM registro_email_status(testata.id_messaggio) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS codice_stato_pec,
    ( SELECT status.descrizione
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM registro_email_status(testata.id_messaggio) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS descr_stato_pec,
    ( SELECT status.data
           FROM ( SELECT f.codice_stato,
                    f.data,
                    f.descrizione,
                    f.nota
                   FROM registro_email_status(testata.id_messaggio) f(codice_stato character varying, data timestamp without time zone, descrizione character varying, nota character varying)) status) AS data_invio
   FROM registro_email_testata testata
     JOIN tipo_evento tipe ON tipe.codice::text = testata.fk_tipo_evento::text
  WHERE testata.id_messaggio IS NOT NULL
  GROUP BY testata.id_messaggio, testata.fk_conferenza, testata.mittente, testata.email, testata.destinatario, testata.fk_tipo_evento, tipe.descrizione;

ALTER TABLE cdst.cruscotto_pec
    OWNER TO cdst;

