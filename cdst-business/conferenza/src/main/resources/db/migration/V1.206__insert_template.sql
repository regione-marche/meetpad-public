update template set nome_template_download='indizione_ord_19' 
	where fk_tipologia_conferenza_specializzazione='6' and fk_tipo_evento='2';
	
INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione)
VALUES('template_new_indizione_meetpad', '2', 'indizione_ord_33', '6');

INSERT INTO cdst."template"
(nome_template, fk_tipo_evento, nome_template_download, fk_tipologia_conferenza_specializzazione)
VALUES('template_new_indizione_meetpad', '2', 'indizione_ord_56', '6');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('25','indizione_ord_19', '6');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('26','indizione_ord_33', '6');

INSERT INTO cdst.modello
(codice, descrizione, fk_tipologia_conferenza_specializzazione)
VALUES('27','indizione_ord_56', '6');