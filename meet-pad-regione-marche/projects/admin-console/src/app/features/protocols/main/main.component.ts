import { Component, OnInit,ViewChild } from '@angular/core';
import { ModalComponent,ActionItem,ErrorMessage } from '@eng-ds/ng-toolkit';
import { SearchProtocol } from '../core/models/searchProtocol.model';
import { LoaderService,WrapperPostPutData } from '@common';
import { Router, ActivatedRoute } from '@angular/router';
import { ProtocolService } from '../core/services/protocol.services';

@Component({
    // tslint:disable-next-line: component-selector
    selector: 'admin-protocol-main',
    templateUrl: './main.component.html',
    styleUrls: ['./main.component.scss']
})
export class MainProtocolComponent implements OnInit {
    @ViewChild('confirmModal') confirmModal: ModalComponent;

    searchQuery: string;
    actions: ActionItem[] = [];
    
    confirmModalTitle: string = '';
    confirmModalText: string = '';

    protocol: SearchProtocol;

    confirmModalButtons: ActionItem[] = [];

    constructor(
        private protocolService: ProtocolService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private loaderService: LoaderService
    ) {}

    ngOnInit() {
    }

    startResearch(query: string) {
        this.searchQuery = query;
    }

    // apertura modale
    updateProtocolState(protocol: SearchProtocol): void {
        this.confirmModalTitle =
                    'PROTOCOLS.MODAL.TITLE';
        this.confirmModalText =
                    'PROTOCOLS.MODAL.PROTOCOL_EDIT_STATE';
        this.protocol = protocol;
        this.viewModalButtons();
        this.openModal();
    }

    viewModalButtons(): void {
        this.confirmModalButtons = [
            new ActionItem(
                'PROTOCOLS.MODAL.CONFIRM_BUTTON',
                (action: ActionItem) => {
                    this.confirmModal.close();
                    this.protocolService.updateProtocolState(
                        this.protocol
                    ).subscribe(
                        (response: WrapperPostPutData) => {
                           this.protocolService.protocolSaveComplete(this.protocol);
                        },
                        (error: any) => {
                          
                            const errorMex = new ErrorMessage();
                            errorMex.title = 'ERRORE.GENERICO_SERVER';
                            errorMex.message = error.message;
                            this.protocolService.saveError(errorMex);
                          
                        }                        
                    );
                }
            ),
            new ActionItem(
                'PROTOCOLS.MODAL.CANCEL_BUTTON',
                (action: ActionItem) => {
                    this.confirmModal.close();

                }
            )
        ];     
    }

    openModal(): void {
        this.confirmModal.open();
    }  
}
