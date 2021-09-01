alter table registro_firma_adapter alter column dt_firma_ins SET DATA TYPE timestamp;
alter table registro_firma_adapter alter column dt_firma_var SET DATA TYPE timestamp;
alter table registro_firma_adapter drop  column id_documento;
alter table registro_firma_adapter add column id_documento  integer;
alter table registro_firma_adapter drop  column id_user;
alter table registro_firma_adapter add column id_user  integer;