INSERT INTO cdst.modalita (codice, descrizione) VALUES ('1', 'Videoconferenza') ON CONFLICT DO NOTHING;
INSERT INTO cdst.modalita (codice, descrizione) VALUES ('2', 'Riunione') ON CONFLICT DO NOTHING;
INSERT INTO cdst.modalita (codice, descrizione) VALUES ('3', 'Online') ON CONFLICT DO NOTHING;