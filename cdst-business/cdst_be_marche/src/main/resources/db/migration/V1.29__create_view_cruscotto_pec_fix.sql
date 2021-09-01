DROP VIEW cdst.cruscotto_pec;

CREATE OR REPLACE VIEW cdst.cruscotto_pec AS
 SELECT '<365402303.2.1541697973261@DESKTOP-486DODU>'::text AS id_messaggio,
    1 AS id_conferenza,
    'mittente'::text AS mittente,
	'prova@pec.it'::text AS email_destinatario,
    'destinatario'::text AS destinatario,
    '2'::text AS codice_tipo_evento,
    'Indizione'::text AS descr_tipo_evento,
    'status_message'::text AS status_message,
    'INOLTRATO'::text AS codice_stato_pec,
    'Inoltrato'::text AS descr_stato_pec,
    now() AS data_invio;

ALTER TABLE cdst.cruscotto_pec
    OWNER TO cdst;
