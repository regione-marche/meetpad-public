import {
    ApplicationRole,
    AppSections,
    AppRolePerm,
    PermissionType
} from '@common';

import {
    conferencePecCommon,
    conferenceAccreditationCommon,
    conferenceIndictionCommon,
    conferenceSignCommon,
    conferenceDetailCommon,
    conferenceWizardManagerAndCreator,
    conferenceWizardCommon,
    conferenceStepCreator,
    conferenceStepCommon,
    conferenceAlertInfoCreatorAndManager,
    sideMenuCreatorAndManager,
    sideMenuCommon,
    conferenceProtocolCommon,
    conferenceProtocolCreatorAndManager
} from './sections-function';

/**
 * mappa dei ruoli per sezione dell'app
 * es:
 *
 * - pannello per caricare il file d'indizione
 *      - il ruolo RESPONSABILE DI CONFERENZA può visualizzarlo e di conseguenza caricare il file
 *      - il ruolo CREATORE DI CONFERENZA può visualizzarlo ma non caricarlo
 * - la tablella dei risultati di ricerca sulla pagina della Scrivania
 *      - il ruolo RESPONSABILE DI CONFERENZA può visualizzarla
 *      - il ruolo CREATORE DI CONFERENZA può visualizzarla
 *
 * il tipo di permesso è in lettura e/o in scrittura:
 *
 * read: true => permesso di visualizzare l'elemento
 * read: false => la visualizzazione dell'elemento non è peremessa
 * read: undefined => si delega lìapplicazionde dei permessi alla cb apply: () => void
 * apply: è la callback per applicare controlli specifici su ogni sezione
 * per esempio il permesso di scrittura può voler dire molte cose
 * (es: click su un pulsante o su un input etc..)
 * per questo non è possibile generalizzare il permesso di scrittura
 * quindi con apply si effetua il controllo specifico sul componente
 * o sull'elemento html in base a dove è applicata la direttiva
 * apply può ritornare un boolean, un Observable<boolean> o void
 */
export const appRoleAuthMappingConfig: Map<AppSections, AppRolePerm> = new Map()
    .set(
        AppSections.CONFERENCE_DETAIL_ROUTE,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: conferenceDetailCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferenceDetailCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferenceDetailCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferenceDetailCommon
            })
    )
    .set(
        AppSections.RESULT_TABLE,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_MANAGER, {
                apply: () => true
            })
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: () => true
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: () => true
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: () => true
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: () => true
            })
            .set(ApplicationRole.PARTICIPANT, {
                apply: () => true
            })
    )
    .set(
        AppSections.CONFERENCE_INDICTION_FILE,
        new Map<ApplicationRole, PermissionType>()

            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: () => true
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferenceIndictionCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferenceIndictionCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferenceIndictionCommon
            })
    )

    .set(
        AppSections.CONFERENCE_SIGN_FILE,
        new Map<ApplicationRole, PermissionType>()

            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp, true, true)
                }
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })

    )
    .set(
        AppSections.CONFERENCE_ACCREDITATION,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: () => true
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferenceAccreditationCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferenceAccreditationCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferenceAccreditationCommon
            })
    )
    .set(
        AppSections.CONFERENCE_PEC,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: conferencePecCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferencePecCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferencePecCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferencePecCommon
            })
    )
    .set(
        AppSections.CONFERENCE_WIZARD,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_MANAGER, {
                apply: conferenceWizardCommon
            })
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: conferenceWizardManagerAndCreator
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferenceWizardCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferenceWizardCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferenceWizardCommon
            })
    )
    .set(
        AppSections.CONFERENCE_STEP,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_MANAGER, {
                apply: conferenceStepCommon
            })
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: conferenceStepCreator
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferenceStepCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferenceStepCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferenceStepCommon
            })
    )
    .set(
        AppSections.CONFERENCE_ALERT_INFO,
        new Map<ApplicationRole, PermissionType>().set(
            ApplicationRole.CONFERENCE_CREATOR,
            {
                apply: conferenceAlertInfoCreatorAndManager
            }
        )
    )
    .set(
        AppSections.SIDE_MENU,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_MANAGER, {
                apply: sideMenuCreatorAndManager
            })
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: sideMenuCreatorAndManager
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: sideMenuCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: sideMenuCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: sideMenuCommon
            })
            .set(ApplicationRole.PARTICIPANT, {
                apply: sideMenuCommon
            })
    )
    .set(
        AppSections.CONFERENCE_PROTOCOL,
        new Map<ApplicationRole, PermissionType>()
            .set(ApplicationRole.CONFERENCE_MANAGER, {
                apply: conferenceProtocolCreatorAndManager
            })
            .set(ApplicationRole.CONFERENCE_CREATOR, {
                apply: conferenceProtocolCreatorAndManager
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONE_PROCEDENTE, {
                apply: conferenceProtocolCommon
            })
            .set(ApplicationRole.ADMIN_AMMINISTRAZIONI, {
                apply: conferenceProtocolCommon
            })
            .set(ApplicationRole.ADMINISTRATOR, {
                apply: conferenceProtocolCreatorAndManager
            })
            .set(ApplicationRole.PARTICIPANT, {
                apply: conferenceProtocolCommon
            })
    );
