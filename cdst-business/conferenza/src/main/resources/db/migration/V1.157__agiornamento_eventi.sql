/**
Modifica custom per Creatore CDS

*/
update Permesso set permission_strategy='' where fk_permesso_ruolo='CREATORE' and fk_permesso_azione='INSERT_EVENTO_DETERMINAZIONE_FINALE'