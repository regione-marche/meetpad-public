import { Component, Output, EventEmitter, OnInit } from '@angular/core';

import { Observable } from 'rxjs';

import { I18nService } from '@eng-ds/ng-core';
import { TooltipModel } from '@eng-ds/ng-toolkit';

import {
    LoaderService,
    SectionLoading,
    AutoUnsubscribe,
    FormButton,
    FooterButtons
} from '@common';

import { ActionForm, StepName, Mixin } from '@app/core';

import { FormStep } from '../../../core/mixins';
import {
    ConferenceStoreService,
    Conference,
    ConferencePermissionsService
} from '../../../core';

@Component({
    selector: 'app-conference-summary',
    templateUrl: './summary.component.html',
    styleUrls: ['./summary.component.scss']
})
@Mixin([FormStep])
export class SummaryComponent extends AutoUnsubscribe implements OnInit {
    procedureTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PROCEDURE.TITLE'
    );
    definitionTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DEFINITION.TITLE'
    );
    documentationTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.DOCUMENTATION.TITLE'
    );
    participantTitle: string = this.i18nService.translate(
        'CONFERENCE.WIZARD.PARTICIPANTS.TITLE'
    );
    tooltip: TooltipModel;
    footerButtons = false;

    groupButtons: Map<FooterButtons, FormButton> = new Map();

    @Output('openConfirmModal') openConfirmModal = new EventEmitter<
        FooterButtons
    >();

    constructor(
        private i18nService: I18nService,
        private conferenceStoreService: ConferenceStoreService,
        private loaderService: LoaderService,
        private conferencePermissionsService: ConferencePermissionsService
    ) {
        super();
        this.tooltip = new TooltipModel(
            'CONFERENCE.WIZARD.SUMMARY.TOOLTIP.CONTENT',
            null,
            'CONFERENCE.WIZARD.SUMMARY.TOOLTIP.TITLE'
        );
    }
    ngOnInit() {
        this._initPermission();
        this.conferenceStoreService.hidePageLoader();
    }

    private _initPermission(): void {
        this.conferencePermissionsService.apply(
            this,
            this.appSections.CONFERENCE_STEP,
            this.conferenceStoreService
        );
    }

    isReadonly(): boolean {
        return false;
    }

    // TODO REMOVE
    get loadingFooterButtons(): Observable<boolean> {
        return this.loaderService.getLoading$(
            SectionLoading.CONFERENCE_FOOTER_BUTTON
        );
    }

    public setFooterButtons(buttons: Map<FooterButtons, FormButton>): void {
        this.groupButtons = new Map<FooterButtons, FormButton>();
        if (buttons != null) {
            buttons.forEach((value, key) => {
                value.onClick = this[key].bind(this);
            });
        }
        setTimeout(() => {
            this.groupButtons = buttons;
        }, 0);
    }

    private _complete(): void {
        this.conferenceStoreService.openConfirmationModal(
            FooterButtons.COMPLETE
        );
    }

    private _authorize(): void {
        this.conferenceStoreService.openConfirmationModal(
            FooterButtons.AUTHORIZE
        );
    }

    private _unauthorize(): void {
        this.conferenceStoreService.openConfirmationModal(
            FooterButtons.UNAUTHORIZE
        );
    }

    get conference(): Conference {
        return this.conferenceStoreService.conference;
    }

    get action(): ActionForm {
        return this.conferenceStoreService.getStepActionForm(StepName.SUMMARY);
    }

    save(): void {
        return this.conferenceStoreService.submitSummary();
    }

    showSubmitBtn(): boolean {
        return (
            this.action !== ActionForm.READONLY && this.conference.isCompiling()
        );
    }
}
