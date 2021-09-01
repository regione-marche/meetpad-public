INSERT INTO cdst.categoria_documento(codice, descrizione) VALUES ('3', 'categoriaDocumento.documentoIntegrativo') ON CONFLICT DO NOTHING;
INSERT INTO cdst.categoria_documento(codice, descrizione) VALUES ('4', 'categoriaDocumento.nota') ON CONFLICT DO NOTHING;
INSERT INTO cdst.categoria_documento(codice, descrizione) VALUES ('5', 'categoriaDocumento.altro') ON CONFLICT DO NOTHING;