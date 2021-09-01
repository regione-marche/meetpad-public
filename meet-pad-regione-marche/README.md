# Conferenze dei Servizi Telematica - INAIL

## Tabella dei contenuti

-   [Installazione dipenendeze](#installazione-dipenendeze)
-   [Clienti Attuali](#clienti)
-   [Nuovi clienti](#nuovi-clienti)

## Installazione dipenendeze

Il software dipende da un paio di pacchetti npm Enginnering presenti sul Nexus NPM aziendale: seguire le [istruzioni](https://engit-my.sharepoint.com/:w:/g/personal/federico_dainelli_eng_it/ER4rqnFQYSpBiRQ6uo47V1QBk23xJQokQoCL6w0NwGTwfg?e=OpYFXx) per ottenere gli accessi al repository.

## Clienti

-   MeetPAp - Regione Marche
-   Suap - Regione Lazio

Il repository contienete i sorgenti del prodotto suddiviso in branch per cliente:

-   **master**: branch che contiene il template/layout grafico senza funzionalità specifiche
-   **marche/develop**: branch che contiene l'ambiente di sviluppo MeetPAd - Regione Marche sul quale è presente l'ultima versione (instabile).
-   **marche/master**: branch che contiene l'ambiente di produzione MeetPAd - Regione Marche sul quale è presente la versione rilasciata in produzione (stabile).
-   **lazio/develop**: branch che contiene l'ambiente di sviluppo Suap - Regione Lazio sul quale è presente l'ultima versione (instabile).
-   **lazio/master**: branch che contiene l'ambiente di produzione Suap - Regione Lazio sul quale è presente la versione rilasciata in produzione (stabile).
-   **features/**: cartella di branch per le funzionalità

Lo sviluppo di nuove funzionalità viene eseguito su nuovi branch, uno per funzionalità, i quali vengono poi riversati sul **develop**.

Inizialmente gli ambienti Suap e MeetPAd sono stati mantenuti allineati mentre attualmente hanno requisiti e quindi funzionalità diverse.

Andare sugli specifici branch **marche/develop** e **lazio/develop** per avere il README.md relativo.

## Nuovi clienti

Per aggiungere un nuovo cliente sarà necessario conoscere:

-   specifiche di progetto
    -   funzionali
    -   template grafico
-   ambienti di rilascio

Da queste informazioni sarà possibile capire se creare il progetto a partire:

-   dal master
-   da marche/develop
-   da lazio/develop

Questi tre branch sono molto diversi tra loro in termini di:

-   scripts per gestire il ciclo di vita del software
-   funzionalità
-   template grafico
