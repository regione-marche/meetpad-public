# Conferenze dei Servizi Telematica - INAIL

## Tabella dei contenuti

-   [Installazione dipenendeze](#installazione-dipenendeze)
-   [Ambienti](#ambienti)
-   [Comandi NPM](#comandi-npm)
    -   [Development server](#development-server)
    -   [Release](#release)
    -   [Distribute](#distribute)
    -   [Generazione documentazione](#generazione-documentazione)
-   [Convenzioni](#convenzioni)
    -   [Versioning](#versioning)
    -   [Commit](#commit)

## Installazione dipenendeze

Il software dipende da un paio di pacchetti npm Enginnering presenti sul Nexus NPM aziendale: seguire le [istruzioni](https://engit-my.sharepoint.com/:w:/g/personal/federico_dainelli_eng_it/ER4rqnFQYSpBiRQ6uo47V1QBk23xJQokQoCL6w0NwGTwfg?e=OpYFXx) per ottenere gli accessi al repository.

## Ambienti

Clienti/ambienti:

-   MeetPAp - Regione Marche
    -   ambiente di sviluppo:
        -   url: https://wso2.meetpad-dev.eng.it/meet-pad-svil/
        -   host Pont-Saint-Martin: 161.27.206.16
        -   document_root: /var/www/html/meet-pad
    -   ambiente di staging:
        -   url: https://wso2.meetpad-dev.eng.it/meet-pad/
        -   host Pont-Saint-Martin: 161.27.206.16
        -   document_root: /var/www/html/meet-pad-svil
    -   ambiente di test (VPN Regione Marche):
        -   url: https://meetpad-test.regione.marche.it
        -   host: 10.101.11.134
        -   document_root: /var/www/html/meet-pad
    -   ambiente di prod (VPN Regione Marche):
        -   url: https://meetpad.regione.marche.it
        -   host: 10.101.11.120
        -   document_root: /var/www/html/meet-pad
-   Suap - Regione Lazio
    -   ambiente di sviluppo:
        -   url: https://suaplazio.esf.eng.it/conferenza-suap/
        -   host Pont-Saint-Martin: 161.27.206.16
        -   document_root: /var/www/html/drupalcsi/conferenza-suap
    -   ambiente di produzione

Il repository contienete i sorgenti del prodotto suddiviso in branch per cliente:

-   **master**: branch che contiene il template/layout grafico senza funzionalità specifiche
-   **marche/develop**: branch che contiene l'ambiente di sviluppo MeetPAd - Regione Marche sul quale è presente l'ultima versione (instabile).
-   **marche/master**: branch che contiene l'ambiente di produzione MeetPAd - Regione Marche sul quale è presente la versione rilasciata in produzione (stabile).
-   **lazio/develop**: branch che contiene l'ambiente di sviluppo Suap - Regione Lazio sul quale è presente l'ultima versione (instabile).
-   **lazio/master**: branch che contiene l'ambiente di produzione Suap - Regione Lazio sul quale è presente la versione rilasciata in produzione (stabile).
-   **features/**: cartella di branch per le funzionalità

Lo sviluppo di nuove funzionalità viene eseguito su nuovi branch, uno per funzionalità, i quali vengono poi riversati sul **develop**.

Inizialmente gli ambienti Suap e MeetPAd sono stati mantenuti allineati mentre attualmente hanno requisiti e quindi funzionalità diverse. NB.

## Comandi NPM

Sul file **_package.json_** sono definiti gli script per la gestione del ciclio di vita del software in funzione degli ambienti/branch:

-   sviluppo
-   rilascio
-   distribuzione
-   generazione documentazione navigabile.

Gli script per eseguire il server di sviluppo in locale e per eseguire il rilascio di una nuova versione hanno un controllo angular envinronment/branch, configurabile; questo è utile ad evitare casi non reali o inconsistenti (es: run suap env on marche-develop/marche-staging branch or run local env on marche-staging/lazio-develop branch).

### Development server

Suddivisione per ambienti (`npm run` può essere sostituito da `yarn` se installato):

-   `npm run serve`: comando per avviare l'ambiente di sviluppo in locale
-   `npm run release-distribute:env`: comando per rilasciare e distribuire l'applicazione sui vari ambienti in base al valore di env:
    -   dev
    -   staging
    -   test
    -   prod

Deprecati:

-   `npm run mocks`: comando per avviare l server locale che fornisce i mocks delle api

Sul file [scripts/serve/conf.ts](./scripts/serve/conf.ts) è possibile configurare nuovi branch da eseguire su determinati ambienti.

### Release

I rilasci vengono eseguiti grazie al pacchetto npm [release-it](https://www.npmjs.com/package/release-it) il quale automatizza alcuni task:

-   gestione della versione da rilasciare
-   commitb
-   tag
-   push

Task configurabili da [scripts/release/index.ts](./scripts/release/index.ts).

### Distribute

I rilasci vengono eseguiti grazie al pacchetto npm [mobile-app-distribution](https://github.com/ruddenchaux/mobile-app-distribution) il quale automatizza alcuni task:

-   build
-   aggiornamento automatico della versione sul footer dell'app e sulla console
-   deploy sul server

Task configurabili da [scripts/distribute/index.ts](./scripts/distribute/index.ts#L26).

Le configurazioni di build e di deploy sono presenti nella cartella [scripts/distribute/environments](./scripts/distribute/environments).

### Generazione documentazione

E' presente la dipendenza di sviluppo [@compodoc/compodoc](https://www.npmjs.com/package/@compodoc/compodoc) per generare la documentazione
del sorgente come sito navigabile.

Comandi:

-   `npm run docs:build`: crea la documentazione all'interno della cartella _doc_
-   `npm run docs:serve`: crea la documentazione e il server per navigarla in locale http://127.0.0.1:8080/
-   `npm run docs:watch`: come il comando precedente con l'opzione watch (watch reload)

## Convenzioni

### Versioning

Il pacchetto **release-it** ad ogni rilascio, attraverso i comandi visti sopra, mostra nella CLI possibili versioni da rilasciare che il programmatore può scegliere: questo progetto usa lo standard [Semantic Versioning 2.0.0](https://semver.org/).

### Commit

E' stata adottata (a progetto iniziato) la convenzione per i messaggi di commit in modo da avere una semplificazione nella manutenzione del progetto (la convenzione sarebbe utile per automatizzare la generazione dei changelog per ogni versione; attualmente non è presente questo automatismo).

I rifermimenti per le convenzioni sono:

-   [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0-beta.4/)
-   [Angular JS Allowed Type](https://gist.github.com/stephenparish/9941e89d80e2bc58a153#allowed-type)
