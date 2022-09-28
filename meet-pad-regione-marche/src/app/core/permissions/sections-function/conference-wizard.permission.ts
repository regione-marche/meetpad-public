import { WizardComponent, Step } from '@eng-ds/ng-toolkit';
import { StepName } from '@app/core/enums';
import { ConferenceStoreService } from '@app/features/private/conference/core';

export function conferenceWizardCommon(
    cmp: WizardComponent,
    conferenceStoreService: ConferenceStoreService
): void {
    if (
        conferenceStoreService.conference.isDraft() ||
        conferenceStoreService.conference.isCompiling()
    ) {
        cmp.steps = cmp.steps.filter(
            (s: Step) =>
                s.id !== StepName.ACCREDITATION &&
                s.id !== StepName.EVENT &&
                s.id !== StepName.PEC
        );
    }

    if (
        conferenceStoreService.conference.isIndictionState() ||
        conferenceStoreService.conference.isClosed() ||
        conferenceStoreService.conference.isArchivied()
    ) {
        cmp.steps = cmp.steps.filter((s: Step) => s.id !== StepName.SUMMARY);
    }
    cmp.loading = false;
}

export function conferenceWizardManagerAndCreator(
    cmp: WizardComponent,
    conferenceStoreService: ConferenceStoreService
): void {
    if (
        conferenceStoreService.conference.isDraft() ||
        conferenceStoreService.conference.isCompiling()
    ) {
        cmp.steps = cmp.steps.filter(
            (s: Step) =>
                s.id !== StepName.ACCREDITATION &&
                s.id !== StepName.EVENT &&
                s.id !== StepName.PEC &&
                s.id !== StepName.VOTINGS &&
                s.id !== StepName.PROTOCOL
        );
    }

    if (
        conferenceStoreService.conference.isIndictionState() ||
        conferenceStoreService.conference.isClosed() ||
        conferenceStoreService.conference.isArchivied()
    ) {
        if (conferenceStoreService.conference.isOperationalMeeting()) {
            cmp.steps = cmp.steps.filter((s: Step) => 
                s.id !== StepName.SUMMARY
                && s.id !== StepName.ACCREDITATION
            );
        }
        else {
            cmp.steps = cmp.steps.filter((s: Step) => 
                s.id !== StepName.SUMMARY
            );
        }
    }
    cmp.loading = false;
}

export function conferenceWizardCommonParticipant(
    cmp: WizardComponent,
    conferenceStoreService: ConferenceStoreService
): void {
    let filterFn = (s: Step) =>
        s.id !== StepName.ACCREDITATION &&
        s.id !== StepName.EVENT &&
        s.id !== StepName.PEC &&
        s.id !== StepName.VOTINGS &&
        s.id !== StepName.PROTOCOL;
    if (
        !conferenceStoreService.conference.isDraft() &&
        !conferenceStoreService.conference.isCompiling()
    ) {
        filterFn = (s: Step) =>
            s.id !== StepName.ACCREDITATION &&
            s.id !== StepName.PEC &&
            s.id !== StepName.SUMMARY &&
            s.id !== StepName.PROTOCOL;
    }

    cmp.steps = cmp.steps.filter(filterFn);
    cmp.loading = false;
}
