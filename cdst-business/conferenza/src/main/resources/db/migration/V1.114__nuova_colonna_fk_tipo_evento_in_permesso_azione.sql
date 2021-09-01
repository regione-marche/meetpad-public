ALTER TABLE cdst.permesso_azione ADD COLUMN fk_tipo_evento character varying(255);

UPDATE cdst.permesso_azione SET fk_tipo_evento='10' WHERE codice='INSERT_EVENTO_CHIUSURA_CONFERENZA';
UPDATE cdst.permesso_azione SET fk_tipo_evento='4' WHERE codice='INSERT_EVENTO_CHIUSURA_INTEGRAZIONI';
UPDATE cdst.permesso_azione SET fk_tipo_evento='5' WHERE codice='INSERT_EVENTO_RICHIESTA_UNICA_INTEGRAZIONI';
UPDATE cdst.permesso_azione SET fk_tipo_evento='7' WHERE codice='INSERT_EVENTO_COMUNICAZIONE_GENERICA';
UPDATE cdst.permesso_azione SET fk_tipo_evento='13' WHERE codice='INSERT_EVENTO_TRASMISSIONE_INTEGRAZIONI_PROTOCOLLATE';
UPDATE cdst.permesso_azione SET fk_tipo_evento='12' WHERE codice='INSERT_EVENTO_DETERMINAZIONE_FINALE';
UPDATE cdst.permesso_azione SET fk_tipo_evento='9' WHERE codice='INSERT_EVENTO_CARICAMENTO_VERBALE_RIUNIONE';
UPDATE cdst.permesso_azione SET fk_tipo_evento='3' WHERE codice='INSERT_EVENTO_RICHIESTA_INTEGRAZIONI';
UPDATE cdst.permesso_azione SET fk_tipo_evento='11' WHERE codice='INSERT_EVENTO_ESPRESSIONE_PARERI';
UPDATE cdst.permesso_azione SET fk_tipo_evento='6' WHERE codice='INSERT_EVENTO_INVIA_INTEGRAZIONI'; 