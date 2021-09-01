/*
fix invio mail indizione domus
*/
update template set nome_template = 'template_new_indizione_meetpad' where fk_tipo_evento = '2' AND fk_tipologia_conferenza_specializzazione IN ('6')

