INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('7', 'Espressione Pareri') ON CONFLICT DO NOTHING;
INSERT INTO cdst.oggetto_evento (codice, descrizione) VALUES ('8', 'Determinazione Finale') ON CONFLICT DO NOTHING;

INSERT INTO cdst.tipologia_documento (codice, descrizione) VALUES ('ESPRESSIONE_PARERI', 'tipologiaDocumento.espressionePareri') ON CONFLICT DO NOTHING;

INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento, fk_tipologia_documento) VALUES ('11', 'Espressione Pareri', '7', 'ESPRESSIONE_PARERI') ON CONFLICT DO NOTHING;
INSERT INTO cdst.tipo_evento (codice, descrizione, fk_oggetto_evento, fk_tipologia_documento) VALUES ('12', 'Determinazione Finale', '8', 'DETERMINA') ON CONFLICT DO NOTHING;