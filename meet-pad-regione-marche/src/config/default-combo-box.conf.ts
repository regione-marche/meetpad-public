import { VotingType } from "projects/common/src/lib/enums/voting-type.enum";
import { VotingRule } from "projects/common/src/lib/enums/voting-rule.enum";
import { VotingState } from "@app/core/enums/voting-state.enum";
import { VotingResult } from "@app/core/enums/voting-result.enum";

export const defaultComboBox = {
    area: { key: '11', value: 'Marche' }, // regione marche
    province: { key: '042', value: 'Ancona' }, // ancona
    city: { key: '042002', value: 'Ancona' }, // ancona
    advancedSearch: {
        conferenceType: { key: 'all', value: 'Tutti' }, // tutti
        state: { key: 'all', value: 'Tutti' } // tutti
    },
    conference: {
        manager: { key: '1', value: 'MARIALAURA MAGGIULLI' },
        administration: { key: '1', value: 'Regione Marche' },
        conferenceType: { key: '1' },
        procedure: {
            activity: {
                key: '12',
                value:
                    'Arti tipografiche, litografiche, fotografiche e di stampa'
            }, //
            //type: { key: '1', value: 'Attività Commerciali ed Assimibilabili' }, //
            type: { key: '2', value: 'Ambienete' }, //
            preliminaryType: { key: '4' },
            action: { key: '27', value: 'Apertura' }, //
            legalForm: { key: '2' } // TODO aggiungere value
        },
        participants: {
            // authority: { key: '1', value: 'Regione Marche' }, // Regione marche
            role: { key: '3', value: 'Amministrazione competente' }, // Amministrazione procedente
            competence: [{ key: '-1', value: '' }],
            users: {
                // il primo profilo relativo al ruolo del partecipante con id 3
                profile: { key: '3', value: 'Responsabile del procedimento' }
            }
        },
        defaultParticipant: {
            administrationProceding: {
                userRole: { key: '2', value: 'RESPONSABILE CONFERENZA' }, // Responsabile conferenza
                participantAuthority: { key: '1', value: 'Regione Marche' }, // Regione marche
                participantRole: {
                    key: '2',
                    value: 'Amministrazione procedente'
                } // Amministrazione procedente
            },
            applicant: {
                userRole: { key: '1', value: 'Richiedente' }, // Richiedente
                participantAuthority: { key: '0', value: 'Richiedente' }, // Ente fittizio -> Richiedente
                participantRole: { key: '1', value: 'Richiedente' } // Richiedente
            }
        },
        definition: {
            instance: {
                address: {
                    type: { key: '1', value: 'Riunione online' } // Riunione online
                }
            }
        },
        accreditation: {
            role: { key: '3', value: 'Responsabile del procedimento' }
        },
        events: {
            conferenceCreated: {
                type: { key: '1', value: 'Creazione conferenza' }
            },
            conferenceIndiction: {
                type: { key: '2', value: 'Convocazione conferenza' },
                document: {
                    category: { key: '4' } // nota di invio
                }
            },
            integrationRequest: {
                type: { key: '3', value: 'Richiesta integrazioni' },
                subject: { key: '1', value: 'Richiesta integrazioni' },
                document: {
                    category: { key: '8' } // richiesta integrazioni
                }
            },
            integrationClosed: {
                type: { key: '4', value: 'Chiusura integrazioni' },
                subject: { key: '1', value: 'Richiesta integrazioni' }
            },
            integrationOnlyOneRequest: {
                type: { key: '5', value: 'Richiesta unica' },
                subject: { key: '1', value: 'Richiesta integrazioni' },
                document: {
                    category: { key: '10' } // richiesta unica
                }
            },
            integrationSend: {
                type: { key: '6', value: 'Invia integrazione' },
                subject: { key: '1', value: 'Richiesta integrazioni' },
                document: {
                    category: { key: '11' } // documentazione integrativa
                }
            },
            conferenceMemo: {
                type: { key: '9', value: 'Caricamento verbale riunione' },
                subject: { key: '5', value: 'Verbale conferenza' },
                document: {
                    category: { key: '12' } // verbale conferenza
                }
            },
            conferenceMemoInternal: {
                type: { key: '31', value: 'Caricamento verbale riunione interno' },
                subject: { key: '5', value: 'Verbale conferenza interno' },
                document: {
                    category: { key: '28' } // verbale conferenza
                }
            },
            conferenceClosing: {
                type: { key: '10', value: 'Chiusura conferenza' },
                subject: { key: '6', value: 'Chiusura conferenza' },
                result: { key: '1' } // chiusura positiva
            },
            opinionExpress: {
                type: { key: '11', value: 'Espressione parere' },
                subject: { key: '7', value: 'Espressione Pareri' },
                determinationType: { key: '1', value: 'Positivo' },
                document: {
                    category: { key: '9' } // parere
                }
            },
            finalExpress: {
                type: { key: '12', value: 'Determinazione finale' },
                subject: { key: '8', value: 'Determinazione finale' },
                document: {
                    category: { key: '13' } // determinazione finale
                }
            },
            genericCommunication: {
                type: { key: '7', value: 'Comunicazione generica' },
                subject: { key: '2', value: 'Comunicazione generica' },
                document: {
                    category: { key: '5' }
                }
            },
            integrationRegistered: {
                type: {
                    key: '13',
                    value: 'Trasmissione Integrazioni protocollate'
                },
                subject: {
                    key: '9',
                    value: 'Trasmissione integrazioni protocollate'
                }, // TODO verificare BE
                document: {
                    category: { key: '5' } // altro
                }
            },
            editDate: {
                type: { key: '26', value: 'Modifica Data' },
                subject: { key: '16', value: 'Modifica Data' },
                dateType: { key: 'DATA_TERMINE', value: 'Data termina' },
                document: {
                    category: { key: '2' }
                }
            },
        },
        pec: {
            search: {
                status: { key: '' },
                event: { key: '' }
            }
        },
        voting: {
            votingType: { key: VotingType.VOTAZIONE },
            votingRule: { key: VotingRule.EVENTO },
            votingState: { key: VotingState.PREPARAZIONE },
            votingResult: { key: VotingResult.APPROVATA}
        },
        documentation: {
            interactionCategory: { key: '2' },
            additionalCategory: { key: '1' },
            sharedDashboardCategory: { key: '18' },
            preliminaryCategory: { key: '1' } // TODO da verificare
        }
    }
};
