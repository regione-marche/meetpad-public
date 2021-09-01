CREATE TABLE cdst.ente_tipologia_istat
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(1000) COLLATE pg_catalog."default",
    CONSTRAINT ente_tipologia_istat_pkey PRIMARY KEY (codice)
);

CREATE TABLE cdst.ente_tipologia_amministrativa
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(1000) COLLATE pg_catalog."default",
    CONSTRAINT ente_tipologia_amministrativa_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('1', 'Aziende Ospedaliere, Aziende Ospedaliere Universitarie, Policlinici e Istituti di Ricovero e Cura a Carattere Scientifico Pubblici');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('2', 'Altri Enti Locali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('3', 'Consorzi tra Amministrazioni Locali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('4', 'Universita'' e Istituti di Istruzione Universitaria Pubblici');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('5', 'Citta'' Metropolitane');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('6', 'Istituti di Istruzione Statale di Ogni Ordine e Grado');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('7', 'Province e loro Consorzi e Associazioni');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('8', 'Unioni di Comuni e loro Consorzi e Associazioni');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('9', 'Automobile Club Federati ACI');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('10', 'Aziende ed Amministrazioni dello Stato ad Ordinamento Autonomo');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('11', 'Presidenza del Consiglio dei Ministri, Ministeri e Avvocatura dello Stato');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('12', 'Agenzie ed Enti Regionali di Sviluppo Agricolo');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('13', 'Agenzie Regionali Sanitarie');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('14', 'Istituti Zooprofilattici Sperimentali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('15', 'Enti e Istituzioni di Ricerca Pubblici');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('16', 'Agenzie ed Enti Regionali per la Formazione, la Ricerca e l''Ambiente');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('17', 'Aziende e Consorzi Pubblici Territoriali per l''Edilizia Residenziale');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('18', 'Enti Pubblici Produttori di Servizi Assistenziali, Ricreativi e Culturali ');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('19', 'Istituzioni per l''Alta Formazione Artistica, Musicale e Coreutica - AFAM');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('20', 'Parchi Nazionali, Consorzi e Enti Gestori di Parchi e Aree Naturali Protette');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('21', 'Agenzie ed Enti per il Turismo');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('22', 'Societa'' in Conto Economico Consolidato');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('23', 'Consorzi di Bacino Imbrifero Montano');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('24', 'Autorita'' Amministrative Indipendenti');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('25', 'Agenzie Fiscali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('26', 'Agenzie ed Enti Regionali del Lavoro');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('27', 'Comuni e loro Consorzi e Associazioni');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('28', 'Aziende Pubbliche di Servizi alla Persona');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('29', 'Enti Pubblici Non Economici');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('30', 'Federazioni Nazionali, Ordini, Collegi e Consigli Professionali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('31', 'Enti di Regolazione dei Servizi Idrici e o dei Rifiuti');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('32', 'Teatri Stabili ad Iniziativa Pubblica');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('33', 'Fondazioni Lirico, Sinfoniche');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('34', 'Agenzie, Enti e Consorzi Pubblici per il Diritto allo Studio Universitario');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('35', 'Autorita'' Portuali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('36', 'Comunita'' Montane e loro Consorzi e Associazioni');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('37', 'Regioni, Province Autonome e loro Consorzi e Associazioni');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('38', 'Camere di Commercio, Industria, Artigianato e Agricoltura e loro Unioni Regionali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('39', 'Organi Costituzionali e di Rilievo Costituzionale');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('40', 'Consorzi per l''Area di Sviluppo Industriale');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('41', 'Consorzi Interuniversitari di Ricerca');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('42', 'Gestori di Pubblici Servizi');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('43', 'Aziende Sanitarie Locali');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('44', 'Agenzie Regionali per le Erogazioni in Agricoltura');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('45', 'Forze di Polizia ad Ordinamento Civile e Militare per la Tutela dell''Ordine e della Sicurezza Pubblica');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('46', 'Enti Nazionali di Previdenza ed Assistenza Sociale in Conto Economico Consolidato');
INSERT INTO cdst.ente_tipologia_istat(codice, descrizione) VALUES ('47', 'Autorita'' di Bacino');

INSERT INTO cdst.ente_tipologia_amministrativa(codice, descrizione) VALUES ('1', 'Gestori di Pubblici Servizi');
INSERT INTO cdst.ente_tipologia_amministrativa(codice, descrizione) VALUES ('2', 'Pubbliche Amministrazioni');
INSERT INTO cdst.ente_tipologia_amministrativa(codice, descrizione) VALUES ('3', 'Enti Nazionali di Previdenza ed Assistenza Sociale in Conto Economico Consolidato');
INSERT INTO cdst.ente_tipologia_amministrativa(codice, descrizione) VALUES ('4', 'Societa'' in Conto Economico Consolidato');
