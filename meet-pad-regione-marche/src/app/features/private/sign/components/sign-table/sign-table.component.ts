    import { Component, OnInit, ViewChild, Input, Output, EventEmitter } from '@angular/core';
    import { AbstractTableField, TableField, TemplateField, TableResultComponent, ModalComponent, ActionItem, ActionsField } from '@eng-ds/ng-toolkit';
    import { I18nService, ConfigService } from '@eng-ds/ng-core';
    import { Subscription, Observable, of } from 'rxjs';
    import { AutoUnsubscribe, LoaderService, WrapperGetData, SectionLoading, FileService } from '@common';
    import { SearchSign } from '../../models/search-sign.models';
    import { SignService } from '../../services/sign.service';
    import { tap, takeUntil, catchError } from 'rxjs/operators';
    import { ToastrService } from 'ngx-toastr';
    import { BaseFile } from '@common';
    import { Router } from '@angular/router';
    import { FormFieldGroup } from 'projects/common/src/lib/components/form/interfaces/form-field-group.interface';

    @Component({
    selector: 'app-sign-table',
    templateUrl: './sign-table.component.html',
    styleUrls: ['./sign-table.component.scss']
    })
    export class SignTableComponent extends AutoUnsubscribe implements OnInit {

    @ViewChild('confirmModal') confirmModal: ModalComponent;
    @ViewChild(TableResultComponent) tableResult: TableResultComponent;
    @ViewChild('actionTemplate') actionTemplate;
    @ViewChild('ownerTemplate') ownerTemplate;
    @Input() title: string;
    @Input('status') status: string;
    @Input('selectionEnabled') selectionEnabled: boolean = false;
    @Input('readonly') readonly: boolean;
    @Input('selectedFiles') selectedFiles: boolean;
    @Input('disabledSignButton') disabledSignButton: boolean;
    @Output('rejectSectionFiles') rejectSectionFiles = new EventEmitter<any>();
    @Output('signSectionFiles') signSectionFiles = new EventEmitter<any>();

    groupFields: Map<string, FormFieldGroup> = new Map();

    loading: boolean = false;
    idToClone: string;
    idDocumentToSign: string[]=[];
    results: SearchSign[] = [];

    /**
     * Witch filed use for sort the result
     */
    public sortFieldActive: string;
    /**
     * Kind of sort
     */
    public sortType: 'asc' | 'desc' = 'asc';

    /**
     * Actual page of result loaded
     */
    public pageNumber: number = 1;
    public pageSizeNumber: number = 10;
    public totalPages = 0;
    public dateFormat = this.configService.get('dateFormat.toUI');
    hasCriteria = false;
    /**
     * This will contain the subscription to search operation
     */
    protected searchSubscription: Subscription;

    /**
     * Contain the table structure for result
     */
    tableStructure: AbstractTableField[] = [];

    selectAction = new ActionItem(
        'BUTTON.SELECT',
        (a, item: SearchSign) => {
            item.toggleSelection();
        },
        'square-o'
    )

    unselectAction = new ActionItem(
        'BUTTON.UNSELECT',
        (a, item: SearchSign) => {
            item.toggleSelection();
        },
        'check'
    )

    downloadAction = new ActionItem(
        'BUTTON.DOWNLOAD',
        (a, item: SearchSign) => {
            this.downloadFile(item);
        },
        'download'
    )

    detailConferenceAction = new ActionItem(
        'SIGN.TABLE.ACTIONS.BUTTONS.ENTER',
        (a, item: SearchSign) => {
            setTimeout(() => {
                window.open('/conference/'+ item.idConference, '_blank');
            });
        },
        'sign-in'
    )
    
    actions: ActionItem[] = [];

    constructor(
        private i18nService: I18nService,
        private signService: SignService,
        private configService: ConfigService,
        private loaderService: LoaderService,
        private toastr: ToastrService,
        private router: Router,
        private fileService: FileService
    ) {
    super();
  
    }
    
    get loading$(): Observable<boolean> {
        return this.loaderService.getLoading$(SectionLoading.CLONE_MODAL);
    }

    ngOnInit() {
        this.tableStructure = this.defineTableStructure(this.tableStructure);
        this._initAction();
        this.sortFieldActive = this.getDefaultSortField();
        this.pageSizeNumber = 10;
        this.runSearch();
        this.isSelectedDocument();

    }

    ngAfterContentChecked(){
        this.isSelectedDocument();
    }

    defineTableStructure(
        structure: AbstractTableField[]
    ): AbstractTableField[] {

        structure.push(
            new TableField(
                this.i18nService.translate(
                    'SIGN.TABLE.STATUS'
                ),
                'NORMAL',
                'status',
                false
            )
        );
        structure.push(
            new TableField(
                this.i18nService.translate(
                    'SIGN.TABLE.DOCUMENT'
                ),
                'NORMAL',
                'title',
                false
            )
        );
        structure.push(
            new ActionsField(
                'SIGN.TABLE.DETAIL',
                this.actions,
                this._canActionShowed.bind(this)
            )
        );
  
        if(!this.readonly){
            structure.push(
                new TemplateField(
                    this.i18nService.translate(
                        'SIGN.TABLE.ACTIONS.BUTTONS.SELECT'
                    ),
                    this.actionTemplate,
                )
            );
        }
        
        return structure;      
    }

    /**
     * Change the page to load
     */
    public changePage(pageNumber: number) {
        if (pageNumber > 0) {
            this.pageNumber = pageNumber--;
        } else {
            this.pageNumber = pageNumber;
        }

        this.hasCriteria = true;
        this.runSearch();
    }

    public changePageElementsShowed(pageSizeNumber: number) {
        this.hasCriteria = true;
        this.pageSizeNumber = pageSizeNumber;
        this.refreshSearch();
    }

    /**
     * Refresh the search and managing also che reset of page cache
     */
    public refreshSearch(newSearch = false) {
        this.startPageLoading();
        if (newSearch) {
            this.pageNumber = 1;
            this.tableResult.pageActive = 1;
            this.tableResult.calculatePageNumberToShow(
                this.pageNumber,
                this.tableResult.selectedOption
            );
        }
        this.runSearch();
    }

    /**
     * Change the sort field to input one. If the field it's the same change the sort type.
     * @param field
     */
    //TODO controllare
    public changeSort(field: string) {
        if (field === this.sortFieldActive) {
            // switch sort type
            this.sortType = this.sortType === 'asc' ? 'desc' : 'asc';
            this.pageNumber = 1;
            this.pageSizeNumber = 10;
        } else {
            this.sortType = 'asc';
            this.sortFieldActive = field;
            this.pageNumber = 1;
            this.pageSizeNumber = 10;
        }
        this.hasCriteria = true;
        // after change refresh the search
        this.refreshSearch();
    }

    private _getTemplateField(label: string, fieldName: string, template: any) {
        const field = new TemplateField(
            this.i18nService.translate(label),
            template
        );
        field.sortable = true;
        field.propertyNameToUse = fieldName;

        return field;
    }

    // TODO
    private _canActionShowed(model: SearchSign, action: ActionItem): boolean {
        return false;
        //model.permissions[  action.name          .substring(action.name.lastIndexOf('.') + 1).toLowerCase()  ];
    }

    getDefaultSortField(): string {
        return 'name';
    }

    private runSearch() {
        this.searchSubscription = this.documentSearch()
            .pipe(takeUntil(this.destroy$))
            .subscribe(() => {
                this.stopPageLoading();
            });
    }

    public documentSearch(): Observable<WrapperGetData<SearchSign>> {
        let pageSize = this.pageSizeNumber;
        const criteria = {};
        criteria['sort'] = this.sortFieldActive + ',' + this.sortType;
        criteria['page'] = this.pageNumber;
        criteria['size'] = this.pageSizeNumber || 10;
        return this.signService
        .documentSignSearch(criteria, this.status)
        .pipe(
            // TODO: ridonante
            tap((results: WrapperGetData<SearchSign>) => {
                this.results = results.list;
           
                this.totalPages = Math.ceil(
                    // tslint:disable-next-line:radix
                    parseInt(results.totalNumber) / pageSize
                );
            }),
            catchError(() => {
                this.stopPageLoading();
                return of({ list: [], totalNumber: '' });
            })
        );
    }

    /**
     * Build the dto for backend from criteria
     */
    buildCriteriaDto() {
        const criteria = {};
        criteria['sort'] = this.sortFieldActive + ',' + this.sortType;
        criteria['page'] = this.pageNumber;
        criteria['size'] = this.pageSizeNumber || 10;
        return criteria;
    }

    public resetSearch() {
        this.sortType = 'asc';
        this.sortFieldActive = this.getDefaultSortField();
        this.pageNumber = 1;
        this.pageSizeNumber = 10;
    }

    public startPageLoading() {
        this.loading = true;
        this.loaderService.showLoading(SectionLoading.SEARCH);
    }

    public stopPageLoading() {
        setTimeout(() => {
            this.loading = false;
            this.loaderService.hideLoading(SectionLoading.SEARCH);
            this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
        }, 500);
    }

    private _initAction(): void {
    // -+ Aggiungere if per ogni pulsante, sulla base di quanto ritornato dal BE
        this.actions.push(
            new ActionItem(
                'SIGN.TABLE.ACTIONS.BUTTONS.ENTER',
                (a, item: SearchSign) => {
                //    this.loggerService.log('edit', item);
                    this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
                    setTimeout(() => {
                //TODO        this.router.navigate(['/conference', item.id]);
                    });
                },
                'sign-in'
            ),       
        );
    }

    toggleSelection(){
        let documents : any = this.results;
        let count=0;

        documents.forEach( item => {  if(item.selected) count++});

        documents.forEach( item => {  
            if(count<10 && !item.selected){
                item.selected = !item.selected;
                count++;
            }
        });
    }
    doSignFilesSelected(){
        let fileList = []

        let folders : any = this.results;
        folders.forEach( file => {
            if(file.selected){
                fileList.push(file)
            }
        });

        this.signSectionFiles.emit(fileList)
        //this.signSelectedModal.open();
    }

    doRejectFilesSelected(){
        let fileList = []

        let folders : any = this.results;
        folders.forEach( file => {  
            if(file.selected){
                fileList.push(file)
            }
        });

        this.rejectSectionFiles.emit(fileList);
    }

    doRefreshTable(){
        this.refreshSearch();
    }
    
    isSelectedDocument(): boolean{
        this.selectedFiles = true;
        let folders : any = this.results;
        folders.forEach( file => {  
            if(file.selected){
               this.selectedFiles = true;
            }

        });
        return this.selectedFiles;
    }

    downloadFile(item: any): void {
        console.log('--------------downloadFile: ');
        this.loaderService.showLoading(SectionLoading.ALL_CONTENT);
        this.fileService
            .download(new BaseFile({ url: item.url , name: item.name}))
            .pipe(
                catchError(_ => {
                    this.toastr.error(
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TEXT'
                        ),
                        this.i18nService.translate(
                            'CONFERENCE.WIZARD.DOCUMENTATION.TOASTR.FILE_DOWNLOAD_ERROR.TITLE'
                        )
                    );
                    return of(null);
                })
            )
            .subscribe(_ => {
                this.loaderService.hideLoading(SectionLoading.ALL_CONTENT);
            });
    }

}
