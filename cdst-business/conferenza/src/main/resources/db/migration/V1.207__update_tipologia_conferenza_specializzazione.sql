--aggiorniamo questo campo che in test è pari a 3 e in prod pari a 4 e non permette di visualizzare gli eventi usr

update tipologia_conferenza_specializzazione set fk_tipologia_conferenza = '6' where descrizione = 'tipologiaConferenzaSpec.domus'