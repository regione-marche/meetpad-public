import { Component, ViewChild, AfterViewInit, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { takeUntil } from 'rxjs/operators';

import { ModalComponent, ActionItem } from '@eng-ds/ng-toolkit';
import { AutoUnsubscribe } from '@common';
import { StepName } from '@app/core/enums';
import { FooterButtons } from '@common';
import { ConferenceModalBeforeCreateComponent } from '../shared';
import { ConferenceStoreService, ConferenceStep, Conference } from '../core';

@Component({
    templateUrl: './conference.component.html',
    styleUrls: ['./conference.component.scss']
})
export class ConferenceComponent extends AutoUnsubscribe
    implements AfterViewInit, OnInit {
    @ViewChild('modalBeforeCreate')
    modalBeforeCreate: ConferenceModalBeforeCreateComponent;

    @ViewChild('confirmModal') confirmModal: ModalComponent;

    modalTitle: string = '';
    modalText: string = '';

    modalButtons: ActionItem[] = [
        new ActionItem(
            'CONFERENCE.AUTHORIZATION_MODAL.CANCEL_BUTTON',
            (action: ActionItem) => {
                this.confirmModal.close();
            }
        ),
        new ActionItem(
            'CONFERENCE.AUTHORIZATION_MODAL.OK_BUTTON',
            (action: ActionItem) => {}
        )
    ];

    stepName = StepName;

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        public conferenceStoreService: ConferenceStoreService
    ) {
        super();
    }

    ngOnInit() {
        this.conferenceStoreService.confirmationModal$
            .pipe(takeUntil(this.destroy$))
            .subscribe((op: FooterButtons) => {
                this.openConfirmModal(op);
            });
    }

    get initLoading(): boolean {
        return this.conferenceStoreService.initLoading;
    }

    get steps(): ConferenceStep[] {
        return this.conferenceStoreService.steps;
    }

    get activeStep(): ConferenceStep {
        return this.conferenceStoreService.activeStep;
    }

    get conference(): Conference {
        return this.conferenceStoreService.conference;
    }

    get wizardAsTabs(): boolean {
        return !(this.conference.isCompiling() || this.conference.isDraft());
    }

    isStepActive(stepName: StepName): boolean {
        return this.conferenceStoreService.isStepActive(stepName);
    }

    changeStepToChild(e: ConferenceStep): void {
        if (!this.isStepActive(e.id as StepName)) {
            return this.conferenceStoreService.changeStepToChild(e);
        }
    }

    ngAfterViewInit() {
        if (
            this.conferenceStoreService.isCreateMode() &&
            this.route.snapshot.data.openModal
        ) {
            this._openModalAfterCreate();
        }
    }

    private _openModalAfterCreate(): void {
        this.modalBeforeCreate.openModal();
    }

    // tslint:disable-next-line:use-life-cycle-interface
    ngOnDestroy() {
        this.destroy$.next(true);
        this.destroy$.unsubscribe();
        // this.conferenceStoreService.resetAll();
    }

    cancelModalBeforeCreate(): void {
        this.conferenceStoreService.cancelModalBeforeCreate();
    }

    closeModalBeforeCreate(): void {
        this.conferenceStoreService.closeModalBeforeCreate();
    }

    openConfirmModal(operation: FooterButtons): void {
        switch (operation) {
            case FooterButtons.COMPLETE: {
                this.modalTitle = 'CONFERENCE.CONFIRM_MODAL.TITLE';
                this.modalText = 'CONFERENCE.CONFIRM_MODAL.MESSAGE';
                this.modalButtons[1].action = (action: ActionItem) => {
                    this.conferenceStoreService.completeChange();
                    this.confirmModal.close();
                };
                break;
            }
            case FooterButtons.AUTHORIZE: {
                this.modalTitle = 'CONFERENCE.AUTHORIZATION_MODAL.TITLE';
                this.modalText = 'CONFERENCE.AUTHORIZATION_MODAL.MESSAGE';
                this.modalButtons[1].action = (action: ActionItem) => {
                    this.conferenceStoreService.authorizeChange();
                    this.confirmModal.close();
                };
                break;
            }
            case FooterButtons.UNAUTHORIZE: {
                this.modalTitle = 'CONFERENCE.DENY_MODAL.TITLE';
                this.modalText = 'CONFERENCE.DENY_MODAL.MESSAGE';
                this.modalButtons[1].action = (action: ActionItem) => {
                    this.conferenceStoreService.unauthorizeChange();
                    this.confirmModal.close();
                };
                break;
            }
        }
        this.confirmModal.open();
    }
}
