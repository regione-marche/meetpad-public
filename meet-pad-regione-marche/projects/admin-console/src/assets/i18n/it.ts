export const IT = {
    APP_NAME: 'Console amministrativa',
    LOGOUT: 'ESCI',
    LOGIN: {
        TITLE: 'ACCEDI',
        COHESION: 'Cohesion',
        SPID: 'Spid'
    },
    PORTAL_LINK: 'Ritorna al portale',
    ADMIN_LINK: 'Pannello di amministrazione',
    UPLOAD_LINK: 'Stato caricamenti',
    DASHBOARD: {
        TITLE: 'Dashboard',
        HOME: 'Homepage'
    },

    COMMON: {
        ALL: 'Tutti',
        BACK: 'Torna indietro',
        NO_FILE_UPLOADING: 'Nessun file in caricamento.'
    },

    UPLOAD: { BREADCRUMB: 'Stato caricamenti' },
    BUTTON: {
        SET_PROCEDING: 'Abilita come amministrazione procedente',
        SET_NOT_PROCEDING: 'Disabilita come amministrazione procedente',
        CHANGE: 'Cambio stato conferenza',
        EDIT: 'Modifica',
        DELETE: 'Elimina',
        CANCEL: 'Reset',
        MAIL: 'Invio mail di indizione',
        UPLOAD: 'Carica file',
        DOWNLOAD: 'Scarica file',
        DELETE_FILE: 'Elimina file'
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
        SELECTED: 'selected',
        REPORT: 'Open Data',
    },
    MENU: {
        TITLE: 'MENU',
        LINKS: {
            AUTHORITIES: {
                TITLE: 'Gestione enti'
            },
            PROCEEDING: {
                TITLE: 'Gestione amministrazioni procedenti',
                EDIT: 'Modifica',
                MANAGERS: 'Gestione responsabili',
                CONFERENCE: 'Gestione conferenze'
            },
            CONFERENCE: {
                TITLE: 'Gestione conferenze'
            },
            USERS: 'Gestione utenti',
            CHATBOT: 'Gestione domande risposte chatbot',
            CONFERENCE_TYPE: 'Gestione rubriche per tipologia conferenza',
            REGISTRY: 'Gestione anagrafiche',
            PROTOCOLS: {
                TITLE: 'Gestione protocolli'
            }
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
    AUTHORITIES: {
        EDIT: {
            TITLE: 'Gestione enti',
            BREADCRUMB: { NEW: 'Nuovo ente', EDIT: 'Modifica ente' },
            FORM: {
                AREA: 'Regione',
                PROVINCE: 'Provincia',
                CITY: 'Comune',
                NAME: 'Ente',
                PEC: 'PEC',
                FISCAL_CODE: 'Codice fiscale',
                PROCEDING: 'Amministrazione procedente',
                ADMINISTRATIVE_TYPE: 'Tipo amministrazione',
                ISTAT_TYPE: 'Istat',
                OFFICE_CODE: 'Codice ufficio'
            },
            TOASTR: {
                NOT_FOUND: {
                    TEXT: "Non è stato possibile trovare l'ente cercato",
                    TITLE: 'Nessun ente trovato'
                },
                EDIT: {
                    SUCCESS: {
                        TEXT: 'Ente modificato con successo',
                        TITLE: 'Operazione conclusa con successo'
                    },
                    FAIL: {
                        TEXT: "Non è stato possibile modificare l'ente",
                        TITLE: 'Operazione non conclusa'
                    }
                },
                NEW: {
                    SUCCESS: {
                        TEXT: 'Ente creato con successo',
                        TITLE: 'Operazione conclusa con successo'
                    },
                    FAIL: {
                        TEXT: "Non è stato possibile creare l'ente",
                        TITLE: 'Operazione non conclusa'
                    }
                }
            }
        },
        PROCEDING: {
            TITLE: 'Gestione amministrazioni procedenti',
            HOME: {
                EDIT: 'Modifica amministrazione procedente',
                MANAGER: 'Gestione responsabili',
                CONFERENCE: 'Gestione conferenze'
            },
            CONFERENCE: {
                TITLE: 'Gestione conferenze',

                EDIT: {
                    TITLE: 'Conferenza',
                    ADMINISTRATION_LABEL: 'Amministrazione procedente',
                    MANAGER_LABEL: 'Responsabile',
                    ID: 'ID',
                    REQUEST_REFERENCE: 'Riferimento istanza',
                    TYPE: 'Tipologia',
                    STATE: 'Stato'
                },
                TOASTR: {
                    NOT_FOUND: {
                        TEXT:
                            'Non è stato possibile trovare la conferenza cercata',
                        TITLE: 'Nessuna conferenza trovata'
                    },
                    EDIT: {
                        SUCCESS: {
                            TEXT: 'Conferenza modificata con successo',
                            TITLE: 'Operazione conclusa con successo'
                        },                       
                    FAIL: {
                            TEXT:
                                'Non è stato possibile modificare la conferenza',
                            TITLE: 'Operazione non conclusa'
                        }
                    }
                },
                SEARCH: {
                    TITLE: 'Ricerca conferenza',

                    INPUT_LABEL: `Ricerca una conferenza utilizzando
                l'id oppure lo stato, la tipologia, il riferimento istanza,
                il richiedente, la provincia o il comune`,

                    SEARCH_BUTTON: 'Cerca',
                    RESET_BUTTON: 'Reset',
                    TOASTR: {
                        EMPTY_SEARCH: {
                            TITLE: 'Nessuna conferenza trovata',
                            TEXT:
                                'I criteri specificati non hanno prodotto risultati'
                        }
                    }
                },
                LIST: {
                    TITLE: 'Conferenze',
                    ID: 'ID',
                    REQUEST_REFERENCE: 'Riferimento istanza',
                    TYPE: 'Tipologia',
                    STATE: 'Stato',
                    PROCEEDING_ADMINISTRATION: 'Amministrazione procedente',
                    ACTIONS: {
                        TITLE: 'Espandi',
                        BUTTONS: {
                            EDIT: 'Modifica'
                        }
                    }
                }
            },
            EDIT: {
                TITLE: 'Modifica amministrazione procedente',
                SEARCH: {
                    TITLE: 'Ricerca ente',
                    INPUT_LABEL: 'Inserisci nome ente',
                    SEARCH_BUTTON: 'Cerca'
                },
                TABLE: {
                    NAME: 'Ente',
                    PEC: 'Pec',
                    PROCEDING: 'Amministrazione procedente',
                    ACTIONS: 'Azioni'
                },
                LIST: {
                    TITLE: 'Enti',
                    EMPTY_MESSAGE: 'Nessuna ente trovato',
                    TOASTR: {
                        TITLE: 'Enti',
                        EMPTY_MESSAGE: 'Nessuna ente trovato'
                    }
                },
                ACTIONS: {
                    TOASTR: {
                        SET: {
                            SUCCESS: {
                                TITLE: 'Enti',
                                TEXT:
                                    'Ente abilitato come Amministrazione Procedente'
                            },
                            FAIL: {
                                TITLE: 'Enti',
                                TEXT:
                                    'Non è stato possibile abilitare come Amministrazione Procedente'
                            }
                        },
                        UNSET: {
                            SUCCESS: {
                                TITLE: 'Enti',
                                TEXT:
                                    'Ente disabilitato come Amministrazione Procedente'
                            },
                            FAIL: {
                                TITLE: 'Enti',
                                TEXT:
                                    'Non è stato possibile disabilitare come Amministrazione Procedente'
                            }
                        }
                    }
                },
                PAGE: {
                    TITLE: 'Modifica ente',
                    NAME: 'Ente',
                    PEC: 'PEC',
                    PROCEDING: 'Amministrazione procedente'
                },
                TOASTR: {
                    NOT_FOUND: {
                        TEXT:
                            "Non è stato possibile trovare l'amministrazione cercata",
                        TITLE: 'Nessuna amministrazione trovata'
                    },
                    EDIT: {
                        SUCCESS: {
                            TEXT: 'Ente modificato con successo',
                            TITLE: 'Operazione conclusa con successo'
                        },
                        FAIL: {
                            TEXT: "Non è stato possibile modificare l'ente",
                            TITLE: 'Operazione non conclusa'
                        }
                    }
                }
            },
            MANAGER: {
                TITLE: 'Gestione responsabili',
                ACTIONS: {
                    TITLE: 'Azioni',
                    NEW_MANAGER: 'Aggiungi responsabile'
                },
                SEARCH: {
                    TITLE: 'Ricerca responsabile',
                    MANAGER_LABEL: 'Inserisci nome responsabile',
                    AUTHORITY_LABEL: 'Inserisci nome ente',
                    SEARCH_BUTTON: 'Cerca'
                },
                TABLE: {
                    NAME: 'Nome',
                    SURNAME: 'Cognome',
                    FISCAL_CODE: 'Codice fiscale',
                    AUTHORITY: 'Ente',
                    ACTIONS: 'Azioni'
                },
                LIST: {
                    TITLE: 'Responsabili',
                    EMPTY_MESSAGE: 'Nessun responsabile trovato',
                    TOASTR: {
                        TITLE: 'Responsabili',
                        EMPTY_MESSAGE: 'Nessuna responsabile trovato'
                    }
                },
                EDIT: {
                    BREADCRUMB: { NEW: 'Nuovo responsabile' },
                    TITLE: 'Responsabile',
                    NAME: 'Nome',
                    SURNAME: 'Cognome',
                    PEC: 'Pec',
                    FISCAL_CODE: 'Codice fiscale',
                    AUTHORITY: 'Ente',
                    TOASTR: {
                        NEW: {
                            SUCCESS: {
                                TITLE: 'Operazione conclusa con successo',
                                TEXT:
                                    'Il nuovo responsabile è stato inserito con successo'
                            },
                            FAIL: {
                                TITLE: 'Operazione non terminata',
                                TEXT:
                                    'Non è stato possibile inserire il responsabile'
                            }
                        }
                    }
                }
            }
        }
    },
    PROTOCOLS: {
        TITLE: 'Gestione protocolli',
        LIST: {
            TITLE: 'Protocolli',
            ID: 'Id Conferenza',
            EVENT: 'Evento',
            DOCUMENTNAME: 'Nome documento',
            ERROR: 'Errore',
            PROTOCOLNUMBER: 'Numero protocollo',
            PROTOCOLDATE: 'Data protocollo',
            PROTOCOLSTATE: 'Stato protocollo',
            ACTIONS: {
                TITLE: 'Rielabora protocollo',
                BUTTONS: {
                    EDIT: 'Modifica'
                }
            },
            STATE: {
                TITLE: 'Stato',
                PROCESSING: 'Protocollazione in corso',
                REGISTERED: 'Protocollato',
                ERROR: 'In Errore'
            },
            EMPTY_MESSAGE: 'Nessun protocollo trovato.',
        },
        SEARCH: {
            TITLE: 'Ricerca protocolli',

            INPUT_LABEL: `Ricerca ID conferenza per verificare eventuali errori di protocollazione`,

            SEARCH_BUTTON: 'Cerca',
            RESET_BUTTON: 'Reset',
            TOASTR: {
                EMPTY_SEARCH: {
                    TITLE: 'Nessun protcollo trovato',
                    TEXT:
                        'I criteri specificati non hanno prodotto risultati'
                }
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
    REGISTRY: {
        COMPANY: {
            SEARCH: {
                INPUT_LABEL:
                    'Inserisci nome impresa o Partiva Iva o Codice Fiscale',
                TITLE: 'Ricerca impresa',
                SEARCH_BUTTON: 'Cerca'
            },
            TABLE: {
                NAME: 'Nome',
                FISCAL_CODE: 'Codice fiscale',
                VAT: 'Partita IVA',
                ACTIONS: 'Azioni',
                EMPTY_MESSAGE: 'Nessuna impresa trovata.',
                TOASTR: {
                    EMPTY_MESSAGE: 'Non sono state trovate imprese',
                    TITLE: 'Nessuna impresa trovata'
                }
            }
        }
    },
    PRELOADING: {
        TITLE: 'Gestione tipologie conferenze',
        COMPANY: {
            TITLE: 'Imprese',
            ACTIONS: { NEW: 'Collega impresa' },
            LIST: { TITLE: 'Imprese precaricate' },
            TOASTR: {
                NOT_FOUND: {
                    TITLE: 'Impresa non trovata',
                    TEXT: "Non è stato possibile trovare l'impresa cercata"
                }
            },

            CONFIRM_DELETE_MODAL: {
                TITLE: 'Eliminazione impresa',
                MESSAGE:
                    "Sei sicuro di voler eliminare l'impresa? Questa azione è irreversibile",
                OK_BUTTON: 'Cancella',
                CANCEL_BUTTON: 'Annulla operazione'
            },
            EDIT: {
                BREADCRUMB: { NEW: 'Nuova impresa', EDIT: 'Modifica impresa' },
                SEARCH: 'Cerca impresa',
                TITLE: 'Gestione impresa',
                DENOMINATION: 'Denominazione',
                FISCAL_CODE: 'Codice fiscale',
                VAT_NUMBER: 'Partita IVA',
                LEGAL_FORM: 'Forma giuridica',
                AREA: 'Regione',
                PROVINCE: 'Provincia',
                CITY: 'Comune',
                ADDRESS: 'Indirizzo',
                STEP: 'Step modificabili',
                EDITABLE: 'Modifiche attive',
                TOASTR: {
                    DELETE: {
                        SUCCESS: {
                            TEXT: 'Impresa eliminata con successo',
                            TITLE: 'Eliminazione impresa'
                        },
                        FAIL: {
                            TEXT: "Impossibile terminare l'operazione",
                            TITLE: 'Eliminazione impresa'
                        }
                    },
                    NEW: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT: 'Impresa inserita con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                "Non è stato possibile inserire l'impresa nella lista di precaricamenti"
                        }
                    },
                    EDIT: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT: 'Impresa modificata con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                "Non è stato possibile modificare l'impresa nella lista di precaricamenti"
                        }
                    }
                }
            }
        },
        APPLICANT: {
            TITLE: 'Richiedenti',
            ACTIONS: { NEW: 'Aggiungi richiedente' },
            LIST: {
                TITLE: 'Richiedenti precaricati',
                EMPTY_MESSAGE: 'Nessuna richiedente trovato',
                TOASTR: {
                    TITLE: 'Richiedenti',
                    EMPTY_MESSAGE: 'Nessuna richiedente trovato'
                },
                TOASTR2: {
                    TITLE: 'Cambio di stato',
                    EMPTY_MESSAGE: 'Il tipo o lo stato della conferenza non permettono di effettuare il cambio di stato'
                } 
            },
            TOASTR: {
                NOT_FOUND: {
                    TITLE: 'Richiedente non trovato',
                    TEXT: 'Non è stato possibile trovare il richiedente cercato'
                }
            },
            TABLE: {
                NAME: 'Nome',
                SURNAME: 'Cognome',
                FISCAL_CODE: 'Codice fiscale',
                COMPANY: 'Impresa',
                ACTIONS: 'Azioni'
            },
            SEARCH: {
                TITLE: 'Ricerca richiedente',
                NAME_LABEL:
                    'Inserisci nome, cognome o codice fiscale richiedente',
                COMPANY_LABEL: 'Inserisci nome impresa',
                LINKED_LABEL: 'Solo richiedenti non associati a impresa',
                SEARCH_BUTTON: 'Cerca'
            },
            CONFIRM_DELETE_MODAL: {
                TITLE: 'Eliminazione richiedente',
                MESSAGE:
                    'Sei sicuro di voler eliminare il richiedente? Questa azione è irreversibile',
                OK_BUTTON: 'Cancella',
                CANCEL_BUTTON: 'Annulla operazione'
            },
            EDIT: {
                BREADCRUMB: {
                    NEW: 'Nuovo richiedente',
                    EDIT: 'Modifica richiedente'
                },
                TITLE: 'Gestione richiedente',
                SEARCH: 'Cerca un richiedente tramite nome o cognome',
                COMPANY: 'Impresa',
                EMAIL: 'Email',
                FISCAL_CODE: 'Codice fiscale',
                SURNAME: 'Cognome',
                NAME: 'Nome',
                TOASTR: {
                    NEW: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT: 'Il richiedente è stato inserito con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Non è stato possibile inserire il richiedente nella lista di precaricamenti'
                        }
                    },
                    EDIT: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT:
                                'Il richiedente è stato aggiornato con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Non è stato possibile modificare il richiedente nella lista di precaricamenti'
                        }
                    },
                    DELETE: {
                        SUCCESS: {
                            TEXT: 'Richiedente eliminato con successo',
                            TITLE: 'Eliminazione richiedente'
                        },
                        FAIL: {
                            TEXT:
                                'Non è stato possibile eliminare il richidente',
                            TITLE: 'Eliminazione richiedente'
                        }
                    }
                }
            }
        },
        PARTICIPANT: {
            TITLE: 'Partecipanti',
            ACTIONS: { NEW: 'Aggiungi partecipante' },
            LIST: {
                TITLE: 'Partecipanti precaricati',
                EMPTY_MESSAGE: 'Nessuna partecipante trovato',
                TOASTR: {
                    TITLE: 'Partecipanti',
                    EMPTY_MESSAGE: 'Nessuna partecipante trovato'
                }
            },
            TOASTR: {
                NOT_FOUND: {
                    TITLE: 'Partecipante non trovato',
                    TEXT:
                        'Non è stato possibile trovare il partecipante cercato'
                }
            },
            TABLE: {
                MAIL: 'Mail',
                CONFERENCE_TYPE: 'Tipo conferenza',
                AUTHORITY: 'Ente',
                ACTIONS: 'Azioni'
            },
            SEARCH: {
                TITLE: 'Ricerca partecipante',
                NAME_LABEL: 'Inserisci nome partecipante',
                SEARCH_BUTTON: 'Cerca'
            },
            CONFIRM_DELETE_MODAL: {
                TITLE: 'Eliminazione partecipante',
                MESSAGE:
                    'Sei sicuro di voler eliminare il partecipante? Questa azione è irreversibile',
                OK_BUTTON: 'Cancella',
                CANCEL_BUTTON: 'Annulla operazione'
            },
            EDIT: {
                BREADCRUMB: {
                    NEW: 'Nuovo partecipante',
                    EDIT: 'Modifica partecipante'
                },
                TITLE: 'Gestione partecipante',
                APPLICANT_TITLE: 'Anagrafica',
                AUTHORITY: 'Ente',
                AUTHORITY_PLACEHOLDER: 'Seleziona ente',
                APPLICANT_AUTHORITY: 'Partecipante',
                ROLE: 'Ruolo',
                AUTHORITY_DESCRIPTION: 'Desc. Ente Competente',
                APPLICANT_AUTHORITY_DESCRIPTION: 'Denominazione impresa',
                PEC: 'PEC Ente Competente',
                APPLICANT_PEC: 'PEC',
                FISCALCODE: 'C.F. Ente Competente',
                APPLICANT_CF: 'Codice fiscale',
                COMPETENCE: 'Competenza',
                DETERMINATION: 'Esprime determinazione',
                TOASTR: {
                    DELETE: {
                        SUCCESS: {
                            TEXT: 'Partecipante eliminato con successo',
                            TITLE: 'Eliminazione partecipante'
                        },
                        FAIL: {
                            TEXT:
                                'Non è stato possibile eliminare il partecipante',
                            TITLE: 'Eliminazione partecipante'
                        }
                    },
                    NEW: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT:
                                'Il partecipante è stato inserito con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Non è stato possibile inserire il partecipante nella lista di precaricamenti'
                        }
                    },
                    EDIT: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT:
                                'Il partecipante è stato aggiornato con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Non è stato possibile modificare il partecipante nella lista di precaricamenti'
                        }
                    }
                }
            }
        },
        DELEGATE: {
            TITLE: 'Delegati',
            ACTIONS: { NEW: 'Aggiungi delegato' },
            LIST: {
                TITLE: 'Delegati precaricati',
                EMPTY_MESSAGE: 'Nessuna delegato trovato',
                TOASTR: {
                    TITLE: 'Delegati',
                    EMPTY_MESSAGE: 'Nessuna delegato trovato'
                }
            },
            TOASTR: {
                NOT_FOUND: {
                    TITLE: 'Delegato non trovato',
                    TEXT: 'Non è stato possibile trovare il delegato cercato'
                }
            },
            TABLE: {
                NAME: 'Nome',
                SURNAME: 'Cognome',
                FISCAL_CODE: 'Codice fiscale',
                DOCUMENT_NAME: 'File Allegato',
                ACTIONS: 'Azioni'
            },
            SEARCH: {
                TITLE: 'Ricerca delegato',
                NAME_LABEL:
                    'Inserisci nome, cognome o codice fiscale delegato',
                COMPANY_LABEL: 'Inserisci nome impresa',
                LINKED_LABEL: 'Solo delagati non associati a impresa',
                SEARCH_BUTTON: 'Cerca'
            },
            CONFIRM_DELETE_MODAL: {
                TITLE: 'Eliminazione delegato',
                MESSAGE:
                    'Sei sicuro di voler eliminare il delegato? Questa azione è irreversibile',
                OK_BUTTON: 'Cancella',
                CANCEL_BUTTON: 'Annulla operazione'
            },
            CONFIRM_DELETE_FILE_MODAL: {
                TITLE: 'Eliminazione Documento Delega',
                MESSAGE:
                    'Sei sicuro di voler eliminare il documento di delega? Questa azione è irreversibile',
                OK_BUTTON: 'Cancella',
                CANCEL_BUTTON: 'Annulla operazione'
            },
            EDIT: {
                BREADCRUMB: {
                    NEW: 'Nuovo delegato',
                    EDIT: 'Modifica delegato'
                },
                TITLE: 'Gestione delegato',
                SEARCH: 'Cerca un delegato tramite nome o cognome',
                COMPANY: 'Impresa',
                EMAIL: 'Email',
                FISCAL_CODE: 'Codice fiscale',
                SURNAME: 'Cognome',
                NAME: 'Nome',
                DOCUMENT_NAME: 'Documento Delega ',
                TOASTR: {
                    NEW: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT: 'Il delegato è stato inserito con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Delegato già esistente'
                        }
                    },
                    EDIT: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT:
                                'Il delegato è stato aggiornato con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Non è stato possibile modificare il delegato nella lista di precaricamenti'
                        }
                    },
                    DELETE: {
                        SUCCESS: {
                            TEXT: 'Delegato eliminato con successo',
                            TITLE: 'Eliminazione delegato'
                        },
                        FAIL: {
                            TEXT:
                                'Non è stato possibile eliminare il delegato',
                            TITLE: 'Eliminazione delegato'
                        }
                    },
                    DELETE_FILE: {
                        SUCCESS: {
                            TEXT: 'Documento delegato eliminato con successo',
                            TITLE: 'Eliminazione documento delegato'
                        },
                        FAIL: {
                            TEXT:
                                'Non è stato possibile eliminare il documento delegato',
                            TITLE: 'Eliminazione documento delegato'
                        }
                    }
                }
            }
        },
        PREACCREDITATION: {
            TITLE: 'Accreditamenti per Ente',
            BREADCRUMB: { NEW: 'Nuovo Accreditamento', EDIT: 'Modifica Accreditamento' },
            TABLE: {
                NAME: 'Nome',
                SURNAME: 'Cognome',
                PEC: 'PEC',
                FISCAL_CODE: 'Codice fiscale',
                PROFILE_TYPE: 'Tipo Profilo',
                AUTHORITY: 'Ente',
                PROFILE: 'Profilo',
                ACTIONS: 'Azioni'
            },
            CONFIRM_DELETE_MODAL: {
                TITLE: 'Eliminazione preaccreditato',
                MESSAGE:
                    'Sei sicuro di voler eliminare il preaccreditato? Questa azione è irreversibile',
                OK_BUTTON: 'Cancella',
                CANCEL_BUTTON: 'Annulla operazione'
            },
            LIST: {
                TITLE: 'Accreditamenti per Ente',
                EMPTY_MESSAGE: 'Nessun Accreditamento per Ente trovato',
                TOASTR: {
                    TITLE: 'Accreditamento per Ente',
                    EMPTY_MESSAGE: 'Nessun Accreditamento per Ente trovato'
                }
            },
            SEARCH: {
                TITLE: 'Ricerca accreditato',
                NAME_LABEL:
                    'Inserisci nome, cognome o codice fiscale accreditato',
                SEARCH_BUTTON: 'Cerca'
            },
            ACTIONS: {
                TITLE: 'Espandi',
                ACCREDITATION: 'Accreditamenti per Ente'
            },
            EDIT: {
                BREADCRUMB: {
                    NEW: 'Nuovo Preaccreditato',
                    EDIT: 'Modifica Preaccreditato'
                },
                TITLE: 'Gestione Preaccreditato',
                SEARCH: 'Cerca un utente accreditato tramite nome o cognome',
                EMAIL: 'Email',
                PEC: 'Segna indirizzo come PEC',
                FISCAL_CODE: 'Codice fiscale',
                SURNAME: 'Cognome',
                NAME: 'Nome',
                AUTHORITY: 'Ente',
                AUTHORITY_PLACEHOLDER: 'Seleziona ente',
                PROFILE: 'Tipo Profilo',
                TOASTR: {
                    NEW: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT: 'Preaccreditato inserito con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Il Preaccreditato già esistente'
                        }
                    },
                    EDIT: {
                        SUCCESS: {
                            TITLE: 'Operazione conclusa con successo',
                            TEXT:
                                'Il Preaccreditato è stato aggiornato con successo'
                        },
                        FAIL: {
                            TITLE: 'Operazione non terminata',
                            TEXT:
                                'Non è stato possibile modificare il Preaccreditato'
                        }
                    },
                    DELETE: {
                        SUCCESS: {
                            TEXT: 'Preaccreditato eliminato con successo',
                            TITLE: 'Eliminazione Preaccreditato'
                        },
                        FAIL: {
                            TEXT:
                                'Non è stato possibile eliminare il Preaccreditato',
                            TITLE: 'Eliminazione Preaccreditato'
                        }
                    }
                }
            }
        },
        MAIN: {
            DESCRIPTION:
                'Seleziona la rubrica di precaricamenti che vuoi modificare',
            TEXT:
                'Seleziona una tipologia di conferenza per gestirne le rubriche di precaricamento',
            TITLE: 'Tipo conferenza',
            ACTIONS: {
                TITLE: 'Espandi',
                APPLICANT: 'Richiedenti',
                PARTICIPANT: 'Partecipanti',
                COMPANY: 'Imprese',
                DELEGATE: 'Delegati',
                PREACCREDITATION: 'Accreditamenti per Ente'
            }
        }
    },
    USERS: {
        ACTIONS: {
            TITLE: 'Azioni',
            NEW_MANAGER: 'Aggiungi utente'
        },
        TITLE: 'Gestione utenti',
        SEARCH: {
            TITLE: 'Ricerca utente',
            MANAGER_LABEL: 'Inserisci nome utente',
            AUTHORITY_LABEL: 'Inserisci nome ente',
            SEARCH_BUTTON: 'Cerca'
        },
        TABLE: {
            NAME: 'Nome',
            SURNAME: 'Cognome',
            FISCAL_CODE: 'Codice fiscale',
            AUTHORITY: 'Ente',
            ACTIONS: 'Azioni'
        },
        LIST: {
            TITLE: 'Utenti',
            EMPTY_MESSAGE: 'Nessun utente trovato',
            TOASTR: {
                TITLE: 'Utenti',
                EMPTY_MESSAGE: 'Nessuna utente trovato'
            }
        },
        EDIT: {
            BREADCRUMB: { NEW: 'Nuovo utente', EDIT: 'Modifica utente' },
            TITLE: 'Utente',
            NAME: 'Nome',
            SURNAME: 'Cognome',
            PEC: 'Pec',
            FISCAL_CODE: 'Codice fiscale',
            AUTHORITY: 'Ente',
            MANAGERS: 'Lista dei creatori di conferenze di cui è consentito modificarne le pratiche',
            PROFILE: 'Profilo',
            FLAG_SIGNATORY: 'Firmatario di documenti mediante funzionalità "Alla Firma"', 
            TOASTR: {
                NEW: {
                    SUCCESS: {
                        TITLE: 'Operazione conclusa con successo',
                        TEXT: "L'utente è stato inserito con successo"
                    },
                    FAIL: {
                        TITLE: 'Operazione non terminata',
                        TEXT:
                            "Non è stato possibile inserire l'utente. Esiste già un utente con lo stesso Codice Fiscale"
                    }
                },
                EDIT: {
                    SUCCESS: {
                        TITLE: 'Operazione conclusa con successo',
                        TEXT: "L'utente è stato aggiornato con successo"
                    },
                    FAIL: {
                        TITLE: 'Operazione non terminata',
                        TEXT:
                            "Non è stato possibile modificare l'utente nella lista di precaricamenti. Non può essere modificato questo campo per l'utente"
                    }
                }
            }
        },
        TOASTR: {
            NOT_FOUND: {
                TITLE: 'Utente non trovato',
                TEXT: "Non è stato possibile trovare l'utente cercato"
            },
            DELETE: {
                SUCCESS: {
                    TEXT: 'Utente eliminato con successo',
                    TITLE: 'Eliminazione utente'
                },
                FAIL: {
                    TEXT: "Non è possibile eliminare l'utente",
                    TITLE: 'Eliminazione utente'
                }
            }
        },
        CONFIRM_DELETE_MODAL: {
            TITLE: 'Eliminazione utente',
            MESSAGE:
                "Sei sicuro di voler eliminare l'utente? Questa azione è irreversibile",
            OK_BUTTON: 'Cancella',
            CANCEL_BUTTON: 'Annulla operazione'
        }
    },
    CHATBOT: {
        ACTIONS: {
            TITLE: 'Azioni',
            NEW_MANAGER: 'Aggiungi Domande'
        },
        TITLE: ' Gestione domande risposte chatbot',
        SEARCH: {
            TITLE: 'Ricerca Domanda',
            MANAGER_LABEL: 'Inserisci Domande',
            AUTHORITY_LABEL: 'Inserisci Risposta',
            SEARCH_BUTTON: 'Cerca'
        },
        TABLE: {
            DOMANDA: 'Domanda',
            RISPOSTA: 'Risposta',
            NAME: 'Nome',
            SURNAME: 'Cognome',
            FISCAL_CODE: 'Codice fiscale',
            AUTHORITY: 'Ente',
            ACTIONS: 'Azioni'
        },
        LIST: {
            TITLE: 'Domande',
            EMPTY_MESSAGE: 'Nessun Domanda trovata',
            TOASTR: {
                TITLE: 'Domande',
                EMPTY_MESSAGE: 'Nessuna Domanda trovata'
            }
        },
        EDIT: {
            BREADCRUMB: { NEW: 'Nuova Domande', EDIT: 'Modifica Domanda' },
            TITLE: 'Domande',
            DOMANDA: 'Domanda',
            RISPOSTA: 'Risposta',
            NAME: 'Nome',
            SURNAME: 'Cognome',
            PEC: 'Pec',
            FISCAL_CODE: 'Codice fiscale',
            AUTHORITY: 'Ente',
            PROFILE: 'Profilo',
            TOASTR: {
                NEW: {
                    SUCCESS: {
                        TITLE: 'Operazione conclusa con successo',
                        TEXT: "L'utente è stato inserito con successo"
                    },
                    FAIL: {
                        TITLE: 'Operazione non terminata',
                        TEXT:
                            "Non è stato possibile inserire l'utente. Esiste già un utente con lo stesso Codice Fiscale"
                    }
                },
                EDIT: {
                    SUCCESS: {
                        TITLE: 'Operazione conclusa con successo',
                        TEXT: "L'utente è stato aggiornato con successo"
                    },
                    FAIL: {
                        TITLE: 'Operazione non terminata',
                        TEXT:
                            "Non è stato possibile modificare l'utente nella lista di precaricamenti. Non può essere modificato questo campo per l'utente"
                    }
                }
            }
        },
        TOASTR: {
            NOT_FOUND: {
                TITLE: 'Domande non trovato',
                TEXT: "Non è stato possibile trovare l'utente cercato"
            },
            DELETE: {
                SUCCESS: {
                    TEXT: 'Domande eliminato con successo',
                    TITLE: 'Eliminazione Domande'
                },
                FAIL: {
                    TEXT: "Non è possibile eliminare la Domande",
                    TITLE: 'Eliminazione Domande'
                }
            }
        },
        CONFIRM_DELETE_MODAL: {
            TITLE: 'Eliminazione Domande',
            MESSAGE:
                "Sei sicuro di voler eliminare l'Domande? Questa azione è irreversibile",
            OK_BUTTON: 'Cancella',
            CANCEL_BUTTON: 'Annulla operazione'
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
                Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
        },
        FILE_DOWNLOAD_ERROR: {
            TITLE: 'Impossibile scaricare il file',
            TEXT: `Non è possibile scaricare il file.
                Se il problema persiste chiedi supporto attraverso la pagina dei contatti.`
        }
    },
    FORM: { SAVE: 'Salva' },
    ERROR: {
        REGEX: 'Formato non valido',
        EMAIL: {
            INVALID: 'Indirizzo e-mail non valido'
        },
        INVALID: '{{value}} non valido',
        CF: {
            INVALID: 'Codice fiscale non valido'
        },
        MODAL: {
            CONFIRM_LOGIN_REDIRECT: {
                TITLE: 'Sessione scaduta',
                MESSAGE: `Per poter continuare ad utilizzare il sistema è necessario eseguire nuovamente la login.
                Vuoi eseguire la login?`,
                OK_BUTTON: 'Si',
                CANCEL_BUTTON: 'No'
            }
        },
        REQUIRED: 'Campo obbligatorio'
    },
    CONFERENCE: {
        TITLE: 'Gestione conferenze',
        SEND_MAIL: {
            BREADCRUMB: 'Email di indizione',
            TITLE: 'Inoltra mail di indizione',
            PARTICIPANT: 'Partecipante',
            MAIL: 'Nuovo indirizzo mail',
            BUTTON: 'Invia mail'
        },
        CHANGE_STATE: {
            BREADCRUMB: 'Stato',
            TITLE: 'Cambia stato conferenza',
            STATE: 'Stato',
            BUTTON: 'Cambia stato',
            TOASTR: {
                NEW: {
                    SUCCESS: {
                        TITLE: 'Operazione conclusa con successo',
                        TEXT:
                            'Cambio stato avvenuto con successo'
                    },
                    FAIL: {
                        TITLE: 'Operazione non terminata',
                        TEXT:
                            'Non è stato possibile cambiare lo stato'
                    }
                }
            }
        },
        UPLOAD: {
            BREADCRUMB: 'Caricamento file',
            MODAL: {
                TITLE: 'Caricamento file',
                TEXT: "Sicuro di volere eseguire l'upload del file?",
                OK_BUTTON: 'Carica',
                CANCEL_BUTTON: 'Annulla'
            },
            FILE: {
                CATEGORY: 'Categoria',
                TYPE: 'Tipo',
                VISIBILITY: 'Visibilità',
                MEETING_DATE: 'Data riunione'
            },
            TOASTR: {
                ERROR_FILE_UPLOAD: {
                    TEXT: 'Caricamento non riuscito',
                    TITLE: 'Errore caricamento'
                },
                FINISH_FILE_UPLOAD: {
                    TEXT: 'Il file è stato caricato con successo',
                    TITLE: 'Caricamento riuscito'
                },
                NEW_FILE_UPLOAD: {
                    TEXT:
                        'Un nuovo file è stato messo in lista per essere caricato',
                    TITLE: 'Caricamento in corso'
                }
            }
        },
        LIST: {
            TITLE: 'Conferenze'
        },
        TOASTR: {
            SEND_MAIL: {
                SUCCESS: {
                    TEXT:
                        'Email di indizione inviata con successo al nuovo indirizzo',
                    TITLE: 'Invio mail di indizione'
                },
                FAIL: {
                    TEXT: 'Non è stato possibile inviare la mail di indizione',
                    TITLE: 'Operazione non conclusa'
                }
            },
            CHANGE_STATE: {
                SUCCESS: {
                    TEXT:
                        'Cambio stato conferenza avvenuto con successo',
                    TITLE: 'Cambio stato conferenza'
                },
                FAIL: {
                    TEXT: 'Non è stato possibile cambiare lo stato della conferenza',
                    TITLE: 'Operazione non conclusa'
                },
            },
            NOT_FOUND: {
                TEXT: 'Non è stato possibile trovare la conferenza cercata',
                TITLE: 'Nessuna conferenza trovata'
            }
        },
        AUTOCOMPLETE: {
            ITEMS_NOT_FOUND: 'Nessun elemento',
            INSERT_MIN_CHARACTERS: 'Scrivere per ricercare'
        }
    },
    HOME: {
        AUTHORITIES: { TITLE: 'Gestione enti' },
        ADMINISTRATION: { TITLE: 'Gestione amministrazioni procedenti' },
        USERS: { TITLE: 'Gestione utenti' },
        PROTOCOLS: { TITLE: 'Gestione protocolli' },
        PRELOADING: { TITLE: 'Gestione rubriche precaricamenti' },
        CHATBOT: { TITLE: 'Gestione domande risposte chatbot' },
        CONFERENCE: { TITLE: 'Gestione conferenze' }
    },

    DROPZONE: {
        LABEL: 'Trascina qui i file o clicca sulla zona',
        BROWSE: {
            BTN_LABEL: 'Scegli file'
        }

    }
};
