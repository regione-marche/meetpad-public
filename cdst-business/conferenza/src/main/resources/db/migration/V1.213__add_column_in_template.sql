ALTER TABLE cdst.template ADD column fk_azione character varying(255);


-- BACKUP TABELLA TEMPLATE TEST PER ERRORE SCARICAMENTO TEMPLATE IN NOTA DI INVITO
INSERT INTO cdst.template
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_indizione_meetpad', '2', 'indizione_ord_33', '6', '212');
INSERT INTO cdst.template
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_indizione_meetpad', '2', 'indizione_ord_56', '6', '215');
INSERT INTO cdst.template
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione, fk_azione)
VALUES('template_new_indizione_meetpad', '2', 'indizione_ord_37', '6', '213');


update cdst.template set fk_azione = '202' where nome_template_download = 'indizione_ord_19'