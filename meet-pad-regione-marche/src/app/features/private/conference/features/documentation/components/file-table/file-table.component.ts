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

@Component({
    selector: 'app-file-table',
    templateUrl: './file-table.component.html',
    styleUrls: ['./file-table.component.scss']
})
export class FileTableComponent implements OnInit, AfterViewInit {
    @Input('disabled') disabled: boolean = false;
    @Input('selectionEnabled') selectionEnabled: boolean = false;
    @Input('showSectionDeleteList') showSectionDeleteList: boolean = false;
    @Input('showSectionDownload') showSectionDownload: boolean = false;
    @Input('files') files: BaseFile[] = [];
    @Input('sharedDocumentation') sharedDocumentation: boolean = false;
    @Input('additionalDocumentation') additionalDocumentation: boolean = false;
    @Input('statusDocumentation') statusDocumentation: boolean = false;

    @Output('editFile') editFile = new EventEmitter<BaseFile>();
    @Output('deleteFile') deleteFile = new EventEmitter<BaseFile>();
    @Output('editCloud') editCloud = new EventEmitter<BaseFile>();
    @Output('downloadFile') downloadFile = new EventEmitter<BaseFile>();
    @Output('downloadPDFFile') downloadPDFFile = new EventEmitter<BaseFile>();

    @ViewChild('templateVisibility') templateVisibility;
    @ViewChild('categoryTemplate') categoryTemplate;
    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('visibility') visibility: ElementRef;
    @ViewChild('statusTemplate') statusTemplate;
    @Output('deleteSectionFiles') deleteSectionFiles = new EventEmitter<any>();
    @Output('downloadSectionFiles') downloadSectionFiles = new EventEmitter<any>();

    fileTypes = FileType;
    cloudAction: ActionItem;
    linkAction: ActionItem;
    actions: ActionItem[] = [];

    selectAction = new ActionItem(
        'BUTTON.SELECT',
        (a, item: BaseFile) => {
            item.toggleSelection();
        },
        'square-o'
    )

    unselectAction = new ActionItem(
        'BUTTON.UNSELECT',
        (a, item: BaseFile) => {
            item.toggleSelection();
        },
        'check'
    )

    /**
     * Contain the table structure for result
     */
    tableStructure: AbstractTableField[] = [];

    constructor(
        private i18nService: I18nService,
        private fileService: FileService,
        private configService: ConfigService
    ) {}

    category(model: any) {
        let _c = '---';
        if (model.category && model.category.value) {
            _c = model.category.value;
        }

        if (model.cityReference && model.cityReference) {
            _c = `${_c} - ${model.cityReference}`;
        }
        return _c;
    }

    getStatus(model: any): string {
        let status = '---';
        if (model.statusDocument) {
            status = 'CONFERENCE.WIZARD.EVENTS.TABLE.STATUS.'.concat(model.statusDocument);

            $('#statusTemplate').addClass('label signed');
        }
        return status;
    }

    ngOnInit(): void {
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        $('.td-visibility')
            .parent('td')
            .addClass('visibilityCel');
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
        this.actions = [
            new ActionItem(
                'BUTTON.DOWNLOAD',
                (a, item: BaseFile) => {
                    this.downloadFile.emit(item);
                },
                'download'
            )
        ];
        if(this.additionalDocumentation) {
            this.actions.push(
                new ActionItem(
                    'BUTTON.DOWNLOAD_PDF',
                    (a, item: BaseFile) => {
                        this.downloadPDFFile.emit(item);
                    },
                    'file-pdf-o'
                )
            );
        }
        if (this.disabled) {
            this.actions.push(
                new ActionItem(
                    'BUTTON.VIEW',
                    (a, item: BaseFile) => {
                        this.editFile.emit(item);
                    },
                    'eye'
                )
            );
        } else {
            this.actions.push(
                new ActionItem(
                    'BUTTON.DELETE',
                    (a, item: BaseFile) => {
                        this.deleteFile.emit(item);
                    },
                    'trash'
                ),
                new ActionItem(
                    'BUTTON.UPDATE',
                    (a, item: BaseFile) => {
                        this.editFile.emit(item);
                    },
                    'pencil'
                )
            );
        }

        if (this.configService.get('onlyOfficeEnabled')) {
            this.cloudAction = new ActionItem(
                'BUTTON.CLOUD_EDIT',
                (a, item: BaseFile) => {
                    this.editCloud.emit(item);
                },
                'cloud'
            );
        }
        this.linkAction = new ActionItem(
            'BUTTON.LINK',
            (a, item: BaseFile) => {
                window.open(item.url, '_blank');
            },
            'link'
        );

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TABLE.NAME'
                ),
                'NORMAL',
                'name',
                false
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TABLE.CATEGORY'
                ),
                this.categoryTemplate
            )
        );
        structure.push(
            new TemplateField(
                this.i18nService.translate(
                    'CONFERENCE.WIZARD.DOCUMENTATION.ADDITIONAL.TABLE.VISIBILITY'
                ),
                this.templateVisibility
            )
        );
        if(this.statusDocumentation) {
            structure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'CONFERENCE.WIZARD.DOCUMENTATION.STATUS_DOCUMENT.TITLE'
                    ),
                    this.statusTemplate
                )
            );
        }
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

    isLink(model: any): boolean {
        return model.url.indexOf(this.configService.get('backend.baseUrl'));
    }

    deleteFilesSelected(){
        let fileList = []

        let folders : any = this.files;
        folders.forEach( file => {  
            if(file.selected){
                fileList.push(file)
            }
        });

        this.deleteSectionFiles.emit(fileList)
    }

    toggleSelection(){
        let folders : any = this.files;
        folders.forEach( file => {  
            file.selected = !file.selected;
        });
    }

    doDownloadSectionFiles(){
        let fileList = []

        let folders : any = this.files;
        folders.forEach( file => {  
            fileList.push(file)
        });

        this.downloadSectionFiles.emit(fileList)
    }

}
