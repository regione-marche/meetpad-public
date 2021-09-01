INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale chiese ord. 23-32', '6', '210');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale decreto ord. 27', '6', '211');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale ord. 13', '6', '201');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale ord. 33', '6', '212');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale ord. 37', '6', '213');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale ord. 48', '6', '214');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale ord. 56', '6', '215');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_verbale_meetpad', '31', 'verbale ord.4', '6', '200');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_meetpad', '31', 'verbale ord. 19', '6', '202');




UPDATE cdst.modello
SET descrizione='verbale ord.4'
WHERE codice='42';

UPDATE cdst.modello
SET descrizione='INDIZIONE ORD. 4 - 8'
WHERE codice='30';

UPDATE cdst.modello
SET descrizione='indizione_ord_19'
WHERE codice='25';

UPDATE cdst.modello
SET descrizione='indizione_ord_37'
WHERE codice='28';

UPDATE cdst.modello
SET descrizione='indizione_ord_56'
WHERE codice='27';
