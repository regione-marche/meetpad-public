CREATE TABLE cdst.competenza_autorizzativa
(
    codice character varying(255) COLLATE pg_catalog."default" NOT NULL,
    descrizione character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT competenza_autorizzativa_pkey PRIMARY KEY (codice)
);

INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('1', ' Autorizzazione su strade di competenza');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('2', 'Compatibilita'' Piani di Bacino/Distretto, o loro stralci funzionali (PAI) – Rischio gravitativo, frana, dissesto idrogeologico. Area di competenza');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('3', 'Autorizzazioni per Interventi su fasce di rispetto o di pertinenza autostradali');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('4', 'Autorizzazione ex. Art. 21 del Codice');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('5', 'Tutela Archeologica ex. Art. 28 del Codice');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('6', 'Tutela del patrimonio storico-architettonico');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('7', 'Autorizzazione paesaggistica ex art 146 del Codice');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('8', 'Valutazione di incidenza Ambientale VIncA – sito appartenente alla rete Natura 2000 per Provincia di appartenenza');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('9', 'Autorizzazioni strade provinciali');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('10', 'Parere Unico del Servizio Tutela Gestione ed Assetto del Territorio');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('11', 'Nulla Osta Vincolo idrogeologico ex art. 7 del R.D. del 30/12/1923, n. 3267');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('12', 'Autorizzazione/Nulla Osta idraulico ex R.D. n. 523 del 1904 per opere e manufatti che occupino, in subalveo o in proiezione, l''alveo di un corso d''acqua o comunque tutti quegli interventi che possono avere relazione con il regime delle acque');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('13', 'Autorizzazione di tagli boschivi nelle aree esterne alle Unioni Montane (art. 10 L.R. n. 6/2005)');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('14', 'Parere obbligatorio e vincolante ai sensi art. 9 delle norme di attuazione del PAI se l''intervento ricade in aree esondabili');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('15', 'Costruzioni in zona sismica');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('16', 'Autorizzazione interventi su strade di proprieta'' regionale');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('17', 'Autorizzazione al taglio dei boschi');
INSERT INTO cdst.competenza_autorizzativa (codice, descrizione) VALUES ('18', 'Altro');

CREATE TABLE cdst.competenza_autorizzativa_tipologia_conferenza
(
    codice_competenza_autorizzativa character varying(255) NOT NULL,
    codice_tipologia_conferenza character varying(255) NOT NULL,
    CONSTRAINT codice_competenza_autorizzativa FOREIGN KEY (codice_competenza_autorizzativa)
        REFERENCES cdst.competenza_autorizzativa (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT codice_tipologia_conferenza FOREIGN KEY (codice_tipologia_conferenza)
        REFERENCES cdst.tipologia_conferenza (codice) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

DO
$do$
DECLARE 
 tipo RECORD;
 competenza RECORD;
BEGIN
 for tipo in (select *
	from cdst.tipologia_conferenza tc
	)
 loop
	for competenza in (select *
	from cdst.competenza_autorizzativa ca
	)
	loop
		IF (tipo.codice in ('4', '5')) THEN
		INSERT INTO cdst.competenza_autorizzativa_tipologia_conferenza(codice_competenza_autorizzativa, codice_tipologia_conferenza)
		VALUES (competenza.codice, tipo.codice) ON CONFLICT DO NOTHING;
		END IF;											
	end loop;
 end loop;
 end
 $do$;