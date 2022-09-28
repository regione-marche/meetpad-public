export const IT = {
    APP_NAME: 'MeetPAd',
    HOME: 'Home',
    LOGOUT: 'ESCI',
    LOGIN: {
        TITLE: 'ACCEDI',
        COHESION: 'Cohesion',
        SPID: 'Spid'
    },
    DOCUMENT: "Istruzioni per l'autenticazione",
    PORTAL_LINK: 'Ritorna al portale',
    ADMIN_LINK: 'Pannello di amministrazione',
    UPLOAD_LINK: 'Stato caricamenti',

    PRIVATE: {
        MODAL: {
            CONFIRM_LOGIN_REDIRECT: {
                TITLE: 'Sessione scaduta',
                MESSAGE: `Per poter continuare ad utilizzare il sistema è necessario eseguire nuovamente la login.
                Vuoi eseguire la login?`,
                OK_BUTTON: 'Si',
                CANCEL_BUTTON: 'No'
            }
        }
    },

    HEADER: {
        AMMINISTRAZIONE_TRASPARENTE: 'Amministrazione Trasparente',
        PROFILO_DEL_COMMITTENTE: 'Profilo del committente',
        SEGNALAZIONE_DI_SEMPLIFICAZIONE: 'Segnalazione di semplificazione',
        COME_FARE_PER: 'Come far per',
        RUBRICA: 'Rubrica',
        URP: 'URP',
        MAIN_MENU_NAVIGATION: 'Menu di navigazione principale',
        HOME: 'Home',
        IN_THE_FOCUS: 'In Primo Piano',
        USEFUL_REGION: 'Regione utile',
        JOIN_REGION: 'Entra in Regione',
        REGION: 'Regione Marche',
        CONTACT: 'Contatti',
        REPORT: 'Open Data',
        SELECTED: 'selected'
    },

    MENU: {
        OPEN_SITE: 'Apri sito regione',
        TITLE: 'MENU',
        LINKS: {
            DESKTOP: 'Scrivania',
            SEARCH: 'Ricerca',
            CREATE_CONFERENCE: {
                PARENT: 'Crea conferenza',
                MEET_PAD: 'MeetPAd',
                PALEO: 'Paleo',
                DOMUS: 'Domus'
            },
            MEDIA_LIBRARY: 'Mediateca',
            CALENDAR: 'Calendario',
            INFORMATION: 'Informazioni utili',
            JITSI: 'Accedi a Jitsi',
            SIGN: 'Alla Firma'
        }
    },

    FOOTER: {
        ALTERNATIVE_NAVIGATION: 'Navigazione alternativa',
        BACK_TO_INERNAL_NAVIGATION: 'Torna alla navigazione interna',
        SERVICE_MENU: 'Menù di servizio',
        PRIVACY: 'Privacy',
        TERM_OF_USE: 'Termini di utilizzo',
        COOKIE_REPORT: 'Informativa sui cookie',
        SITEMAP: 'Sitemap',
        LOGIN: 'Login'
    },

    DESKTOP: {
        BREADCRUMB: 'Scrivania',
        TITLE: 'Scrivania',
        PANEL_TITLE: 'Elenco Conferenze'
    },

    SEARCH: {
        BREADCRUMB: 'Ricerca',
        TITLE: 'Cerca una conferenza',
        SEARCH: {
            TITLE: 'Ricerca',
            INPUT_LABEL: `Ricerca una conferenza utilizzando
                l'id oppure lo stato, la tipologia, il riferimento istanza,
                il richiedente, la provincia o il comune`,
            STATE: 'Stato',
            OPTION_STATE: {
                ALL: 'Tutte',
                CLOSED: 'Chiuse',
                FILED: 'Archiviate',
                OPEN: 'Aperte'
            }
        },
        BASIC_RESEARCH: {
            TITLE: 'Ricerca base',
            INPUT_LABEL:
                "Ricerca una conferenza utilizzando l'ID della conferenza oppure utilizza la ricerca avanzata."
        },
        ADVANCED_RESEARCH: {
            TITLE: 'Ricerca avanzata',
            RADIO_LABELS: {
                STATE: 'Stato',
                TYPE: 'Tipologia'
            },
            RADIO_STATE: {
                ALL: 'Tutti',
                EVALUATION: 'In valutazione',
                CLOSED: 'Chiusa',
                FILED: 'Archiviata',
                DRAFT: 'In bozza',
                OPEN: 'Aperti'
            },
            RADIO_TYPE: {
                ALL: 'Tutti',
                SEMPLIFIED: 'Semplificata',
                SIMULTANEOUS: 'Simultanea',
                PERMANENT: 'Permanente'
            },
            INPUT_LABELS: {
                REQUEST_REFERENCE: 'Riferimento istanza',
                APPLICANT: 'Richiedente',
                PROVINCE: 'Provincia',
                CITY: 'Comune'
            }
        },
        RESULT_TABLE: {
            TITLE: 'Risultati ricerca',
            ID: 'ID',
            IDCLONE: 'Clonata da',
            REQUEST_REFERENCE: 'Riferimento istanza',
            TYPE: 'Tipologia',
            END_PROCEDURE: 'Termine procedimento',
            STATE: 'Stato',
            PROCEEDING_ADMINISTRATION: 'Amministrazione procedente',
            APPLICANT: 'Richiedente',
            OWNER: 'Intestatario',
            END_INTEGRATION: 'Termine integrazione',
            END_DETERMINE: 'Termine per la determina',
            END_NEXT_SESSION: 'Termine prossima sessione',
            ACTIONS: {
                TITLE: 'Espandi',
                BUTTONS: {
                    VIEW: 'Visualizza',
                    EDIT: 'Modifica',
                    ENTER: 'Entra',
                    DELETE: 'Elimina',
                    SKYPE: 'Conferenza online',
                    CLONE: 'Clona'
                }
            }
        },
        SEARCH_BUTTON: 'Cerca',
        RESET_BUTTON: 'Reset',
        TOASTR: {
            EMPTY_SEARCH: {
                TITLE: 'Nessuna conferenza trovata',
                TEXT: 'I criteri specificati non hanno prodotto risultati'
            },
            EMPTY_DESKTOP: {
                TITLE: 'Nessuna conferenza disponibile',
                TEXT:
                    'Nella propria scrivania non è ancora presente nessuna conferenza'
            },
            SUCCESS_DELETE: {
                TITLE: 'Conferenza eliminata',
                TEXT:
                    'La conferenza in compilazione è stata eliminata correttamente'
            }
        },
        CONFIRM_DELETE_MODAL: {
            TITLE: 'Conferma cancellazione conferenza',
            MESSAGE: 'Sicuro di volere cancellare la conferenza?',
            OK_BUTTON: 'Ok',
            CANCEL_BUTTON: 'Annulla'
        },        
        CONFIRM_CLONE_MODAL: {
            TITLE: 'Conferma clone conferenza',
            MESSAGE: 'Sicuro di volere clonare la conferenza?',
            OK_BUTTON: 'Ok',
            CANCEL_BUTTON: 'Annulla'
        }
    },

    CALENDAR: {
        TITLE: 'Calendario',
        PREVIOUS: 'Precedente',
        TODAY: 'Corrente',
        NEXT: 'Successivo'
    },

    MEDIA_LIBRARY: {
        TITLE: 'Mediateca',
        SEARCH: {
            TITLE: 'Cerca un documento di tipo video',
            INPUT_LABEL: `Ricerca una documento video utilizzando l'oggetto della conferenza oppure il nome del documento`,
            BUTTON: 'Cerca'
        },
        TABLE: {
            TITLE: 'Lista dei documenti video',
            ID: 'Id',
            DOCUMENT_NAME: 'Nome file',
            CONFERENCE_SUBJECT: 'Oggetto della conferenza',
            DOCUMENT_MD5: 'MD5 Hash File Checksum',
            ACTIONS: 'Azioni'
        }
    },

    CONTACT: {
        TITLE: 'Contatti'
    },
    OPENDATA: {
        TITLE: 'Open Data'
    },

    INFORMATION: {
        TITLE: 'Informazioni utili',
        USEFUL_DOC: 'Documenti Utili',
        REGULATION: 'Normativa',
        FORMS: 'Modulistica',
        CONFERENCE: {
            TITLE: 'Conferenza dei servizi'
        }
    },
    SIGN: {
        TITLE: 'Alla Firma',
        TABLE: {    
            DOCUMENT:
            'Elenco Documenti',
            USER: 'Utente',
            DATE: 'Data Protocollazione',
            PROTOCOL_NUMBER: 'Numero Protocollo',
            ACTIVITY: 'Attività',
            TYPE: 'Tipo Documento',
            FILE: 'Documento principale',
            RECIPIENT: 'Destinatari',
            RECIPIENT_ALL: 'Tutti',
            STATUS: 'Stato',
            EMPTY_TABLE: 'Non ci sono documenti alla firma',
            DETAIL: 'Dettagli Documento',
            ATTACHED_FILE: 'Documento allegato',
            ACTIONS: {
                TITLE: 'Azioni',
                BUTTONS: {
                    DETAIL: 'Dettagli Documento',
                    EDIT: 'Modifica',
                    ENTER: 'Dettagli Richiesta',
                    DELETE: 'Elimina',
                    SKYPE: 'Conferenza online',
                    CLONE: 'Clona',
                    SELECT: 'Seleziona'
                }
            },
            ACTIONS_BUTTONS: {
                SEND: 'Da completare',
                VIEW: 'Accettato',
                REJECTED: 'Rifiutato',
                SIGN: 'Firma Selezionati',
                REJECT: 'Rifiuta Selezionati',
                SELECT_ALL: 'Seleziona Tutti'
            }
        },
        CONFIRM_REJECT_LIST_MODAL: {
            TITLE: 'Conferma documenti rifiutati',
            MESSAGE: 'Sicuro di volere rifiutare la lista dei documenti selezionati?',
            OK_BUTTON: 'Ok',
            CANCEL_BUTTON: 'Annulla',
            SUCCESS_REJECT_LIST_FILE: {
                TITLE: 'Documenti rifiutati',
                TEXT: 'I documenti selezionati stato stati rifiutati correttamente'
            },
            WARNING_REJECT_LIST_FILE: {
                TITLE: 'Nessun documento selezionato',
                TEXT: 'Non è stato selezionato nessun documento da rifiutare'
            },
            ERROR_REJECT_LIST_FILE: {
                TITLE: 'Documenti non rifiutati',
                TEXT: 'Non è stato possibile rifiutare i documenti'
            },
        },
        CONFIRM_SIGN_LIST_MODAL: {
            TITLE: 'Caricamento documento',
            TITLE_REMOTE: 'Firma dei documenti in remoto',
            CANCEL_BUTTON: 'Annulla',
            UPLOAD: 'Carica documento',
            SIGN_REMOTE: 'Firma selezionati',
            SUCCESS_SIGN_LIST_FILE: {
                TITLE: 'Documenti firmati',
                TEXT: 'I documenti selezionati stato stati firmati correttamente'
            },
            WARNING_SIGN_LIST_FILE: {
                TITLE: 'Nessun documento selezionato',
                TEXT: 'Non è stato selezionato nessun documento da firmare'
            },
            ERROR_SIGN_LIST_FILE: {
                TITLE: 'Documenti non firmati',
                TEXT: 'Non è stato possibile firmare i documenti'
            },
        },
        ACTIONS_BUTTONS: {
            SEND: 'Documenti da Completare',
            REJECTED: 'Documenti Rifiutati',
            SIGNED: 'Documenti Accettati',
            REJECT: 'Rifiuta Selezionati',
            SELECT_ALL: 'Seleziona Tutti'
        },
        MODAL_SIGN: {
            TITLE: 'Invia documento alla firma multipla',
            TEXT: 'Invia il documento caricato alla firma multipla',
            OK_BUTTON: 'Ok',
            CANCEL_BUTTON: 'Annulla',
            MANAGER: 'Seleziona responsabile CST della firma',
            WARNING_SEND_TO_SIGN: {
                TITLE: 'Attenzione',
                TEXT: `E' necessario selezionare il responsabile CST`
            }
        },
    },
    CONFERENCE: {
        TITLE: 'Conferenza',
        STATES: {
            DRAFT: 'Bozza',
            JUDGMENT: 'In valutazione'
        },
        HEAD: {
            ID: 'Id',
            CLONEID: 'Clonata dalla conferenza',
            INSTANCE_REFERENCE: 'Riferimento istanza',
            STATE: 'Stato',
            SKYPE_CONFERENCE: 'Conferenza online'
        },
        TOOLTIP_STATES: {
            COMPILING:
                'Nello stato "Compilazione", la conferenza può essere modificata ed eliminata.',
            DRAFT: `Nello stato "Bozza", la conferenza può essere modificata,
                non può essere eliminata e può essere caricato il file d\'indizione.`,
            JUDGMENT:
                'Nello stato "Valutazione", la conferenza non è modificabile, non è cancellabile.',
            CLOSED:
                'Nello stato "Chiusa", la conferenza non è modificabile, non è cancellabile.',
            ARCHIVIED:
                'Nello stato "Archiviata", la conferenza non è modificabile, non è cancellabile.'
        },
        ADD: {
            TITLE: 'Crea conferenza'
        },
        PALEO: {
            TITLE: 'Crea conferenza paleo',
            INPUTS_LABEL: {
                MANAGER: 'Selezionare il responsabile della conferenza',
                ADMINISTRATION: "Selezionare l'amministrazione procedente",
                FOLDER_CODE: 'Codice fascicolo',
                FOLDER_DESCRIPTION: 'Descrizione fascicolo',
                CONFERENCE_TYPE: 'Tipologia conferenza',
                FILES: 'Documenti',
                FILES_LIST: 'Lista',
                FILE_CODE: 'Codice file',
                FILE_DESCRIPTION: 'Descrizione file'
            },
            TABLE: {
                EMPTY_TEXT_LIST: 'Non ci sono documenti censiti'
            },
            TOASTR: {
                SAVE_COMPLETE: {
                    TITLE: 'Conferenza creata',
                    TEXT:
                        'I documenti sono stati creati su paleo ed associati alla nuova conferenza'
                },
                SAVE_ERROR: {
                    TITLE: 'Conferenza non creata',
                    TEXT: `Non è stato possibile creare la conferenza con i documenti paleo associati.
                        Contattare l'amministratore di sistema`
                },
                SAVE_ERROR_404: {
                    TITLE: 'Conferenza non creata',
                    TEXT: `Il fascicolo o uno dei documenti specificati, non esiste`
                }
            }
        },
        DOMUS: {
            TITLE: 'Crea conferenza domus',
            INPUTS_LABEL: {
                FOLDER_CODE: 'ISTAT comune oppure #ID-pratica oppure +CodiceFascicolo',
                FOLDER_DESCRIPTION: 'Descrizione fascicolo',
                CONFERENCE_TYPE: 'Tipologia conferenza',
                //DOSSIER_CODE: 'Codice Fascicolo',
                ACCOUNTABLE: 'Responsabile'
            },
            TOASTR: {
                SAVE_COMPLETE: {
                    TITLE: 'Conferenza creata',
                    TEXT:
                        'I documenti sono stati creati su paleo ed associati alla nuova conferenza'
                },
                SAVE_ERROR: {
                    TITLE: 'Conferenza non creata',
                    TEXT: `Non è stato possibile creare la conferenza con i documenti paleo associati.
                        Contattare l'amministratore di sistema`
                },
                SAVE_ERROR_404: {
                    TITLE: 'Conferenza non creata',
                    TEXT: `Il fascicolo o uno dei documenti specificati, non esiste`
                }
            }
        },
        VIEW: {
            TITLE: 'Visualizza conferenza'
        },
        EDIT: {
            TITLE: 'Modifica conferenza'
        },
        AUTHORIZATION_MODAL: {
            TITLE: 'Autorizza alla modifica',
            MESSAGE:
                'Sei sicuro di voler abilitare il richiedente alla modifica? In caso positivo il richiedente potrà intervenire solo nel TAB Partecipanti, per la definizione degli enti coinvolti, e nel TAB Documentazione per il caricamento della documentazione di interesse.',
            CANCEL_BUTTON: 'Annulla',
            OK_BUTTON: 'Ok'
        },
        DENY_MODAL: {
            TITLE: 'Revoca permessi di modifica',
            MESSAGE: 'Sicuro di volere revocare i permessi di modifica?',
            CANCEL_BUTTON: 'Annulla',
            OK_BUTTON: 'Ok'
        },
        CONFIRM_MODAL: {
            TITLE: 'Termina modifiche',
            MESSAGE:
                'Sicuro di volere terminare le modifiche? Una volta confermato non potrai più apportare modifiche alla conferenza.',
            CANCEL_BUTTON: 'Annulla',
            OK_BUTTON: 'Ok'
        },
        JOIN: {
            TITLE: 'Accreditamento',
            PENDING_PAGE: {
                TITLE: 'In attesa di accreditamento',
                TEXT:
                    'La sua richiesta è stata presa in carico, è in attesa di approvazione da parte del responsabile della conferenza.',
                TEXT_GO_TO: 'Vai alla',
                HOME_PAGE: 'Home page'
            },
            PENDING_LIST_PAGE: {
                TITLE: 'In attesa di accreditamento',
                TEXT: `Non sono presenti Conferenze dei Servizi di interesse per questa utenza
                    perchè esistono delle richieste di accreditamento in attesa di approvazione
                    da parte del responsabile della conferenza.`,
                TABLE: {
                    TITLE: 'Lista delle richieste di accreditamento',
                    ID: 'Id',
                    CONFERENCE_ID: 'ID Conferenza',
                    CREATION_DATE: 'Data inserimento'
                }
            },
            MODAL: {
                FORM: {
                    NAME: 'Nome',
                    SURNAME: 'Cognome',
                    FISCAL_CODE: 'Codice fiscale',
                    PROFILE: 'Profilo',
                    EMAIL: 'Email',
                    FILE: 'Documento di accreditamento ',
                    NOTE: 'Motivazioni',
                    FILE_TOOLTIP_TITLE: '',
                    FILE_TOOLTIP_TEXT: `Caricare l'eventuale documento che attesta
                        la nomina per la partecipazione alla conferenza dei servizi in oggetto`
                }
            },
            TOASTR: {
                SUCCESS_SAVE: {
                    TITLE: 'Maschera accreditamento salvata',
                    TEXT: 'I dati sono stati salvati correttamente'
                },
                ERROR_SAVE: {
                    TITLE: 'Maschera accreditamento non salvata',
                    TEXT: 'Non è stato possibile salvare i dati'
                },
                POST_PUT_ERROR: {
                    TITLE: 'Accreditamento non salvato',
                    TEXT: `Non è stato possibile salvare i dati dell'accreditamento. Contattare l'amministratore di sistema.`
                },
                ACCESS_DENIED: {
                    TITLE: 'Accreditamento negato',
                    TEXT: 'Non hai i permessi per accreditarti alla conferenza'
                },
                EXPIRED: {
                    TITLE: 'Accreditamento negato',
                    TEXT:
                        'Il termine per la prima sessione simultanea della conferenza in oggetto è scaduto'
                },
                POST_PUT_ERROR_422_1: {
                    TITLE: 'Accreditamento negato',
                    TEXT: `Il codice fiscale inserito non corrisponde a quello dell'utente connesso`
                },
                POST_PUT_ERROR_404_001: {
                    TITLE: 'Accreditamento negato',
                    TEXT: `I dati inseriti non corrispondono ad un accreditamento esistente`
                }
            },
            MASKS: {
                ACCREDITMENT: {
                    TITLE:
                        'Maschera di accreditamento alla conferenza n. {{conferenceId}}',
                    TITLE_SUAP:
                        'Richiedi accesso alla conferenza n. {{conferenceId}}'
                },
                EDIT_QUESTION: {
                    TEXT: `Hai una richiesta di accreditamento in attesa di conferma per questa conferenza. Vuoi modificarla?`,
                    OK_BTN: 'Si',
                    NOK_BTN: 'No'
                }
            }
        },
        WIZARD: {
            MODAL_BEFORE_CREATE: {
                TITLE: 'Inserimento dati preliminari',
                MANAGER_LABEL: 'Selezionare il responsabile della conferenza',
                ADMINISTRATION_LABEL:
                    "Selezionare l'amministrazione procedente",
                TYPE_LABEL: 'Selezionare la tipologia della conferenza',
                COMPANY: "Selezionare l'impresa"
            },
            PROCEDURE: {
                TITLE: 'Pratica',
                APPLICANT: {
                    TITLE: 'Dati richiedente',
                    INPUTS_LABEL: {
                        REQUEST_REFERENCE: 'Riferimento istanza',
                        NAME: 'Nome richiedente',
                        SURNAME: 'Cognome richiedente',
                        PUBIC_NAME: 'Denominazione',
                        FISCAL_CODE: 'Codice fiscale richiedente',
                        PEC: 'PEC/Mail',
                        START_DATE: 'Data avvio',
                        TYPE: 'Tipologia',
                        ACTIVITY: 'Attività',
                        ACTION: 'Azione'
                    }
                },
                LOCALIZATION: {
                    TITLE: "Localizzazione (comune di preminenza dell'istanza)",
                    INPUTS_LABEL: {
                        PROVINCE: 'Provincia',
                        CITY: 'Comune',
                        ADDRESS: 'Indirizzo'
                    }
                },
                COMPANY: {
                    TITLE: 'Dati Installazione',
                    TITLE_BUL: 'Dati Fornitore',
                    TITLE_VIA_AIA: 'Dati Installazione',
                    TITLE_USR: 'Dati Intestatario',
                    INPUTS_LABEL: {
                        DENOMINATION: 'Denominazione',
                        FISCAL_CODE: 'Codice fiscale intestatario',
                        VAT_NUMBER: 'Partita IVA',
                        LEGAL_FORM: 'Forma giuridica',
                        AREA: 'Regione',
                        PROVINCE: 'Provincia',
                        CITY: 'Comune',
                        ADDRESS: 'Indirizzo'
                    }
                },
                TOASTR: {
                    SUCCESS_SAVE: {
                        TITLE: 'Step 1 salvato',
                        TEXT:
                            'I dati del primo step sono stati salvati correttamente'
                    },
                    ERROR_SAVE: {
                        TITLE: 'Step 1 non salvato',
                        TEXT: 'I dati del primo step non sono stati salvati'
                    },
                    SUCCESS_SAVE_NO_APPLICANT: {
                        TITLE: 'Richiedente non salvato',
                        TEXT: `I dati del richiedente non sono stati salvati a casusa
                            di un malfunzionamento. Contattare l'amministratore di sistema.`
                    }
                }
            },
            DEFINITION: {
                TITLE: 'Definizione',
                DETERMINATION: {
                    TITLE: 'Oggetto',
                    DETERMINATION_OBJECT_INPUT:
                        'Oggetto della determinazione da assumere'
                },
                INSTANCE: {
                    TITLE: 'Dati generali',
                    INSTANCE_REFERENCE_LABEL: 'Riferimento istanza',
                    CREATION_DATE: 'Data creazione Pratica',
                    CONFERENCE_TYPE: 'Tipologia di conferenza',
                    END_INTEGRATION_DATE: 'Termine per la richiesta di integrazioni',
                    END_OPINION_DATE: 'Termine per l’espressione dei pareri',
                    FIRST_SESSION_DATE: 'Prima sessione simultanea',
                    EXPIRATION_DATE: 'Data termine',
                    
                    DATA_TERMINE_RICHIESTA_INTEGRAZIONI_CONFERENZA: 'Termine per la richiesta di integrazioni',
                    DATA_TERMINE_ESPRESSIONE_PARERI: 'Termine per l’espressione dei pareri',
                    DATA_PRIMA_SESSIONE_SIMULATA: 'Prima sessione simultanea',
                    DATA_PRIMA_SESSIONE_SIMULTANEA: 'Prima sessione simultanea',
                    DATA_TERMINE: 'Data termine',

                    CONFERENCE_TIME: 'Orario della conferenza',
                    ADDRESS: 'Indirizzo dove si svolgerà la simultanea',
                    ADDRESS_TYPE: "Modalità dell'incontro",
                    ADDRESS_TYPE_TITLE: 'Incontro',
                    PHYSICAL_ADDRESS: 'Indirizzo',
                    CAP: 'CAP',
                    CITY: 'Comune',
                    PROVINCE: 'Provincia',
                    RADIO_OPTIONS: {
                        SIMPLIFY: 'Semplificata',
                        SIMULTANEOUS: 'Simultanea',
                        PERMANENT: 'Permanente'
                    },
                    TOOLTIP: {
                        TITLE_END_INTEGRATION_DATE: '',
                        TEXT_END_INTEGRATION_DATE: `Le scadenze proposte si riferiscono
                            alle tempistiche indicate dal D.Lgs 241/90. Nel caso di normativa
                            specifica è possibile modificare le date proposte.`,
                        TITLE_END_OPINION_DATE: '',
                        TEXT_END_OPINION_DATE: `Le scadenze proposte si riferiscono alle
                            tempistiche indicate dal D.Lgs 241/90.
                            Nel caso di normativa specifica è possibile modificare le date proposte.`,
                        TITLE_FIRST_SESSION_DATE: '',
                        TEXT_FIRST_SESSION_DATE: `Le scadenze proposte si riferiscono alle tempistiche
                            indicate dal D.Lgs 241/90. Nel caso di normativa specifica è possibile modificare le date proposte.`,
                        TITLE_EXPIRATION_DATE: '',
                        TEXT_EXPIRATION_DATE: `Le scadenze proposte si riferiscono alle tempistiche indicate dal D.Lgs 241/90.
                            Nel caso di normativa specifica è possibile modificare le date proposte.`,
                        TEXT_ADDRESS: `Puoi inserire il link della conferenza online (es. https://meet.lync.com/engit-eng)`,
                        TITLE_ADDRESS: ''
                    }
                },
                DATE_UPDATE: {
                    TITLE: 'Date modificate',
                    END_INTEGRATION_DATE:
                        'Termine per la richiesta di integrazioni',
                    END_OPINION_DATE: 'Termine per l’espressione dei pareri',
                    FIRST_SESSION_DATE: 'Prima sessione simultanea',
                    EXPIRATION_DATE: 'Data termine',
                    TOOLTIP: {
                        TITLE_END_INTEGRATION_DATE: '',
                        TEXT_END_INTEGRATION_DATE: `Le scadenze proposte si riferiscono
                            alle tempistiche indicate dal D.Lgs 241/90. Nel caso di normativa
                            specifica è possibile modificare le date proposte.`,
                        TITLE_END_OPINION_DATE: '',
                        TEXT_END_OPINION_DATE: `Le scadenze proposte si riferiscono alle
                            tempistiche indicate dal D.Lgs 241/90.
                            Nel caso di normativa specifica è possibile modificare le date proposte.`,
                        TITLE_FIRST_SESSION_DATE: '',
                        TEXT_FIRST_SESSION_DATE: `Le scadenze proposte si riferiscono alle tempistiche
                            indicate dal D.Lgs 241/90. Nel caso di normativa specifica è possibile modificare le date proposte.`,
                        TITLE_EXPIRATION_DATE: '',
                        TEXT_EXPIRATION_DATE: `Le scadenze proposte si riferiscono alle tempistiche indicate dal D.Lgs 241/90.
                            Nel caso di normativa specifica è possibile modificare le date proposte.`,
                        TEXT_ADDRESS: `Puoi inserire il link della conferenza online (es. https://meet.lync.com/engit-eng)`,
                        TITLE_ADDRESS: ''
                    }                
                },
                SUPPORT_CONTACTS: {
                    TITLE: 'Contatti per assistenza Conferenza dei Servizi',
                    FORM: {
                        NAME: 'Nome',
                        SURNAME: 'Cognome',
                        EMAIL: 'Email',
                        PHONE: 'Telefono'
                    },
                    PANEL: {
                        ADD_DEFINITION_BUTTON: 'Aggiungi file'
                    },
                    TABLE: {
                        TITLE: 'Contatti',
                        EMPTY_TEXT_LIST: 'Non ci sono contatti censiti'
                    },
                    MODAL: {
                        TITLE: 'Modifica contatto',
                        TEXT: ''
                    },
                    TOASTR: {
                        SUCCESS_SAVE: {
                            TITLE: 'Contatto salvato',
                            TEXT:
                                'I dati del nuovo contatto sono stati salvati correttamente.'
                        },
                        ERROR_SAVE: {
                            TITLE: 'Contatto non salvato',
                            TEXT:
                                'I dati del nuovo contatto non sono stati salvati.'
                        },
                        SUCCESS_UPDATE: {
                            TITLE: 'Contatto modificato',
                            TEXT:
                                'I dati del contatto sono stati modificato correttamente.'
                        },
                        ERROR_UPDATE: {
                            TITLE: 'Contatto non modificato',
                            TEXT:
                                'I dati del contatto non sono stati modificati.'
                        },
                        SUCCESS_DELETE: {
                            TITLE: 'Contatto eliminato',
                            TEXT:
                                'I dati del contatto sono stati eliminati correttamente.'
                        },
                        ERROR_DELETE: {
                            TITLE: 'Contatto non eliminato',
                            TEXT:
                                'I dati del nuovo contatto non sono stati eliminati.'
                        }
                    }
                },
                TOASTR: {
                    SUCCESS_SAVE: {
                        TITLE: 'Step 2 salvato',
                        TEXT:
                            'I dati del secondo step sono stati salvati correttamente'
                    },
                    ERROR_SAVE: {
                        TITLE: 'Step 2 non salvato',
                        TEXT: 'I dati del secondo step non sono stati salvati'
                    },
                    DATE_WARNING: {
                        TITLE: 'Attenzione',
                        TEXT:
                            'La data creazione pratica non può essere posteriore alla data odierna'
                    },
                    DATE_CREATION_WARNING: {
                        TITLE: 'Attenzione',
                        TEXT:
                            'La data selezionata non può essere anteriore alla data di creazione pratica'
                    }
                },
                SUPPORT_CONTACT_POPOVER: {
                    TITLE: 'Conferma cancellazione',
                    TEXT: 'Sicuro di volere cancellare il contatto?',
                    CANCEL_BTN: 'Annulla',
                    OK_BTN: 'Cancella'
                }
            },
            DOCUMENTATION: {
                TITLE: 'Documentazione',
                PANEL: {
                    ADD_FILE_BUTTON: 'Aggiungi file',
                    SYNCRONIZE_FILE_BUTTON: 'Sincronizza i file',
                    DELETE_LIST_FILE_BUTTON: 'Elimina la lista di file selezionati',
                    DOWNLOAD_ZIP_LIST_FILE_BUTTON: 'Scarica un file zip con tutti i file di questa lista',
                    DELETE_SELECTED_LIST_FILE_BUTTON: 'Elimina la lista di file selezionati da questa lista',
                    DEFAULT_FOLDER_NAME: 'Fascicolo'
                },
                SWITCH_LABEL: 'Visualizza tutti i documenti',
                DROP_FILE: {
                    MODAL: {
                        TEXT:
                            'Sicuro di volere caricare i file con associata la categoria scelta?',
                        TITLE: 'Conferma caricamento file',
                        CANCEL_BUTTON: 'Annulla',
                        OK_BUTTON: 'Ok'
                    }
                },
                FILE_COMPLIENT: 'Il file caricato è copia conforme a quella presente nel protocollo del Comune',
                ADDITIONAL: {
                    TITLE: 'Documentazione istanza',
                    FILE: {
                        CATEGORY: 'Categoria',
                        PARTICIPANT: 'Partecipanti',
                        CITY_REFERENCE: 'Comune di riferimento',
                        NAME: 'Nome',
                        URL: 'Url',
                        MODE: 'Cosa vuoi caricare?'
                    },
                    TABLE: {
                        TITLE: 'File caricati',
                        NAME: 'Nome',
                        CATEGORY: 'Categoria',
                        VISIBILITY: 'Visibilità',
                        STATUS_DOCUMENT: 'Alla Firma',
                        ACTIONS: 'Azioni'
                    },
                    BUTTON: { SAVE: 'Carica URL' },
                    TOOLTIP: {
                        MODE: {
                            TITLE: 'Cosa vuoi caricare?',
                            TEXT:
                                "E' possibile caricare un file e un URL ad una risorsa"
                        }
                    },
                    MODAL: {
                        TITLE: 'Modifica ',
                        TITLE_DISABLED: 'Visualizza file',
                        PARTICIPANT: {
                            TOOLTIP: {
                                TITLE: '',
                                CONTENT:
                                    'Lasciare vuoto per rendere visibile il documento a tutti i partecipanti'
                            }
                        }
                    },
                    SIGN_ALERT: {
                        TEXT: `Effettuare l'upload di soli file firmati digitalmente`
                    },
                    CONFORMITY_ALERT: {
                        TEXT:
                            'Gli elaborati presenti sono copia conforme di quelli presentati presso il comune capofila'
                    }
                },
                INTERACTIONS: {
                    TITLE: 'Documentazione interazioni'
                },
                SHARED: {
                    TITLE: 'Documentazione condivisa',
                    FILE: {
                        CATEGORY: 'Categoria',
                        PARTICIPANT: 'Visibilità',
                        MODEL: 'Modello',
                        FILE: 'File',
                        DASHBOARD: 'Appunti Dashboard',
                        MODE: 'Cosa vuoi caricare?',
                        NAME: 'Nome file'
                    },
                    BUTTON: {
                        SAVE: 'Carica file'
                    },
                    MODAL: {
                        TITLE: 'Caricamento documento condiviso',
                        TEXT: `Confermi di volere eseguire il caricamento di questo documento?
                            Una volta caricato i partecipanti a cui hai dato visibilità potranno modificare il file.`,
                        UPLOAD: 'Carica file',
                        CANCEL: 'Annulla'
                    },
                    TOOLTIP: {
                        MODE: {
                            TITLE: 'Cosa vuoi aggiungere?',
                            TEXT: `E' possibile caricare un file generico, un modello o un file appunti dashboard.
                                Il file generico potrà essere caricato dal proprio pc.
                                Il modello sarà inserito e precompilato da MeetPAd con le informazioni della conferenza.
                                Il file appunti dashboard sarà inserito da MeetPAd senza alcuna precompilazione.`
                        }
                    }
                },
                FIRMA: {
                    TITLE: 'Firma',
                    FILE: {
                        CATEGORY: 'Categoria',
                        PARTICIPANT: 'Firmatari per partecipante',
                        CALAMAIO: 'Firma il documento con Calamaio',
                        CALAMAIO_REMOTE: 'Firma digitale del documento in remoto',
                        CALAMAIO_UID: 'Username per la firma remota',
                        CALAMAIO_PWD: 'Password per la firma remota',
                        CALAMAIO_OTP: 'OTP (one time password)',
                        CALAMAIO_DOMAIN: 'Dominio da usare per la firma remota',
                        MODEL: 'Modello',
                        FILE: 'File',
                        DASHBOARD: 'Appunti Dashboard',
                        MODE: 'Cosa vuoi caricare?',
                        NAME: 'Nome file',
                        ALERT: {                            
                            BEFORE_LINK: `Scarica l'applicazione `,
                            LINK : 'Calamaio',
                            AFTER_LINK: 'per applicare la firma digitale',
                            WARNING_MESSAGE: 'Non è consigliato l’aggiornamento all’ultima versione Java, qualora fosse richiesto',
                        },
                        LABEL_PADES_CADES: 'Firma in formato .pdf (PADES)',
                    },
                    REMOTESIGN: {
                        TITLE: 'Firma il documento con Calamaio',
                        CALAMAIO_REMOTE: 'Firma digitale del documento in remoto',
                        CALAMAIO_UID: 'Username per la firma remota',
                        CALAMAIO_PWD: 'Password per la firma remota',
                        CALAMAIO_OTP: 'OTP (one time password)',
                        CALAMAIO_DOMAIN: 'Dominio da usare per la firma remota',
                    },
                    TABLE: {
                        TITLE: 'File caricati',
                        NAME: 'Nome',
                        OWNER: 'Proprietario',
                        STATUS: 'Stato',
                        ACTIONS: 'Azioni'
                    },
                    STATUS: {
                                UNLOCKED:'Sbloccato',
                                SIGNING: "In Firma",
                                LOCKED: "Bloccato",
                                SIGNED: "Firmato",
                                COMPLETED: "Completo"
                            },
                    BUTTON: {
                        SAVE: 'Carica file'
                    },                    
                    MODAL: {
                        TITLE: 'Caricamento documento',
                        TITLE_REMOTE: 'Firma del file in remoto',
                        TITLE_CALAMAIO: 'Firma del file in calamaio',
                        TEXT: `Esegui l'upload del documento firmato.
                            Una volta caricato i partecipanti a cui hai dato visibilità potranno modificare il file.`,
                        TEXT_CALAMAIO_SELECTED: `Esegui l'upload del documento e segui i passi per completare la firma con Calamaio.
                            Una volta completato il caricamento i partecipanti a cui hai dato visibilità potranno modificare il file.`,
                        UPLOAD: 'Carica documento',
                        SIGN_REMOTE: 'Firma il file in remoto',
                        CANCEL: 'Annulla',
                        SIGN_CHOISE_TITLE: 'Scegli il metodo di firma',
                        SIGN_CHOISE_TEXT: `Esegui l'upload del documento firmato oppure se il bottone "Firma con Calamaio" è abilitato, puoi eseguire la firma digitale semplicemente usando la tua carta nazionale dei servizi e l'apposito lettore.
                        Se il bottone risulata disabilitato installa l'applicazione Calamaio.`,
                        CALAMAIO_SIGN_BUTTON: 'Firma con Calamaio',
                    },
                    TOOLTIP: {
                        MODE: {
                            TITLE: 'Cosa vuoi aggiungere?',
                            TEXT: `E' possibile caricare un file generico, un modello o un file appunti dashboard.
                                Il file generico potrà essere caricato dal proprio pc.
                                Il modello sarà inserito e precompilato da MeetPAd con le informazioni della conferenza.
                                Il file appunti dashboard sarà inserito da MeetPAd senza alcuna precompilazione.`
                        },
                        CALAMAIO: {
                            TITLE: 'Firma con Calamaio',
                            TEXT: `Abilitando la firma con Calamaio, puoi eseguire la firma digitale semplicemente usando la tua carta nazionale dei servizi e l'apposito lettore.`,
                        },
                        CALAMAIO_REMOTE: {
                            TITLE: 'Firma remota',
                            TEXT: `In alternativa alla firma fatta con il software Calamaio, abilitando questo campo, puoi eseguire la firma del file semplicemente usando le tue credenziali da usare per il server di firma digitale remota.`,
                            UID: `Nome utente da usare per la firma remota`,
                            PWD: `Password da usare per la firma remota.`,
                            OTP: `One Time Password da usare per la firma remota.`,
                            DOMAIN: `Nel caso in cui la firma remota sia stata acquistata direttamente da Aruba e non emessa da Regione Marche, utilizzare il dominio rilasciato dal fornitore. Nel caso in cui la firma remota sia stata emessa da Regione Marche, NON è necessario compilare il dominio.`,
                            ERROR: `I campi username, password e OTP (One Time Password) sono obbligatori per poter effettuare la firma remota.`
                        }
                    }
                },
                PRELIMINARY: {
                    TITLE: 'Documentazione preistruttoria',
                    TITLE_SUAP: 'Documentazione preliminare',
                    FILE: {
                        CATEGORY: 'Categoria',
                        PARTICIPANT: 'Partecipanti',
                        CITY_REFERENCE: 'Comune di riferimento'
                    },
                    TABLE: {
                        TITLE: 'File caricati',
                        NAME: 'Nome',
                        CATEGORY: 'Categoria',
                        VISIBILITY: 'Visibilità',
                        ACTIONS: 'Azioni'
                    },
                    MODAL: {
                        TITLE: 'Modifica ',
                        TITLE_DISABLED: 'Visualizza file',
                        PARTICIPANT: {
                            TOOLTIP: {
                                TITLE: '',
                                CONTENT:
                                    'Lasciare vuoto per rendere visibile il documento a tutti i partecipanti'
                            }
                        }
                    }
                },
                INDICTION: {
                    TITLE: 'Nota di invito',
                    INDICTION_MODAL: {
                        TITLE: 'Aggiungi documento di indizione',
                        TEXT: `Aggiungendo il documento di indizione verranno trasmesse le mail di invito,
                            tutti i partecipanti potranno accedere alle informazioni della conferenza in oggetto. Sei sicuro di procedere?`,
                        OK_BUTTON: 'Ok',
                        CANCEL_BUTTON: 'Annulla',
                        PARTICIPANT_ALERT:
                            "Verifica la lista dei Partecipanti e assicurati di aver inserito l'elenco completo degli invitati.",
                        SUMMARY_DATE: 'Riepilogo scadenze conferenza'
                    },
                    FILE: {
                        PROTOCOL_NUMBER: 'Numero di protocollo',
                        PROTOCOL_DATE: 'Data di protocollo',
                        INVENTORY_NUMBER: 'Numero di repertorio ',
                        INVENTORY_SUAP_NUMBER: 'Numero provvedimento'
                    },
                    FILE_ALERT: {
                        LINK: 'template',
                        BEFORE_LINK: 'Scaricare il',
                        AFTER_LINK:
                            'da compilare e allegare come nota di invito'
                    },
                    EMPTY_DATE: 'non inserita'
                },
                TOASTR: {
                    SUCCESS_SAVE: {
                        TITLE: 'Step 4 salvato',
                        TEXT:
                            'I dati del terzo step sono stati salvati correttamente'
                    },
                    ERROR_SAVE: {
                        TITLE: 'Step 4 non salvato',
                        TEXT: 'I dati del terzo step non sono stati salvati'
                    },
                    SUCCESS_SAVE_FILE: {
                        TITLE: 'File salvato',
                        TEXT: 'Il file è stato salvato correttamente'
                    },
                    ERROR_SAVE_FILE: {
                        TITLE: 'File non salvato',
                        TEXT: 'Non è stato possibile salvare il file'
                    },
                    ERROR_REMOTE_SIGNING_FILE: {
                        TITLE: 'Messaggio di firma',
                        TEXT: 'Non è stato possibile salvare il file. Nel caso di firma remota verificare che le credenziali di acceso al server di firma siano valide.'
                    },
                    SUCCESS_DELETE_FILE: {
                        TITLE: 'File eliminato',
                        TEXT: 'Il file è stato eliminato correttamente'
                    },
                    ERROR_DELETE_FILE: {
                        TITLE: 'File non eliminato',
                        TEXT: 'Non è stato possibile eliminare il file'
                    },
                    OO_ERROR: {
                        TITLE: 'Impossibile modificare il file',
                        TEXT: `Non è possibile modificare il file selezionato. Contattare l'amministratore di sistema`
                    },
                    SUCCESS_SIGN_FILE: {
                        TITLE: 'File firmato',
                        TEXT: 'Il file è stato firmato con successo'
                    },
                    FILE_UNLOCK_ERROR: {
                        TITLE: 'Impossibile sbloccare il file',
                        TEXT: `Non è possibile sbloccare il file.
                            Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
                    },
                    FILE_SIGN_ERROR: {
                        TITLE: 'Impossibile firmare il file',
                        TEXT: `Non è possibile firmare il file.
                            Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
                    },
                    SUCCESS_ZIP_FILE: {
                        TITLE: 'File ZIP',
                        TEXT: `File ZIP compresso con successo`
                    },
                    SUCCESS_SYNC_FILE: {
                        TITLE: 'File sincronizzati',
                        TEXT: `File sincronizzati con successo`
                    },
                    ERROR_SYNC_FILE: {
                        TITLE: 'Impossibile sincronizzare i file',
                        TEXT: `Non è stato possibile sincronizzare i file.
                        Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
                    },
                    ERROR_ZIP_FILE: {
                        TITLE: 'Impossibile sincronizzare il file ZIP',
                        TEXT: `Non è stato possibile generare il file ZIP.
                        Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
                    },
                    SUCCESS_DELETE_LIST_FILE: {
                        TITLE: 'File eliminati',
                        TEXT: 'I file selezionati stato stati eliminati correttamente'
                    },
                    WARNING_DELETE_LIST_FILE: {
                        TITLE: 'Nessun file selezionato',
                        TEXT: 'Non è stato selezionato nessun file da eliminare'
                    },
                    ERROR_DELETE_LIST_FILE: {
                        TITLE: 'File non eliminati',
                        TEXT: 'Non è stato possibile eliminare i file'
                    },
                },
                CONFIRM_DELETE_MODAL: {
                    TITLE: 'Conferma cancellazione file',
                    MESSAGE: 'Sicuro di volere cancellare il file?',
                    OK_BUTTON: 'Ok',
                    CANCEL_BUTTON: 'Annulla'
                },
                CONFIRM_DELETE_LIST_MODAL: {
                    TITLE: 'Conferma cancellazione file',
                    MESSAGE: 'Sicuro di volere cancellare la lista dei file selezionati?',
                    OK_BUTTON: 'Ok',
                    CANCEL_BUTTON: 'Annulla'
                },
                DOWNLOAD_SIGNATURE_FILE_MODAL: {
                    TITLE: 'Scarica documento',
                    DOWNLOAD_MESSAGE: 'Scaricare il documento da firmare ed applica la firma digitale',
                    DOWNLOAD_BUTTON: 'Scarica',
                    CANCEL_BUTTON: 'Annulla'
                },
                EMPTY_TABLE: 'Non ci sono documenti caricati',
                STATUS_DOCUMENT: {
                    TITLE: 'Alla Firma',
                    REJECTED: 'RIFIUTATO',
                    SIGNED: 'FIRMATO',
                    DRAFT: 'IN ATTESA'
                },
                STATUS_UPLOAD_MODAL: {
                    TITLE: 'Caricamento in corso'
                },
                STATUS_DOWNLOAD_MODAL: {
                    TITLE: 'Download in corso'
                }
            },
            PARTICIPANTS: {
                TITLE: 'Partecipanti',
                PANEL: {
                    ADD_APPLICANT_BUTTON: 'Aggiungi partecipante',
                    DELETE_LIST_PARTICIPANTS_BUTTON: 'Elimina la lista dei partecipanti selezionati',
                    DELETE_SELECTED_LIST_PARTICIPANTS_BUTTON: 'Elimina la lista dei partecipanti selezionati da questa lista',
                },
                TABLE: {
                    TITLE: 'Partecipanti',
                    ACTOR: 'Attore',
                    ROLE: 'Ruolo',
                    PEC: 'PEC',
                    FISCALCODE: 'Codice fiscale',
                    DESCRIPTION: 'Descrizione ente',
                    DETERMINATION_EXPRESSED: 'Trasmissione pareri',
                    INTEGRATION_REQUIRED: 'Richiesta integrazioni',
                    ACTIONS: 'Azioni',
                    ACTIONS_BUTTONS: {
                        VIEW: 'Visualizza',
                        EDIT: 'Modifica',
                        ENTER: 'Entra',
                        DELETE: 'Elimina',
                        SKYPE: 'Conferenza online'
                    }
                },
                MODAL: {
                    TITLE: 'Soggetto partecipante',
                    ACTOR: {
                        TITLE: 'Anagrafica ente',
                        APPLICANT_TITLE: 'Anagrafica',
                        AUTHORITY: 'Ente',
                        AUTHORITY_PLACEHOLDER: 'Seleziona ente',
                        APPLICANT_AUTHORITY: 'Partecipante',
                        ROLE: 'Ruolo',
                        AUTHORITY_DESCRIPTION: 'Desc. Ente Competente',
                        AUTHORITY_DESCRIPTION_SUAP: 'Desc. Ente',
                        APPLICANT_AUTHORITY_DESCRIPTION:
                            'Denominazione impresa',
                        PEC: 'PEC Ente Competente',
                        PEC_SUAP: 'PEC Ente',
                        APPLICANT_PEC: 'PEC',
                        FISCALCODE: 'C.F. Ente Competente',
                        FISCALCODE_SUAP: 'C.F. Ente ',
                        APPLICANT_CF: 'Codice fiscale',
                        COMPETENCE: 'Competenza',
                        DETERMINATION: 'Esprime determinazione'
                    },
                    USER: {
                        LIST_TITLE: 'Lista',
                        EMPTY_TEXT_LIST: 'Non ci sono utenti censiti',
                        TITLE: 'Utenti',
                        TITLE_DISABLED: 'Utenti',
                        NAME: 'Nome',
                        SURNAME: 'Cognome',
                        PROFILE: 'Profilo',
                        EMAIL: 'Email',
                        //ADDRESSPEC : 'Segna indirizzo come PEC',
                        CF: 'Codice fiscale',
                        ACTIONS: 'Azioni',
                        TOOLTIP: {
                            CONTENT: `In questa sezione è possibile aggiungere uno o più utenti,
                                appartenenti all'amministrazione partecipante indicata sopra,
                                che verranno invitati e accreditati direttamente per partecipare alla conferenza in oggetto.`
                        }
                    },
                    EMAIL: {
                        TITLE: 'Altre email',
                        LABEL: "Inserisci un'email"
                    },
                    NOTES: {
                        TITLE: 'Note'
                    }
                },
                CONFIRM_DELETE_LIST_MODAL: {
                    TITLE: 'Conferma cancellazione partecipante',
                    MESSAGE: 'Sicuro di volere cancellare la lista dei partecipanti selezionati?',
                    OK_BUTTON: 'Ok',
                    CANCEL_BUTTON: 'Annulla'
                },
                POPOVER: {
                    TITLE: 'Conferma cancellazione',
                    TEXT: "Sicuro di volere cancellare l'utente?",
                    CANCEL_BTN: 'Annulla',
                    OK_BTN: 'Cancella'
                },
                TOASTR: {
                    SUCCESS_SAVE: {
                        TITLE: 'Partecipante salvato',
                        TEXT:
                            'I dati del partecipante sono stati salvati correttamente'
                    },
                    SUCCESS_DELETE: {
                        TITLE: 'Partecipante eliminato',
                        TEXT: 'Il partecipante è stato eliminato correttamente'
                    },
                    SUCCESS_USER_DELETE: {
                        TITLE: 'Utente eliminato',
                        TEXT: "L'utente è stato eliminato correttamente"
                    },
                    SUCCESS_USER_SAVE: {
                        TITLE: 'Utente salvato',
                        TEXT:  "I dati dell'utente sono stati salvati correttamente"
                    },
                    ERROR_USER_SAVE: {
                        TITLE: 'Utente non salvato',
                        TEXT:  "Non è stato possibile salvare i dati dell'utente"
                    },
                    ERROR_SAVE: {
                        TITLE: 'Partecipante non salvato',
                        TEXT: 'Non è stato possibile salvare i dati del partecipante'
                    },
                    SUCCESS_SUBMIT: {
                        TITLE: 'Step 3 salvato',
                        TEXT:
                            'I dati del quarto step sono stati salvati correttamente'
                    },
                    WARNING_EMPTY_AUTHORITY: {
                        TITLE:
                            'Non è possibile aggiungere un nuovo partecipante',
                        TEXT:
                            'Non è possibile aggiungere un nuovo ente partecipante perchè sono già tutti invitati.'
                    },
                    SUCCESS_DELETE_LIST_PARTICPANT: {
                        TITLE: 'Partecipanti eliminati',
                        TEXT: 'I partecipanti selezionati stato stati eliminati correttamente'
                    },
                    WARNING_DELETE_LIST_PARTICPANT: {
                        TITLE: 'Nessun partecipante selezionato',
                        TEXT: 'Non è stato selezionato nessun partecipante da eliminare'
                    },
                    ERROR_DELETE_LIST_PARTICPANT: {
                        TITLE: 'Partecipanti non eliminati',
                        TEXT: 'Non è stato possibile eliminare i partecipanti'
                    },
                },
                CONFIRM_DELETE_MODAL: {
                    TITLE: 'Conferma cancellazione partecipante',
                    MESSAGE: 'Sicuro di volere cancellare il partecipante?',
                    OK_BUTTON: 'Ok',
                    CANCEL_BUTTON: 'Annulla'
                }
            },
            SUMMARY: {
                TITLE: 'Riepilogo',
                TOASTR: {
                    SUCCESS: {
                        TITLE: 'Stato della conferenza in bozza',
                        TEXT: `I dati della conferenza sono stati salvati correttamente e lo stato è passato In Bozza.
                        E' ora possibile procedere con il caricamento del file di indizione.`,
                        PRELIMINARY_TEXT: `I dati della conferenza sono stati salvati correttamente e lo stato è passato In Bozza.
                        E' ora possibile procedere con il caricamento della nota di invito alla preistruttoria.`
                    },
                    ERROR: {
                        TITLE: 'Conferenza non salvata',
                        TEXT:
                            'Non è stato possibile salvare i dati della conferenza'
                    }
                },
                TOOLTIP: {
                    CONTENT:
                        'La conferenza potrà passare nello stato di bozza solo quando il richiedente avrà finito di eseguire le sue modifiche oppure il creatore ritiri le autorizzazioni di modifica',
                    TITLE: 'Salva in bozza'
                }
            },
            EVENTS: {
                TITLE: 'Eventi',
                PANEL: {
                    ADD_EVENT_BUTTON: 'Crea evento',
                    EMPTY_MESSAGE: 'Non ci sono eventi',
                    REFRESH_LIST: 'Ricarica lista'
                },
                SEARCH_PANEL: {
                    TITLE: 'Ricerca eventi',
                    EVENT_TYPE: 'Tipo evento',
                    AUTHORITY: 'Ente',
                    ADMINISTRATION_TYPE: 'Tipologia amministrazione',
                    SEARCH_BUTTON: 'Cerca',
                    RESET_BUTTON: 'Reset'
                },

                TABLE: {
                    DATE: 'Data',
                    TYPE: 'Tipo',
                    SUBJECT: 'Oggetto',
                    SENDER: 'Mittente',
                    STATUS_DOCUMENT: 'Alla Firma',
                    ACTIONS: 'Azioni',
                    STATUS: {
                        REJECTED: 'RIFIUTATO',
                        SIGNED: 'FIRMATO',
                        DRAFT: 'IN ATTESA'
                    }
                },
                MODAL: {
                    TITLE_CONFERENCE_CREATED: 'Creazione conferenza',
                    TITLE_CONFERENCE_INDICTION: 'Indizione conferenza',
                    TITLE_REQUEST_INTEGRATIONS: 'Richiesta integrazioni',
                    TITLE_CONFERENCE_MEMO: 'Caricamento verbale conferenza',
                    TITLE_CONFERENCE_MEMO_INTERNAL: 'Caricamento verbale interno conferenza',
                    TITLE_CONFERENCE_CLOSING: 'Chiusura conferenza',
                    TITLE_OPINION_EXPRESS: 'Espressione parere',
                    TITLE_FINAL_RESOLUTION: 'Determinazione finale',
                    TITLE_GENERIC_COMMUNICATION: 'Comunicazione generica',
                    TITLE_INTEGRATION_REGISTERED:
                        'Trasmissione integrazioni protocollate',
                    TITLE_INTEGRATION_ONLY_ONE_REQUEST:
                        'Richiesta unica di Integrazioni',
                    TITLE_INTEGRATION_CLOSED: 'Chiusura integrazioni',
                    TITLE_INTEGRATION_SEND: 'Invio integrazioni',
                    TITLE_EDIT_DATA: 'Modifica data',
                    PANEL: {
                        TITLE_CONFERENCE_CREATED: 'Identificativo pratica',
                        TITLE_CONFERENCE_INDICTION: 'Identificativo pratica',
                        TITLE_REQUEST_INTEGRATIONS: 'Identificativo pratica',
                        TITLE_CONFERENCE_MEMO: 'Caricamento verbale conferenza',
                        TITLE_CONFERENCE_MEMO_INTERNAL: 'Caricamento verbale interno conferenza',
                        TITLE_CONFERENCE_CLOSING: 'Chiusura conferenza',
                        TITLE_OPINION_EXPRESS: 'Espressione parere',
                        TITLE_FINAL_RESOLUTION: 'Determinazione finale',
                        TITLE_GENERIC_COMMUNICATION: 'Identificativo pratica',
                        TITLE_INTEGRATION_REGISTERED: 'Identificativo pratica',
                        TITLE_EDIT_DATA: 'Modifica data',
                        DATE: 'Data',
                        TYPE: 'Tipo',
                        SUBJECT: 'Oggetto della richiesta',
                        SUBJECT_GENERIC_COMMUNICATION:
                            'Oggetto della comunicazione',
                        SENDER: 'Mittente',
                        BODY: 'Testo del messaggio',
                        DETERMINATION_TYPE: 'Tipo di parere',
                        PROTOCOL_NUMBER: 'Numero protocollo',
                        PROTOCOL_DATE: 'Data di protocollo',
                        RESOLUTION_NUMBER: 'Numero della determinazione',
                        RESOLUTION_DATE: 'Data della determinazione',
                        ADMINISTRATION: 'Amministrazione',
                        ATTACHED_FILE: 'Documento allegato',
                        MAIN_DOCUMENT: 'Documento principale (Massimo 20 MB)',
                        ATTACHMENTS_LIST: 'Lista degli allegati (opzionali)',
                        PROTOCOL_NUMBER_DATE: 'Dati protocollo',
                        ATTACHED_FILE_ALERT: {
                            LINK: 'template',
                            BEFORE_LINK: 'Scaricare il',
                            AFTER_LINK:
                                'da compilare e allegare come verbale della conferenza',
                           // SIZETOOLARGE_LINK: 'Non si posso accettare file più grandi di 20 Mb'
                        },
                        NOTE: 'Note',
                        RESULT: 'Esito',
                        RECIPIENTS: 'Destinatari',
                        VISIBILITY: 'Visibilità',
                        DATE_TYPE: 'Tipo di Data',
                        EDIT_DATE: 'Data della Modifica',
                        OLD_DATE: 'Data impostata',
                        LAST_VALUE: 'Data precedente',
                        DELAY_DATE: 'Ritarda la date del seguente numero di giorni',
                        DELAY_DATE_TERMINE: 'Aggiungi giorni alla scadenza',
                        NEW_DATE: 'Nuova Data',
                        EDIT_REASON: 'Motivazione della Modifica',
                        TYPE_TERMINE: 'Termine',
                        TYPE_TERMINE_ESPRESSIONE_PARERI: 'Termine Espressione pareri',
                        TYPE_TERMINE_RICH_INTEG: 'Termine Richiesta Integrazione',
                        TYPE_PRIMA_SESS_SIMUL: 'Prima Sessione Simultanea'
                    },
                    SAVE: 'Salva e invia'
                },
                CONFIRM_MODAL: {
                    TITLE_INTEGRATION_CLOSED: 'Chiusura integrazioni',
                    TEXT_INTEGRATION_CLOSED: "Confermi di voler procedere alla Chiusura Integrazioni? In seguito non sarà più possibile ricevere richieste di integrazioni da parte dei partecipanti. Sarà possibile procedere con le fasi successive: trasmettere l'eventuale richiesta unica di integrazioni al richiedente o caricare il verbale della conferenza.",
                    
                    TITLE_EDIT_DATE: 'Modifica Data',
                    TEXT_EDIT_DATE: "Salvando questo evento verrà inviata una mail a tutti i partecipanti per avvisare della modifica della data in oggetto. Sei sicuro di voler procedere?",
                    
                },
                ERROR_MODAL: {
                    TITLE_ERROR_DATE: 'Modifica Data',
                    TEXT_ERROR_DATE_CONTROL: "I nuovi valori selezionati per le date sono tutti uguali a quelli già presenti",
                    TITLE_ERROR_ATTACH: 'Inserimento allegato',
                    ATTACH_TO_LARGE: 'Il file inserito è troppo grande. Non superare 20Mb',
                    TEXT_ERROR_ATTACH_CONTROL: "Un allegato è stato selezionato ma non salvato"
                },
                ATTENTION_MODAL: {
                    TITLE: 'Conferenza chiusa',
                    MESSAGE: `La conferenza è stata chiusa con successo
                         di conseguenza non sarà possibile effettuare ulteriori modifiche ma sarà
                         accessibile in visualizzazione.
                    `,
                    OK_BUTTON: 'Ok'
                },
                TOASTR: {
                    SUCCESS: {
                        TITLE: 'Evento creato',
                        TEXT: "L'evento è stato creato correttamente"
                    },
                    ERROR: {
                        TITLE: 'Evento non creato',
                        TEXT: "Non è stato possibile creare l'evento"
                    }
                }
            },
            ACCREDITATION: {
                TITLE: 'Accreditamento',
                TABLE: {
                    TITLE:
                        'Elenco delle richieste di accreditamento alla conferenza',
                    NAME: 'Nome',
                    SURNAME: 'Cognome',
                    FISCAL_CODE: 'Codice fiscale',
                    EMAIL: 'Email',
                    USER: 'Utente',
                    ACTOR: 'Attore',
                    ROLE: 'Ruolo',
                    PARTICIPANT_DESCRIPTION: 'Ente',
                    PROFILE: 'Profilo partecipante',
                    ACTIONS: 'Dettaglio',
                    FILE: 'Documento di Designazione',
                    EMPTY_MESSAGE: 'Non ci sono richieste di accreditamento',
                    REFRESH_LIST: 'Ricarica lista',
                    STATE: {
                        TITLE: 'Stato',
                        WAITING: 'In attesa',
                        OK: 'Approvato',
                        REJECTED: 'Rifiutato'
                    }
                },
                TOASTR: {
                    SUCCESS_CONFIRM: {
                        TEXT:
                            "L'accreditamento è stato approvato correttamente",
                        TITLE: 'Accreditamento approvato'
                    },
                    SUCCESS_REJECT: {
                        TEXT:
                            "L'accreditamento è stato rigettato correttamente",
                        TITLE: 'Accreditamento rigettato'
                    }
                },
                MODAL: {
                    TITLE: 'Accreditamento alla conferenza',
                    CANCEL_BUTTON: 'Rigetta richiesta',
                    CONFIRM_BUTTON: 'Approva richiesta',
                    CLOSE_BUTTON: 'Chiudi',
                    DOWNLOAD: 'Download file'
                }
            },
            PEC: {
                TITLE: 'Messaggi',
                TABLE: {
                    TITLE: 'Messaggi',
                    EMPTY_MESSAGE: 'Non sono stati inviati messaggi',
                    SENDER: 'Mittente',
                    RECIPIENT: 'Destinatario',
                    STATE: 'Stato',
                    EVENT: 'Evento',
                    DATE: 'Data invio',
                    ACTIONS: 'Dettaglio'
                },
                SEARCH: {
                    TITLE: 'Ricerca messaggi',
                    SENDER: 'Mittente',
                    RECIPIENT: 'Destinatario',
                    STATE: 'Stato',
                    EVENT: 'Evento',
                    DATE: 'Data invio',
                    ALL: 'Tutti'
                },
                MODAL: {
                    TITLE: 'Dettaglio messaggio'
                },
                FORM: {
                    SENDER: 'Mittente',
                    RECIPIENT: 'Destinatario',
                    RECIPIENT_PEC: 'Email destinatario',
                    STATE: 'Stato',
                    EVENT: 'Evento',
                    DATE: 'Data invio',
                    ACTIONS: 'Dettaglio',
                    STATUS_MESSAGE: 'Eventuale messaggio di errore'
                }
            },
            CHANGE: {
                TOASTR: {
                    AUTHORIZATION: {
                        TITLE: 'Operazione eseguita',
                        TEXT:
                            'Autorizzazione alle modifiche eseguita con successo'
                    },
                    UNAUTHORIZATION: {
                        TITLE: 'Operazione eseguita',
                        TEXT:
                            'Rimozione autorizzazione alle modifiche eseguita con successo'
                    },
                    COMPLETE: {
                        TITLE: 'Operazione eseguita',
                        TEXT: 'Termina modifiche eseguito con successo'
                    }
                }
            },
            VOTING: {
                PANEL: { ADD_VOTING_BUTTON: 'Crea nuova votazione' },
                TITLE: 'Votazioni',
                OPTIONS_ENUM: {
                    VOTAZIONE: 'Votazione',
                    CALENDARIZZAZIONE : 'Calendarizzazione',
                    RILEVAZIONE_PRESENZE : 'Rilevazione presenze',
                    PREPARAZIONE: 'Preparazione',
                    IN_CORSO: 'In corso',
                    ESITO_IMPOSTATO: 'Esito impostato',
                    TERMINATA: 'Terminata' ,
                    EVENTO: 'Evento',
                    VALUTAZIONE: 'Valutazione',
                    APPROVATA: 'Approvata',
                    NON_APPROVATA: 'Non approvata'
                },
                TABLE: {
                    ID: 'ID',
                    SUBJECT: 'Oggetto',
                    VOTING_TYPE: 'Tipo votazione',
                    VOTING_STATE: 'Stato votazione',
                    EMPTY_MESSAGE: 'Non ci sono votazioni',
                    ACTIONS: {
                        TITLE: 'Azioni'
                    },
                    MODAL: {
                        DELETE: {
                            TITLE: 'Annulla votazione',
                            TEXT:
                                "Sicuro di voler annullare la votazione selezionata? L'operazione non è reversibile",
                            CANCEL_BUTTON: 'Annulla',
                            OK_BUTTON: 'Ok'
                        },
                        START: {
                            TITLE: 'Avvia votazione',
                            TEXT:
                                'Sicuro di voler avviare la votazione selezionata? Cliccando ok permetterai ai partecipanti della conferenza di esprimere la propria opinione.',
                            CANCEL_BUTTON: 'Annulla',
                            OK_BUTTON: 'Avvia'
                        },
                        CLOSE: {
                            TITLE: 'Chiudi votazione',
                            TEXT:
                                'Sicuro di voler chiudere la votazione selezionata? Cliccando ok gli utenti non potranno più votare.',
                            CANCEL_BUTTON: 'Annulla',
                            OK_BUTTON: 'Termina'
                        },

                        TOASTR: {
                            DELETE: {
                                FAIL: {
                                    TEXT:
                                        'Non è stato possibile annullare la votazione',
                                    TITLE: 'Operazione non riuscita'
                                },
                                SUCCESS: {
                                    TEXT: 'Votazione annullata con successo',
                                    TITLE: 'Operazione conclusa'
                                }
                            },
                            START: {
                                FAIL: {
                                    TEXT:
                                        'Non è stato possibile avviare la votazione',
                                    TITLE: 'Operazione non riuscita'
                                },
                                SUCCESS: {
                                    TEXT: 'Votazione avviata con successo',
                                    TITLE: 'Operazione conclusa'
                                }
                            },
                            CLOSE: {
                                FAIL: {
                                    TEXT:
                                        'Non è stato possibile terminare la votazione',
                                    TITLE: 'Operazione non riuscita'
                                },
                                SUCCESS: {
                                    TEXT: 'Votazione terminata con successo',
                                    TITLE: 'Operazione conclusa'
                                }
                            },
                            EDIT: {
                                FAIL: {
                                    TEXT:
                                        'Non è stato possibile modificare la votazione',
                                    TITLE: 'Operazione non riuscita'
                                },
                                SUCCESS: {
                                    TEXT: 'Votazione modificata con successo',
                                    TITLE: 'Operazione conclusa'
                                }
                            },
                            ADD: {
                                FAIL: {
                                    TEXT:
                                        'Non è stato possibile aggiungere la votazione',
                                    TITLE: 'Operazione non riuscita'
                                },
                                SUCCESS: {
                                    TEXT: 'Votazione aggiunta con successo',
                                    TITLE: 'Operazione conclusa'
                                }
                            },
                            VOTE: {
                                FAIL: {
                                    TEXT:
                                        'Non è stato possibile aggiungere la votazione',
                                    TITLE: 'Operazione non riuscita'
                                },
                                SUCCESS: {
                                    TEXT: 'Votazione aggiunta con successo',
                                    TITLE: 'Operazione conclusa'
                                }
                            }
                        }
                    }
                },
                MODAL: {
                    TITLE: 'Crea nuova votazione',
                    EDIT_TITLE: 'Modifica votazione',
                    EVALUATE_TITLE: 'Valuta votazione',
                    SHOW_TITLE: 'Visualizza votazione',
                    FORM: {
                        TITLE: 'Votazione',
                        SUBJECT: 'Oggetto votazione',
                        VOTING_TYPE: 'Tipo votazione',
                        VOTING_RULE: 'Criterio votazione',
                        END_VOTING_DATE: 'Data scadenza votazione',
                        VOTING_DATE: 'Data votazione',
                        VOTING_STATE: 'Stato votazione',
                        VOTING_RESULT: 'Esito votazione',
                        VOTE: {
                            TITLE: 'Voti effettuati',
                            TITLE_SCHEDULING: 'Preferenze',
                            TITLE_PRESENCE: 'Presenze',
                            LIST: {
                                TITLE: 'Lista voti',
                                PARTICIPANT: 'Partecipante',
                                DATE: 'Data voto',
                                VOTE: 'Voto',
                                DETAIL: 'Motivazione'
                            },
                            EMPTY_TEXT_LIST: 'Nessun voto pervenuto'
                        }
                    }
                },
                VOTE: {
                    MODAL: {
                        TITLE: 'Esprimi il tuo voto',
                        YES_BUTTON: 'Si',
                        NO_BUTTON: 'No',
                        DETAIL: 'Spiegazione del voto',
                        CONFIRM_PRESENCE: "Conferma la tua presenza nella videoconferenza",
                        SCHEDULING_TITLE: 'Preferenza data votazione',
                        RILEVAZIONE_PRESENZE_TITLE : "Rilevazione presenza",
                    }
                },
                SWITCH_LABEL: 'Solo votazioni attive'
            },
            PROTOCOL: {
                TITLE: 'Protocolli',
                TABLE: {
                    TITLE:
                        'Elenco dei protocolli associati alla conferenza',
                    ID: 'Id conferenza',
                    DOCUMENTNAME: 'Nome documento',
                    EVENT: 'Evento',
                    ERROR: 'Errore',
                    PROTOCOLNUMBER: 'Numero protocollo',
                    PROTOCOLDATE: 'Data protocollo',
                    ACTIONS: 'Rielabora protocollo',
                    EMPTY_MESSAGE: 'Non ci sono protocolli associati',
                    REFRESH_LIST: 'Ricarica lista',
                    STATE: {
                        TITLE: 'Stato',
                        PROCESSING: 'Protocollazione in corso',
                        REGISTERED: 'Protocollato',
                        ERROR: 'In Errore'
                    }
                },
                MODAL: {
                    TITLE: 'Rielaborazione protocollo',
                    CANCEL_BUTTON: 'Annulla',
                    CONFIRM_BUTTON: 'OK',
                    PROTOCOL_EDIT_STATE: "Con questa operazione si tenterà di rieseguire la protocollazione. Si è sicuri di voler procedere?",
                },
                TOASTR: {
                    SUCCESS_CONFIRM: {
                        TEXT:
                            "Il protocollo è stato rimesso in elaborazione",
                        TITLE: 'Protocollo aggiornato'
                    },
                    SUCCESS_REJECT: {
                        TEXT:
                            "Lo stoato del protocollo non è stato aggiornato correttamente",
                        TITLE: 'Protocollo non aggiornato'
                    }
                },
            },
            ALERT: {
                CREATOR: {
                    AUTHORIZED: {
                        TITLE: 'Modifiche richiedente abilitate',
                        TEXT:
                            'In questo momento il richiedente è abilitato ad effettuare modifiche sulla conferenza. Finchè non avrà terminato o finchè non verranno ritirate le autorizzazioni alla modifica la conferenza non potrà passare nello stato di bozza.'
                    },
                    UNAUTHORIZED: {
                        TITLE: 'Modifiche richiedente disabilitate',
                        TEXT:
                            'In questo momento il richiedente non è abilitato ad effettuare modifiche sulla conferenza. Potrai autorizzarlo cliccando il pulsante in fondo "Autorizza alla modifica"'
                    }
                },
                APPLICANT: {
                    TITLE: 'Modifiche abilitate',
                    TEXT:
                        'Puoi effettuare delle modifiche alla conferenza. Una volta terminato clicca sul pulsante "Termina modifiche" in fondo.'
                }
            },
            VALIDATIONS_ERROR: {
                TITLE: 'Errore di validazione degli input'
            }
        },
        TOASTR: {
            NOT_FOUND: {
                TITLE: 'Conferenza non trovata',
                TEXT: 'La conferenza non è stata trovata.'
            }
        },
        AUTOCOMPLETE: {
            ITEMS_NOT_FOUND: 'Nessun elemento',
            INSERT_MIN_CHARACTERS: 'Scrivere per ricercare'
        }
    },

    PAGE_NOT_FOUND: {
        TITLE: 'Pagina non trovata',
        TEXT: 'Pagina non trovata!'
    },

    PAGE_NOTHING_TO_DO: {
        TITLE: 'Niente da fare',
        TEXT:
            'Non sono presenti Conferenze dei Servizi di interesse per questa utenza.'
    },

    PAGE_NOT_AUTHORIZED: {
        TITLE: 'Non autorizzato',
        TEXT:
            'Non si hanno le autorizzazioni necessarie per poter accedere alla Conferenza dei Servizi.'
    },

    INPUT: {
        SELECT_PLACEHOLDER: '- seleziona opzione -'
    },

    COMMON: {
        ALL: 'Tutti',
        NOBODY: 'Nessuno',
        NO_FILE_UPLOADING: 'Nessun file in caricamento.'
    },

    BUTTON: {
        VIEW: 'Visualizza',
        UPDATE: 'Modifica',
        CLOUD_EDIT: 'Modifica condivisa',
        DELETE: 'Elimina',
        SAVE: 'Salva',
        DOWNLOAD: 'Scarica',
        DOWNLOAD_PDF: 'Scarica PDF',
        SIGN: 'Firma',
        SEND_TO_SIGN: 'Invia alla Firma',
        UNLOCK: 'Sblocca',
        ADD: 'Aggiungi',
        SELECT: 'Seleziona',
        UNSELECT: 'Rimuovi selezione',
        SAVE_NEXT: 'Salva e continua',
        NEXT: 'Continua',
        CANCEL: 'Annulla',
        AUTHORIZE: 'Autorizza alla modifica',
        UNAUTHORIZE: 'Revoca permessi di modifica',
        COMPLETE: 'Termina modifiche',
        LINK: 'Apri link',
        VOTING: {
            VOTE: 'Vota',
            START: 'Inizia votazione',
            CLOSE: 'Termina votazione',
            DELETE: 'Annulla votazione',
            EDIT: 'Modifica votazione',  
            EVALUATE: 'Valuta votazione',           
            CONFIRM: 'Conferma'
        },
        UPDATESTATEPROTOCOL: 'Rielabora protocollo',
    },

    ERROR: {
        REQUIRED: 'Campo richiesto',
        INVALID: '{{value}} non valido',
        REGEX: 'Formato non valido',
        CF: {
            INVALID: 'Codice fiscale non valido'
        },
        EMAIL: {
            INVALID: 'Indirizzo e-mail non valido'
        },
        PHONE: {
            INVALID: 'Numero non valido'
        }
    },

    TOASTR: {
        UNAUTHENTICATED: {
            TITLE: 'Sessione scaduta',
            TEXT: 'Eseguire il login.'
        },
        MAINTENANCE: {
            TITLE: 'Sistema in manutenzione',
            TEXT: `Il sistema non è disponibile perchè sono in corso degli interventi di manutenzione.
                Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`,
            ID: 'Messaggio di errore da comunicare'
        },
        FILE_DOWNLOAD_ERROR: {
            TITLE: 'Impossibile scaricare il file',
            TEXT: `Non è possibile scaricare il file.
                Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
        }        
    },

    COMPONENT: {
        FILE_INPUT: {
            EMPTY_FILE: 'Nessun file'
        }
    },

    DROPZONE: {
        LABEL: 'Trascina qui i file o clicca sulla zona',
        BROWSE: {
            BTN_LABEL: 'Scegli file'
        }
    }
};
