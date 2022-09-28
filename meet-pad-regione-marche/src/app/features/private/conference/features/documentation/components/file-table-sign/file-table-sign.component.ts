/* tslint:disable:no-inferrable-types */

import {
    Component,
    OnInit,
    Input,
    ViewChild,
    Output,
    EventEmitter,
    ElementRef,
    AfterViewInit
} from '@angular/core';

import * as $ from 'jquery';

import {
    AbstractTableField,
    TableField,
    ActionItem,
    TemplateField
} from '@eng-ds/ng-toolkit';

import { I18nService, ConfigService } from '@eng-ds/ng-core';

import { ComboBox, BaseFile, FileType, FileService } from '@common';
import { FileTableComponent } from '../file-table/file-table.component';
import { SignatureFile } from '@app/features/private/conference/core/models/documentation/signature-file.model';
import { SignatureStatus } from '@app/core';

@Component({
    selector: 'app-file-table-sign',
    templateUrl: './file-table-sign.component.html',
    styleUrls: ['./file-table-sign.component.scss']
})
export class FileTableSignComponent implements OnInit, AfterViewInit {
    @Input('disabled') disabled: boolean = false; //readonly
    @Input('isAdmin') isAdmin: boolean = false; 
    @Input('files') files: SignatureFile[] = [];

    @Output('signFile') signFile = new EventEmitter<BaseFile>();
    @Output('deleteFile') deleteFile = new EventEmitter<BaseFile>();
    @Output('unlockFile') unlockFile = new EventEmitter<SignatureFile>();
    @Output('downloadFile') downloadFile = new EventEmitter<SignatureFile>();

    @ViewChild('ownerTemplate') ownerTemplate;
    @ViewChild('statusTemplate') statusTemplate;
    @ViewChild('actionTemplate') actionTemplate;

    fileTypes = FileType;
    actions: ActionItem[] = [];

    /**
     * Contain the table structure for result
     */
    tableStructure: AbstractTableField[] = [];

    constructor(
        private i18nService: I18nService,
        private fileService: FileService,
        private configService: ConfigService
    ) {}

    isActionVisible(status: SignatureStatus, action: ActionItem, item: BaseFile): boolean{
        // xmf: sign fix
        switch (action.name){
            case 'BUTTON.SIGN' : 
                return !this.disabled && (!this.isAdmin || (this.isAdmin && !item.isFileSignedFromUser())) && (status == SignatureStatus.UNLOCKED || status == SignatureStatus.SIGNING);
            case 'BUTTON.UNLOCK' : 
                return  !this.disabled && this.isAdmin && status == SignatureStatus.LOCKED;
            case 'BUTTON.DELETE' : 
                return !this.disabled && this.isAdmin;
            case 'BUTTON.DOWNLOAD' : 
                return true; //status == SignatureStatus.COMPLETED;
            default:
                return false;
        }
        
    }

    ngOnInit(): void {
        this.tableStructure = this.defineTableStructure(this.tableStructure);
    }

    ngAfterViewInit(): void {
        // Workaround caused by use jQuery in Angular
        // tslint:disable-next-line: no-eval
        eval('$(\'[data-toggle="tooltip"]\').tooltip();');
    }

    truncate(s: string): string {
        if (s.length > 35) {
            return s.substring(0, 35) + '...';
        } else {
            return s;
        }
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {
        //set actions
        this.actions.push( new ActionItem(
            'BUTTON.SIGN',
            (a, item: BaseFile) => {
                this.signFile.emit(item);
            },
            'pencil'
        ));

        this.actions.push( new ActionItem(
            'BUTTON.UNLOCK',
            (a, item: SignatureFile) => {
                this.unlockFile.emit(item);
            },
            'unlock'
        ))

        this.actions.push( new ActionItem(
            'BUTTON.DELETE',
            (a, item: BaseFile) => {
                this.deleteFile.emit(item);
            },
            'trash'
        ))

        this.actions.push( new ActionItem(
            'BUTTON.DOWNLOAD',
            (a, item: SignatureFile) => {
                this.downloadFile.emit(item);
            },
            'download'            
        ))
            
        //set table column
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TABLE.STATUS'
                ),
                this.statusTemplate
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TABLE.OWNER'
                ),
                this.ownerTemplate
            )
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.FIRMA.TABLE.NAME'
                ),
                'NORMAL',
                'name',
                false
            )
        );

        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TABLE.ACTIONS'
                ),
                this.actionTemplate
            )
        );

        return structure;
    }

}
