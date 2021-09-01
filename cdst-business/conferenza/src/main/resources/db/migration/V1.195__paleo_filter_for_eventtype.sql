
ALTER TABLE cdst.observer_registry ADD COLUMN priority numeric(5);

delete FROM cdst.observer_registry where subrscribed_event_type='9';

INSERT INTO cdst.observer_registry (id, codice, nome, subrscribed_event_type, "class", protocol_event_type, fk_conferenza_specializzazione)
VALUES(NULL, 'SCRITTURA-DOCUMENT-INTRA-PALEO', 'Archiviazione documentale', '9', 'PaleoObserverDocumentaleIntraListnerInterface', '23', NULL);

update cdst.observer_registry set priority=cast(subrscribed_event_type as int) where priority is null;

