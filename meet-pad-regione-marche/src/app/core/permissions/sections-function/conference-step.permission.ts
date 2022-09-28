import { takeUntil } from 'rxjs/operators';

import { StepName } from '@app/core/enums';
import { FormButton, FooterButtons } from '@common';
import {
    ConferenceStoreService,
    ConferencePermissions
} from '@app/features/private/conference/core';
import { FormStep } from '@app/features/private/conference/core/mixins';

export function conferenceStepCreator(
    cmp: FormStep,
    conferenceStoreService: ConferenceStoreService
): void {
    const buttons: Map<FooterButtons, FormButton> = new Map();

    const fn = (conferencePermission: ConferencePermissions) => {
        buttons.clear();
        if (
            conferenceStoreService.conference.isCompiling() &&
            conferencePermission.conferenceEditable
        ) {
            if (conferenceStoreService.conference.enableApplicantEdit) {
                buttons.set(FooterButtons.UNAUTHORIZE, {
                    color: 'blue',
                    title: 'BUTTON.UNAUTHORIZE'
                });
            } else {
                buttons.set(FooterButtons.AUTHORIZE, {
                    color: 'blue',
                    title: 'BUTTON.AUTHORIZE'
                });
            }
        }
        cmp.setFooterButtons(buttons);
    };

    if (typeof conferenceStoreService.conference.id !== 'undefined') {
        conferenceStoreService.userConferencePermissions$
            .pipe(takeUntil(cmp.destroy$))
            .subscribe((_conferencePermissions: ConferencePermissions) => {
                fn(_conferencePermissions);
            });
    }
    if (conferenceStoreService.conferencePermissions) {
        fn(conferenceStoreService.conferencePermissions);
    }
}

export function conferenceStepCommon(
    cmp: FormStep,
    conferenceStoreService: ConferenceStoreService
): void {
    if (cmp.isReadonly()) {
        return;
    }
}

export function conferenceStepApplicant(
    cmp: FormStep,
    conferenceStoreService: ConferenceStoreService
): void {
    if (cmp.isReadonly()) {
        return;
    }
    const fn = (conferencePermission: ConferencePermissions) => {
        if (conferencePermission.enabled) {
            const buttons: Map<FooterButtons, FormButton> = new Map();
            const activeStep = conferenceStoreService.activeStep;
            if (
                (conferencePermission.stepList.find(
                    step => step === activeStep.id
                ) ||
                    activeStep.id === StepName.SUMMARY) &&
                conferencePermission.enabled
            ) {
                buttons.set(FooterButtons.COMPLETE, {
                    color: 'blue',
                    title: 'BUTTON.COMPLETE'
                });
            }
            cmp.setFooterButtons(buttons);
        } else {
            cmp.setStepAsReadOnly();
            cmp.setFooterButtons(null);
        }
    };

    if (typeof conferenceStoreService.conference.id !== 'undefined') {
        conferenceStoreService.userConferencePermissions$
            .pipe(takeUntil(cmp.destroy$))
            .subscribe((_conferencePermissions: ConferencePermissions) => {
                fn(_conferencePermissions);
            });
    } else {
        return;
    }
    if (conferenceStoreService.conferencePermissions) {
        fn(conferenceStoreService.conferencePermissions);
    }
}
