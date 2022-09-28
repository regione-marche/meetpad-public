import { takeUntil } from 'rxjs/operators';

import {
    ConferenceStoreService,
    ConferencePermissions
} from '@app/features/private/conference/core';
import { AlertInfoComponent } from '@app/features/private/conference/shared';

export function conferenceAlertInfoCreatorAndManager(
    cmp: AlertInfoComponent,
    conferenceStoreService: ConferenceStoreService
): void {
    const fn = () => {
        if (
            conferenceStoreService.conferencePermissions.conferenceEditable &&
            conferenceStoreService.conference.isCompiling()
        ) {
            if (conferenceStoreService.conference.enableApplicantEdit) {
                cmp.show = true;
                cmp.title = 'CONFERENCE.WIZARD.ALERT.CREATOR.AUTHORIZED.TITLE';
                cmp.text = 'CONFERENCE.WIZARD.ALERT.CREATOR.AUTHORIZED.TEXT';
            }
            if (!conferenceStoreService.conference.enableApplicantEdit) {
                cmp.show = true;
                cmp.title =
                    'CONFERENCE.WIZARD.ALERT.CREATOR.UNAUTHORIZED.TITLE';
                cmp.text = 'CONFERENCE.WIZARD.ALERT.CREATOR.UNAUTHORIZED.TEXT';
            }
        }
    };

    if (typeof conferenceStoreService.conference.id !== 'undefined') {
        conferenceStoreService.userConferencePermissions$
            .pipe(takeUntil(cmp.destroy$))
            .subscribe((_conferencePermissions: ConferencePermissions) => {
                if (conferenceStoreService.conference.isCompiling()) {
                    fn();
                } else {
                    cmp.show = false;
                }
            });
    }
    if (conferenceStoreService.conferencePermissions) {
        if (conferenceStoreService.conference.isCompiling()) {
            fn();
        } else {
            cmp.show = false;
        }
    }
}

export function conferenceAlertInfoApplicant(
    cmp: AlertInfoComponent,
    conferenceStoreService: ConferenceStoreService
): void {
    const fn = () => {
        if (conferenceStoreService.conferencePermissions.enabled) {
            cmp.show = true;
            cmp.title = 'CONFERENCE.WIZARD.ALERT.APPLICANT.TITLE';
            cmp.text = 'CONFERENCE.WIZARD.ALERT.APPLICANT.TEXT';
        } else {
            cmp.show = false;
        }
    };

    if (typeof conferenceStoreService.conference.id !== 'undefined') {
        conferenceStoreService.userConferencePermissions$
            .pipe(takeUntil(cmp.destroy$))
            .subscribe((_conferencePermissions: ConferencePermissions) => {
                if (conferenceStoreService.conference.isCompiling()) {
                    fn();
                }
            });
    }
    if (conferenceStoreService.conferencePermissions) {
        if (conferenceStoreService.conference.isCompiling()) {
            fn();
        }
    }
}
