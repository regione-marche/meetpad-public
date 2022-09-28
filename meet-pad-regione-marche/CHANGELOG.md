## [Unrelased]

### Added

### Fixed

## [1.21.16] - 2019-10-24

### Added

-   removed fields protocolo/date protocol for tipology 8,9,10,11
-   removed protocol fields for domus

### Fixed

-   fix label conferenza
-   Fix inserimento partecipanti pannello amministrativo
-   Fix modifica dati richiedente con conseguente aggiornamento dei parteipanti

## [1.21.15] - 2019-10-17

### Added

-   eliminati campi protocollo/data protocollo per le tipologia di specializzazione conferenza 8,9,10,11 
-   Aggiunta tipologia conferenza "Incontro operativo"
-   Inserita la gestione dell'errore nel salvataggio dell'utente associato al partecipante
-   Inserimento nuove tiplogie conferenza ambiente

### Fixed

## [1.21.14] - 2019-09-18

### Added

-   Inserimento flag per la dichiarazione della conformità dei file caricati
-   Modificata la gesione del fallimento del caricamento dei file associati ai documenti 

### Fixed

## [1.21.13] - 2019-09-11

### Added

-   Inserimento nuovo evento Modifica Date
-   Inserimento nuovo tab nella sezione Definizione con la lista delle Date modificate

### Fixed

## [1.21.12] - 2019-08-23

### Added

-   Inserito bottone per la sincronizzazione dei documenti di istanza con Domus
-   Inserita la possibilità della rimozione multipla dei documenti di istanza

### Fixed

## [1.21.11] - 2019-08-02

### Added

### Fixed

-   Fix ticket V20190719-TKT20190802.1

## [1.21.10] - 2019-07-25

### Added

### Fixed

-   Fix gestione unlock del file firmato

## [1.21.9] - 2019-07-25

### Added

-   Inserita nuova chiamata per il lock con download del file firmato, sbloccato il download del file firmato in tutte le fasi della sessione di firma. Aumentato il timeout nelle chamate della sessione di firma

### Fixed

## [1.21.8] - 2019-07-23

### Added

### Fixed

-   Fix ticket [TKT20190722.2]

## [1.21.7] - 2019-07-19

### Added

-   Inserimento logo pro-fesr nel footer 
-   Aggiornamento sezione open data con la lista di tutti i tipi di record e lo scaricamento del report.

### Fixed

-   Fix ticket [TKT20190705.1]
-   Fix ticket [PROD20190716.1]
-   Fix ticket [TKT20190708.1]

## [1.21.6] - 2019-07-16

### Added

-   Inserito alert con il link per lo scaricamento dell'applicazione Calamaio.

## [1.21.5] - 2019-07-16

### Added

-   Aggiunta il supporto per la firma dei documenti con l'applicazione Calamaio, 

## [1.21.4] - 2019-07-09

### Fixed

-   Rimosso il problema sulle rotte in fase di post login (relativo al [bug di angular](https://github.com/angular/angular/pull/25483))

## [1.21.3] - 2019-07-04

### Fixed

-   Rimosso il problema dell'invio dell'authorization bearer dalle modali

## [1.21.2] - 2019-07-03

### Fixed

-   Votazioni
-   Rimosso il problema dell'invio dell'authorization bearer dalle modali

### Added

-   Aggiunta data votazione nella votazione di tipo Calendarizzazione

## [1.21.1] - 2019-06-18

### Fixed

-   Firma

## [1.21.0] - 2019-06-18

### Added

-   Parte della Firma
-   Aggiunta creazione conferenza Domus

### Changed

-   Trasformato paleo in lazy loading

## [1.20.0] - 2019-06-15

### Added

-   Aggiunta la pagina changelog delle versione per utilità
-   Aggiunto alert per scaricare il modello della nota di invito precompilata sualla relativa documentazione
-   Aggiunto alert per BUL-Decisoria pre indizione: "Effettuare l'uplod di soli file firmati digitalmente"
-   Aggiunto alert per BUL-Decisoria post indizione: "Gli elaborati presenti sono copia conforme di quelli presentati presso il comune capofila"

### Changed

-   Spostato il componente AlertWithLinkComponent sulla @common

## [1.19.8] - 2019-06-10

### Fixed

-   Fix scelta categoria sulla documentazione

## [1.19.7] - 2019-06-05

### Added

-   Configurazione oauth2 prima di eseguire la logout

### Fixed

-   Fix logout non funzionante dopo il ricaricamento della pagina
-   Fix click menù login

## [1.19.6] - 2019-05-31

## [1.19.5] - 2019-05-31

### Added

-   Mediateca
    -   Aggiunta pagina mediateca con ricerca e lista di tutti i doc video
    -   Aggiunta voce di menù
-   Appuinti dashboard
    -   Aggiunta la nuova modalità (Appunti Dashboard) di caricamento dei file sulla sezione documentazione condivisa
    -   Aggiunto del testo che spiega le differenze tra le varie modalità sul tooltip
-   Login SPID
    -   Aggiunta possibilità di eseguire la login differenziata: Cohesion e SPID
    -   Aggiunta login fake con SPID: attualmente punta alla default a cui punti anche Cohesion
    -   Differenziate le voci per accedere sulla topbar (visualizazione smartphone e tablet/desktop) e sul menù

### Fixed

-   Fix TKT 17: eliminazione dei file al click su annulla in creazione Paleo

## [1.19.4] - 2019-05-29

### Added

-   Aggiunto caricamento del file video dalla admin console

### Changed

-   Migliorata UX del caricamento file con drag & drop: aggiunto click sulla zona "droppabile" per navigare sul file system locale e scegliere più files

## [1.19.3] - 2019-05-27

### Fixed

-   Fix baseUrl api per l'admin console
-   Fix redirectAfterLogin

## [1.19.1] - 2019-05-24

### Added

-   Aggiunta la condizione di match del codice fiscale nel salvataggio del richiedente
-   Footer: Aggiunta la versione del FrontEnd, di seguito al copyright, in basso a sinistra

### Fixed

-   Fix refresh list dei partecipanti
-   Fix loading a tutto scermo in posizione fissa
-   Fix testi centrati in alcune card della console amministrativa
-   Fix errore sull'accrodion della documentazione nello step del summary
-   Fix del margine superiore del contenuto dello step del sommario
-   Fix sul salvataggio del primo step pratica:
-   Fix migliorata UI con una migliore gestione del loading nella fase di creazione di una conferenza
-   Fix clean code

### Removed

-   Rimossa la condizione principalApplicant nel salvataggio del richiedente

## [1.18.0] - 2019-05-19

### Added

-   Calendario: Aggiunto evento click sull'evento di calendario per entrare nel dettaglio della conferenza

### Fixed

-   Fix TKT 6: gestione errori in fase di creazione di una conferenza paleo
-   Fix TKT 7: redirect verso la conferenza paleo creata
-   Fix loading in fase di login
-   Fix posizionamento loading del layout privato
-   Fix stop loading prematuro sul tab eventi
-   Fix stop loading prematuro sulla scrivania
-   Fix loading sul nome utente nella top header
-   Fix dei margini e dei padding sul pannello di ricerca nel tab messaggi
-   Fix posizionamento del tasto + nella creazione di una conferenza paleo
-   Fix animazione "zoppa" degli accordion

## [1.17.0] - 2019-05-22

### Added

-   Aggiunto caricamento con URL in Documentazione Istanza

### Fixed

-   Fix controllo icona OnlyOffice
-   Fix label modale Chiusura integrazione
-   Fix flag richiesta unica integrazioni per l'amministrazione procedente, sulla lista dei partecipanti
-   Fix oggeto pec parlante per tutti i messaggi (esclusa la convocazione)
-   Fix della label "Dati impresa/soggetto fisico rappresentato" nel tab Pratica
-   Fix lubghezza del campo "Oggetto della conferenza" nel tab Definizione
-   Fix lista delle conferenze ordinate per id decrescente sulla Scrivania
-   Fix TKT 1: caricamento file in documentazione
-   Fix TKT 2: caricamento utenti in partecipanti
-   Fix TKT 3: date picker su documentazione
-   Fix TKT 4: date non inserite sul modale di conferma indizione
-   Fix TKT 5: downlod della nota di invio
-   Fix gestione degli errori ritornati dal BE in fase di modifica della conferenza

### Removed

-   Rimosso campo Categoria in Inserimento Documentazione Condivisa
-   Rimosso caricamento URL in Documentazione Condivisa

## [1.16.0] - 2019-05-20

### Added

-   Documentazione: Documentazione condivisa
-   Documentazione: Modelli
-   Documentazione: Caricamento con url
-   Gestione errori: Rivista la gestione degli errori migliorando l'esperienza utente
-   Gestione accessi utente: Rivisita la gestione degli accessi degli utenti in base ai casi d'uso specificati nel wiki Sommario in Test List migliorando l'esperienza utente

## [1.15.1] - 2019-05-13

### Changed

-   Accesso all'admin console

## [1.15.0] - 2019-05-13

### Changed

-   Flusso di accreditamento: Specifiche dal file [Flusso di accreditamento](https://teams.microsoft.com/l/entity/com.microsoft.teamspace.tab.wiki/tab::a1732314-685d-439b-9f19-5e5d366a1bac?context=%7B%22subEntityId%22%3A%22%7B%5C%22pageId%5C%22%3A44%2C%5C%22origin%5C%22%3A2%7D%22%2C%22channelId%22%3A%2219%3Aaee7d621598f422f9c7b9daa0423f1ff%40thread.skype%22%7D&tenantId=f2d7d6c5-1bee-41ff-9e79-b372a5cce71d)

## [1.15.0] - 2019-05-13

## [1.14.0] - 2019-05-13

### Added

-   Gestione delle condizioni legate alla tipologia di conferenza dal BE
    -   Tab Pratica:
        -   campo attività
        -   campo tipologia
    -   Tab Definizione
        -   campi delle date
        -   campo orario
    -   Tab Partecipanti
        -   campo comptetenze

### Changed

-   Menù responsive
-   Footer responsive

## [1.13.0] - 2019-05-09

### Added

-   Console amministrativa: Aggiunta funzionalità per inserimento e modifica Ente

## [1.12.0] - 2019-05-08

### Added

-   Console amministrativa
    -   Gestione amministrazioni procedenti
        -   Modifica
        -   Gestione Responsabili
        -   Gestione conferenza
    -   Gestione utenti
    -   Gestione rubriche per tipo conferenza
        -   Gestione richiedenti (per ogni tipologia di conferenza)
        -   Gestione partecipanti (per ogni tipologia di conferenza)
        -   Gestione imprese (per ogni tipologia di conferenza)
-   Aggiunta funzionalità di invio mail di indizione per specifica conferenza

## [1.11.0-5] - 2019-03-27

### Added

-   Paleo: Aggiustato il valore passato per il campo conferenceType

## [1.11.0-4] - 2019-03-26

### Added

-   Richieste mail di Ivana di Lun 25/03/2019 13:11
    -   Creata la nuova voce di menu 'Informazioni utili' visibile solo agli utenti loggati
    -   Creata la nuova pagina con del testo e un immagine sulla sinistra e un menù sulla destra con dei link utili
-   Aggiunto il nuovo metodo \_hideAll sul servizio LoaderService per gestire la chiusura di tutti/qualsiasi loading
-   Gestito il caso generico di errore sul download di file con messaggio di errore generico e gestione loading
-   Gestito il caso in cui esistano nuovi eventi di sistema con un dettaglio non implementato
-   Gestiti i permessi sugli eventi lanciando una chiamata in background per recuperare la lista completa degli eventi (da rivedere)
-   Riviste le regole CSS per impostare il colore delle ancore sulla header dei pannelli con accordion
-   Implementazione di nuovi componenti per gestire dinamicamente i contenuti delle pagine statiche (home, informazioni utili)
-   Aggiunta label mancante per la lista dei documenti in creazione paleo

### Fixed

-   Fix di tutti i punti mailmail di Ivana ven 22/03/2019 17:27
-   Fix del download dei file sul tab accreditamenti e eventi

### Removed

-   Rimossi i warning del framework Angular riguardanti l'utilizzo dell'attributo disabled sugli input

## [1.11.0-3] - 2019-03-22

### Fixed

-   Fix caricamento file

## [1.11.0-2] - 2019-03-22

### Added

-   Integrazione Only Office: Condivisione permessa solamente per i documenti degli eventi COMUNICAZIONE_GENERICA

### Changed

-   Restyling grafico: Mail di gio 21/03/2019 12:47

### Fixed

-   Fix punto 8 mail di Ivana mer 20/03/2019 17:41

## [1.11.0-1] - 2019-03-21

### Changed

-   Mail di gio 21/03/2019 12:47
    -   Restyling pagina home
    -   Rivisitazione logo
    -   Spostato logo sulla header prima del nome

### Fixed

-   Mail di Ivana mer 20/03/2019 17:41
    -   Fix dei punti: 1,3, 4, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 18, 19, 20

## [1.11.0-0] - 2019-03-20

### Added

-   Abilitazione modifiche per il richiedente
-   Associazione tipologia conferenza <-> categoria dei documenti
-   Campo Comune di riferimento
-   Integrazione Paleo
-   Standardizzare le tipologie di autorizzazione (competenze) che rilasciano i vari Enti
-   Pagina contatti
-   Lista partecipanti: Aggiunto flag trasmissione pareri
-   Lista partecipanti: Aggiunto flag richiesta integrazioni
-   Inserita la ricerca sullo step degli eventi con i seguenti campi di ricerca
    -   Tipo evento
    -   Tipologia amministrazione
    -   Ente
-   Aggiunto logo meetpad sul menu e sulla destra della topbar
-   Aggiunta immagine nella home
-   Aggiunti loghi regione marche e UE sul footer
-   Aggiunte le seguenti info sul popup di conferma indizione
    -   data Termine interazioni
    -   data Termine espressione pareri
    -   data Termine prima sessione simultanea
    -   orario conferenza
    -   data Termine ultimo
    -   messaggio di warning per verificare la lista dei partecipanti
-   Form restyling
    -   Trasformate le varie sezioni in accordion
    -   Aggiunto un menu di navigazione tra gli accordion sulla sinistra del form (input ristretti sulla sinistra)
-   Espressione parere multipla
-   Mostrato evento verbale conferenza anche per BUL e preistruttoria
-   Gestione caso di conferenza scaduta (410 Gone)
-   Gestione caso di codice fiscale inserito diverso dal cf dell'utente loggato (422 - 422.1)

### Changed

-   Refactor, pulizzia e ottimizzazioni

### Fixed

-   Fix loop in fase di autoaccreditamento su nuovo utente

### Removed

-   Rimossi loghi dalla header guadagnado spazio inutilizzato
-   Rimosse le condizioni dal FE per centralizzarle sul BE

## [1.10.0] - 2019-03-08

### Chanced

-   Standardizzare le tipologie di autorizzazione (competenze) che rilasciano i vari Enti
    -   Aggiunto stato aggiunto un campo auto completante di scelta multipla per le competenze che gli enti possono avere a partire da una lista predefinita

## [1.9.1] - 2019-03-07

### Changed

-   Gestione campi non presenti per le conferenze paleo

## [1.9.0] - 2019-03-06

### Added

-   Aggiunta la condizione per mostrare il campo solo per i casi in cui la categoria della documentazione è
    -   Documentazione per comune
    -   Documentazione per provincia

## [1.8.0] - 2019-03-06

### Added

-   Abilitazione modifiche per il richiedente
-   Associazione tipologia conferenza <-> categoria dei documenti
    -   La categoria dei documenti è mostrata in base alla tipologia della conferenza
-   Aggiunto il campo “comune di riferimento”, sulla base della tipologia documento, quando si caricano i documenti
-   Possibilità di creare una conferenza a partire da un fascicolo con dei documenti Paleo
-   Gestione della documentazione con fascicolo

## [1.6.0] - 2019-02-04

### Added

-   Campo "Orario delle conferenza" nel tab definizione (in tutte le tipologie tranne che nella semplificata)
-   Aggiunte le seguenti voci: "riunione fisica" e "riunione on line" nel tab definizione
-   Aggiunto per l'Indirizzo una convention di inserimento: nel caso di scelta di "riunione fisica" presentare i campi: indirizzo, cap, comune, provincia
-   Aggiunta la sezione nel tab definizione chiamata "contatti per assistenza" con dei campi : nome , cognome, numero di telefono e mail
-   Aggiunto switch sulla documentazione per mostrare tutti i documenti
-   Aggiunta una nuova voce di menù
-   Aggiunta una nuova pagina per il calendario
-   La pagina di calendario contiene una visualizzazione mensile delle scadenze di tutte le conferenze presenti in Scrivania
-   Inserimento legenda
-   Aggiunto evento "Espressioni parere"
-   Aggiunto evento "Determinazione finale"
-   Aggiunta possibilità di effettuare il download di un modello di verbale di riunione da rendere disponibile (da verificare il punto in cui renderlo disponibile e le informazioni da inserire)
-   Aggiunto evento "Comunicazione generica"
-   Aggiunto evento "Trasmissione integrazioni protocollate"
-   Aggiornate le condizioni di attivazione/disattivazione
-   Aggiunto download del template per il "Verbale conferenza"
-   Aggiunto campo "tipo parere" sull'evento "Espressione parere"
-   Aggiunto campo "visibilità" sull'evento "Invio integrazioni"
-   Aggiunta ricerca conferenza unificata, stile google

### Changed

-   Modificato il campo "tipo di indirizzo" in "modalità dell'incontro" nel tab definizione
-   Determina finale: Modificate le etichette in: numero della determinazione e data della determinazione
-   Modificatp il campo “corpo” in “testo del messaggio”

## [1.5.0] - 2019-01-03

### Added

-   Dati impresa: Agganciato alla rubrica in cui inserire almeno i dati di Open Fiber. Al momento prevediamo il caso semplice in cui per la tipologia di conferenza BUL viene caricato un solo richiedente

### Changed

-   Modificato il comportamento del menù rendendolo a comparsa/responsive

## [1.4.0] - 2018-12-19

### Added

-   Aggiunto evento caricamento verbale riunione
-   Aggiunto evento chiusura Conferenza
-   Aggiunto drag n drop sulla documentazione
    -   Caricamento di un un insieme di file con il drag and drop. Soluzione riportata sulla tab “Documentazione” per le sezioni; “Documentazione aggiuntiva”, “Documentazione interazioni” e “Documentazione pre-istruttoria”

### Changed

-   Gestione delle combo con molti elementi con Autocomplete: Combo di localizzazione
-   PREISTRUTTORIA: date non obbligatorie
-   Abbiamo reso le tabelle leggibili togliendo la regola che troncava i testi lunghi di default e li mostrava per intero passandoci sopra con il mouse. Questo comporta che alcune righe hanno un’altezza maggiore rispetto alle altre. L’integrazione di altre librerie per la gestione delle tabelle potrebbe essere impattante e soprattutto non risolverebbe il problema. Un’altra alternativa è quella di troncare i testi lunghi (mantenendo un’altezza fissa delle righe della tabella) ed aggiungere un title ben visibile al passaggio del mouse (vediamo se la soluzione attuale soddisfa il cliente).
-   Adeguate le dimensioni dei campi in base ai lunghi testi, migliorando leggibilità ed usabilità
-   Messaggio nessuna conferenza: modificato "Nessuna conferenza trovata" in "Nella propria scrivania non è ancora presente nessuna conferenza"
-   Impostati gli stessi permessi del Responsabile al Creatore

### Removed

-   Tolte dalla tabella le due colonne relative a "PEC" e "codice fiscale";

## [1.3.5] - 2018-12-07

### Added

-   Aggiunto il link sul menù per scaricare le istruzioni di autenticazione

## [1.3.4] - 2018-12-07

### Changed

-   Aumentato numero di caratteri a 1000 per text area del campo oggetto del pannello definizione

### Fixed

-   Fix internet explorer

## [1.3.3] - 2018-12-05

### Added

-   Aggiunto autoaccreditamento senza back-office per conferenza preistruttoria

### Changed

-   Modificati i valori di default delle select tipologia, (pannello pratica) nel caso di creazione di conferenza preistruttoria o banda larga
-   Modificato testo toastr passaggio in bozza

### Fixed

-   Fix sulle date del tab definizione (non venivano salvate sulla maschera ma solo sul db)

## [1.3.2] - 2018-12-03

### Added

-   Aggiunto polyfills.ts per Internet Explorer

### Changed

-   Reso sempre visibile il pulsante clona se la conferenza è di tipo preistruttoria

## [1.3.1] - 2018-11-29

### Fixed

-   Fix sull'autenticazione in fase di accreditamento
-   Fix sulla gestione degli eventi

## [1.3.0] - 2018-11-28

### Changed

-   Ottimizzazioni

## [1.2.1] - 2018-11-21

### Added

-   Aggiunto pulsante per clonare una conferenza

## [1.2.0] - 2018-11-19

### Added

-   Aggiunto evento di richiesta integrazioni

## [1.0.2] - 2018-11-06

### Added

-   Refactor della logica di download dei file per supportare l'autenticazione
-   Creazione di uno store condiviso per la conferenza
-   Gestione del preloading dei moduli

### Changed

-   Popup di conferma eliminazione utente
-   Salvataggio indirizzo

## [1.0.0] - 2018-10-26

### Added

-   Allineato con le funzionalità della demo del 25/10/18
-   Delega per creatore di conferenze
    -   alcuni utenti creatori cst potranno ricevere la delega per creare conferenze per altre amm. procedenti (per adesso l'abilitazione sarà totale, in futuro sarà possibile restringerla a determinate amm.procedenti)
    -   l'asseganzione della delega verrà effettuata da back-office (attualmente verrà fatta manualmente dagli sviluppatori su DB) quando un creatore cst con tale delega, vuole creare una nuova conferenza, prima di tutto, dovrà scegliere l'amm. procedente e il relativo responsabile di conferenza
-   Accreditamenti
    -   le persone non censite che riceverranno la mail di invito alla conferenza, accederanno al link (che punta al portale meet-pad) contenuto nella mail
    -   all'atterraggio sul portale, successivamente al login su Cohesion, verranno eseguiti i controlli sull'utente:
        -   se già censito e preaccreditato: entra direttamente sulla conferenza
        -   se già censito ma non preacreadditato: verrà mostrata una maschera con gli input (nome, cognome, codice fiscale, email, ruolo/profilo e documento di accreditamento) prepopolati che l'utente potrà modificare
        -   se non ancora censito: verrà mostrata una maschera con gli input (nome, cognome, codice fiscale, email, ruolo/profilo e documento di accreditamento) vuoti che l'utente dovrà inserire
        -   al submit della maschera dei punti 2.2 e 2.3, l'utente sarà posto in attesa (pagina di cortesia) per attendere l'approvazione da parte del responsabile di conferenza (back-office)
    -   BACK_OFFICE: il responsabile della conferenza, vedrà la lista degli accreditamenti, su un tab apposito, con la possibilità di vedere i dettagli e approvare ogni accreditamento (in linea con quanto specificato sul file di specifiche Analisi_rev1-4)- NON RILASCIATA
-   Creazione di uno store condiviso per la conferenza
-   Gestione del preloading dei moduli
-   Testi dei tooltip sulle date nel wizard "definizioni"
-   Aggiunti campi in fase di indizione:
    -   Numero di protocollo
    -   Data di protocollo
    -   Numero di repertorio
-   Aggiunto refresh automatico della sessione
-   Aggiunto logout

### Changed

-   Modifica del corpo della mail di accreditamento
-   Popup di conferma eliminazione utente
-   Salvataggio indirizzo
-   Refactor della logica di download dei file per supportare l'autenticazione
-   Campo indirizzo della conferenza suddiviso in tipologia e riferimento
-   Campo partecipante -> competenza testuale

### Removed

-   Eliminazione del parametro App-Id-Token sulla header di tutte le chiamate REST
