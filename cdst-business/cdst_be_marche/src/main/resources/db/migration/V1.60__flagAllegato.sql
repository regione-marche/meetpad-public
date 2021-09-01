ALTER TABLE cdst.tipo_evento ADD COLUMN flag_allegato boolean;

UPDATE cdst.tipo_evento	SET flag_allegato = 'false' WHERE codice = '1';
UPDATE cdst.tipo_evento	SET flag_allegato = 'true' WHERE codice = '2';
UPDATE cdst.tipo_evento	SET flag_allegato = 'true' WHERE codice = '3';
UPDATE cdst.tipo_evento	SET flag_allegato = 'false' WHERE codice = '4';
UPDATE cdst.tipo_evento	SET flag_allegato = 'true' WHERE codice = '5';
UPDATE cdst.tipo_evento	SET flag_allegato = 'true' WHERE codice = '6';
UPDATE cdst.tipo_evento	SET flag_invio_email = 'true', flag_allegato = 'true' WHERE codice = '7';
UPDATE cdst.tipo_evento	SET flag_allegato = 'false' WHERE codice = '8';
UPDATE cdst.tipo_evento	SET flag_allegato = 'true' WHERE codice = '9';
UPDATE cdst.tipo_evento	SET flag_allegato = 'false' WHERE codice = '10';
UPDATE cdst.tipo_evento	SET flag_allegato = 'true' WHERE codice = '11';
UPDATE cdst.tipo_evento	SET flag_invio_email = 'true', flag_allegato = 'true' WHERE codice = '12';