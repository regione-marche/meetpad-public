/*
marshaller per firma multipla..

*/
insert into tipo_firma (id,tipofirma,descrizione,marshalling)values(4,'FSFIRMAMULTIPLA','Mearshaller per Firma Multipla','conferenza.firma.adapter.FSFirmaMultiplaAdapter');

-- nuovi stati per la firma multipla
insert into cdst.stato_firma (id,stato,descrizione)values
(6,'DRAFT','Il file è stato messo in bozza');

insert into cdst.stato_firma (id,stato,descrizione)values
(7,'REJECTED','Il file è stato rifiutato');

insert into cdst.stato_firma (id,stato,descrizione)values
(8,'SIGNED','Il file è stato firmato con firma multipla');