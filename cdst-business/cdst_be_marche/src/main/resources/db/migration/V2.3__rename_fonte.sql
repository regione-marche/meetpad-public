UPDATE cdst.registro_documento SET fonte=null;
delete from cdst.registro_documento_fonte where codice = 'MEETPAD';
insert into cdst.registro_documento_fonte(codice, descrizione) values ('CONFERENZA', 'da applicativo CONFERENZA') ON CONFLICT DO NOTHING;
UPDATE cdst.registro_documento SET fonte='CONFERENZA';