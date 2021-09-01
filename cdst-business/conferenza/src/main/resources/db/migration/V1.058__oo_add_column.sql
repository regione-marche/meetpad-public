
/**
aggiunta colonna per tenere in parallelo
sia i riferimenti FS che ONLYOFFICE
in attesa di fare lo switch su file system
OLYOFFICE

*/

alter table oo_adapter_versioni add  IF NOT EXISTS  id_oo_file integer;