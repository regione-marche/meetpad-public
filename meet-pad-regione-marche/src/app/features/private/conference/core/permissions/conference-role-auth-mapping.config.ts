import {
    ConferenceRole,
    PermissionType,
    AppSections,
    ConfRolePerm
} from '@common';

import {
    conferencePecCommon,
    conferenceAccreditationCommon,
    conferenceIndictionCommon,
    conferenceSignCommon,
    conferenceWizardManagerAndCreator,
    conferenceWizardCommonParticipant,
    conferenceDetailCommon,
    conferenceStepCommon,
    conferenceStepApplicant,
    conferenceStepCreator,
    conferenceAlertInfoApplicant,
    conferenceAlertInfoCreatorAndManager
} from '@app/core/permissions/sections-function';

/**
 * mappa dei ruoli per sezione della conference
 * es:
 * - wizard visibile in base ai ruoli sulla conferenza
 * - azione dì creazione degli eventi visibile in base
 *   ai ruoli sulla conferenza
 */
export const conferenceRoleAuthMappingConfig: Map<
    AppSections,
    ConfRolePerm
> = new Map()
    .set(
        AppSections.CONFERENCE_DETAIL_ROUTE,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: conferenceDetailCommon
            })
            .set(ConferenceRole.APPLICANT, {
                apply: conferenceDetailCommon
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: conferenceDetailCommon
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: conferenceDetailCommon
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: conferenceDetailCommon
            })
    )
    .set(
        AppSections.CONFERENCE_INDICTION_FILE,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: () => true
            })
            .set(ConferenceRole.APPLICANT, {
                apply: conferenceIndictionCommon
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: conferenceIndictionCommon
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: conferenceIndictionCommon
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: conferenceIndictionCommon
            })
    )

    .set(
        AppSections.CONFERENCE_SIGN_FILE,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp, true, true)
                }
            })
            .set(ConferenceRole.APPLICANT, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: (cmp) => {
                    conferenceSignCommon(cmp)
                }
            })
    )
    .set(
        AppSections.CONFERENCE_ACCREDITATION,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: () => true
            })
            .set(ConferenceRole.APPLICANT, {
                apply: conferenceAccreditationCommon
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: conferenceAccreditationCommon
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: conferenceAccreditationCommon
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: conferenceAccreditationCommon
            })
    )
    .set(
        AppSections.CONFERENCE_PEC,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: () => true
            })
            .set(ConferenceRole.APPLICANT, {
                apply: conferencePecCommon
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: conferencePecCommon
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: conferencePecCommon
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: conferencePecCommon
            })
    )
    .set(
        AppSections.CONFERENCE_WIZARD,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: conferenceWizardManagerAndCreator
            })
            .set(ConferenceRole.APPLICANT, {
                apply: conferenceWizardCommonParticipant
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: conferenceWizardCommonParticipant
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: conferenceWizardCommonParticipant
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: conferenceWizardCommonParticipant
            })
    )
    .set(
        AppSections.CONFERENCE_STEP,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: conferenceStepCreator
            })
            .set(ConferenceRole.APPLICANT, {
                apply: conferenceStepApplicant
            })
            .set(ConferenceRole.PROCEDURE_MANAGER, {
                apply: conferenceStepCommon
            })
            .set(ConferenceRole.ONLY_ONE_MANAGER, {
                apply: conferenceStepCommon
            })
            .set(ConferenceRole.ACCREDITED_PERSON, {
                apply: conferenceStepCommon
            })
    )
    .set(
        AppSections.CONFERENCE_ALERT_INFO,
        new Map<ConferenceRole, PermissionType>()
            .set(ConferenceRole.APPLICANT, {
                apply: conferenceAlertInfoApplicant
            })
            .set(ConferenceRole.CONFERENCE_MANAGER, {
                apply: conferenceAlertInfoCreatorAndManager
            })
    );
